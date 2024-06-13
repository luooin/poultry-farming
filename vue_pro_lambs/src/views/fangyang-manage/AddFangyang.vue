<script setup>
import { Scene, ImageLayer, PointLayer, Popup } from '@antv/l7'
import { Map } from '@antv/l7-maps'
import { ElMessage } from 'element-plus'
import { nextTick, onMounted, reactive, ref } from 'vue'
/* 导入添加放养方法 */
import { addFangyang } from '@/apis/fang'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'

/* 实例化用户状态 */
const { user } = useUserStore()

/* 全局变量 */
let popup

let scene

/* 新增繁育信息对话框控制显示隐藏对象 */
const Fangdialog = ref(false)

/* 表单繁育对象 */
const ruleFormRef = ref()

/* 表单繁育规则 */
const rules = reactive({
  fangName: [{ required: true, message: '请输入放养地球名称', trigger: 'blur' }],
  fangKind: [{ required: true, message: '请输入放养种类', trigger: 'blur' }],
  fangCount: [{ required: true, message: '请输入放养数量', trigger: 'blur' }],
})

/* 表单繁育信息 */
const ruleForm = reactive({
  fangName: '',
  fangKind: '',
  fangCount: '',
  x: '',
  y: '',
  userId: '',
})

onMounted(() => {
  /* nextTick:页面渲染完毕后立即执行 */
  nextTick(() => {
    /* 初始化Scene */
    initScene()
  })
})

/* 初始化Scene */
const initScene = () => {
  scene = new Scene({
    id: 'map',
    map: new Map({
      center: [500, 500],
      zoom: 3,
      version: 'SIMPLE',
      mapSize: 1000,
      maxZoom: 5,
      minZoom: 2,
      pitchEnabled: false,
      rotateEnabled: false,
    }),
  })
  scene.setBgColor('rgb(94, 182, 140)')

  /* 图片信息 */
  const imagelayer = new ImageLayer({}).source('/ditu.png', {
    parser: {
      type: 'image',
      /* 左下到右上的x,y坐标轴 */
      extent: [360, 400, 640, 600],
    },
  })

  /* 图片添加带点击事件 */
  imagelayer.on('click', (e) => {
    /* 解构出纬度、经度 */
    let { lat, lng } = e.lngLat
    /* 添加弹窗 */
    popup = new Popup({
      offsets: [0, 0],
      closeButton: false,
    })
      .setLnglat(e.lngLat)
      .setHTML(`<button class="el-button el-button--primary" onclick="addFy(${lat},${lng})">添加放养信息</button>`)
    scene.addPopup(popup)
  })

  /* scene加载时添加图标 */
  scene.on('loaded', () => {
    scene.addLayer(imagelayer)
  })
}
/* 定义window事件用于添加放养信息 */
window.addFy = (lat, lng) => {
  /* 打开对话框 */
  Fangdialog.value = true
  /* ruleForm对象中的x、y赋值 */
  ruleForm.x = 500 + 2.8 * lng
  ruleForm.y = 500 + 2.8 * lat
  /* 关闭popup对象 */
  popup.close()
}

/* 新增放养信息按钮点击 */
const addFang = (formEl) => {
  /* 表单信息非空校验 */
  formEl.validate(async (vallid) => {
    if (vallid) {
      ruleForm.userId = user.userId
      const { data: ref } = await addFangyang(ruleForm)
      /* 判断是否成功 */
      if (ref.code > 0) {
        /* 调用方法在界面上显示信息 */
        showFangMessage()
        /* 关闭对话框 */
        Fangdialog.value = false
        ElMessage.success(ref.msg)
      }
    } else {
      ElMessage.warning('请填写相关信息')
    }
  })
}

/* 显示信息 */
const showFangMessage = () => {
  const textlayer = new PointLayer({ zIndex: 2 })
    .source(
      [
        {
          x: ruleForm.x,
          y: ruleForm.y,
          t: ruleForm.fangName,
        },
      ],
      {
        parser: {
          type: 'json',
          x: 'x',
          y: 'y',
        },
      }
    )
    .shape('t', 'text')
    .size(12)
    .active({
      color: '#00f',
      mix: 0.9,
    })
    .color('rgb(86, 156, 214)')
    .style({
      textAnchor: 'center', // 文本相对锚点的位置 center|left|right|top|bottom|top-left
      spacing: 2, // 字符间距
      fontWeight: '800',
      padding: [1, 1], // 文本包围盒 padding [水平，垂直]，影响碰撞检测结果，避免相邻文本靠的太近
      stroke: '#ffffff', // 描边颜色
      strokeWidth: 2, // 描边宽度
      textAllowOverlap: true,
    })
  scene.addLayer(textlayer)
}
</script>

<template>
  <div style="height: 89vh; justify-content: center; position: relative" id="map" />

  <!-- 放养列表对话框 -->
  <el-dialog v-model="Fangdialog" title="新增放养信息" width="30%">
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="110px" status-icon>
      <el-form-item label="放养地区名称" prop="fangName">
        <el-input v-model.trim="ruleForm.fangName" placeholder="请输入放养地区名称" />
      </el-form-item>
      <el-form-item label="放养种类" prop="fangKind">
        <el-input v-model.trim="ruleForm.fangKind" placeholder="请输入放养种类" />
      </el-form-item>
      <el-form-item label="放养数量" prop="fangCount">
        <el-input v-model.trim="ruleForm.fangCount" type="number" placeholder="请输入放养数量" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="margin-right: 20px" @click="addFang(ruleFormRef)">新增</el-button>
        <el-button type="info" @click=";(Fangdialog = false), popup.close()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style></style>
