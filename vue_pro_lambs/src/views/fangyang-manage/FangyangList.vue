<script setup>
import { Scene, ImageLayer, PointLayer, Popup } from '@antv/l7'
import { Map } from '@antv/l7-maps'
import { onMounted } from 'vue'
/* 导入放养方法 */
import { getFangyang } from '@/apis/fang'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'

/* 实例化用户状态 */
const { user } = useUserStore()

/* 全局变量 */
let popup
/* 全局变量 */
let scene

onMounted(() => {
  /* 初始化Scene */
  initScene()
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

  scene.on('loaded', () => {
    /* 调用方法获取个人所有放养信息 */
    getF()
    /* 添加图片 */
    scene.addLayer(imagelayer)
  })

  /* 图片添加点击事件 */
  imagelayer.on('click', () => {
    /* 存在则关闭弹窗 */
    popup && popup.close()
  })

  /* 获取放养信息 */
  const getF = async () => {
    /* 获取用户id */
    const data = { userId: user.userId }
    /* 调用方法获取列表 */
    const { data: ref } = await getFangyang(data)
    /* 采用map转换为需要的数值 */
    let fangData = ref.data.map((item) => ({
      x: item.x,
      y: item.y,
      t: item.fangName,
      userId: item.userId,
    }))
    /* 调用方法添加文字 */
    addText(fangData)
  }

  /* 添加文字方法 */
  const addText = (data) => {
    const textlayer = new PointLayer({ zIndex: 2 })
      .source(data, {
        parser: {
          type: 'json',
          x: 'x',
          y: 'y',
        },
      })
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
    /* 为文字添加点击事件 */
    showDialog(textlayer)
  }
}
/* 定义文字点击事件 */
const showDialog = (textlayer) => {
  /* 图片添加带点击事件 */
  textlayer.on('click', async (e) => {
    /* 携带用户id、用户养殖场名称 */
    const da = { userId: e.feature.userId, fangName: e.feature.t }
    const { data: ref } = await getFangyang(da)
    /* 判断是否返回成功 */
    if (ref.code > 0) {
      /* 定义临时数组 */
      let list = []
      list = ref.data
        .map(
          (item) => `
      <div class="el-card is-always-shadow box-card">
        <span style="color:skyblue">养殖场名称:<span style="color:black">${item.fangName}</span></span></br>
        <span style="color:skyblue">养殖类型:<span style="color:black">${item.fangKind}</span></span></br>
        <span style="color:skyblue">养殖数量:<span style="color:black">${item.fangCount}</span>只</span></br>
      </div>`
        )
        .join('')
      /* 添加弹窗 */
      popup = new Popup({
        offsets: [0, 0],
        closeButton: false,
      })
        .setLnglat(e.lngLat)
        .setHTML(list)
      scene.addPopup(popup)
    }
  })
}
</script>

<template>
  <div style="height: 89vh; justify-content: center; position: relative" id="map" />
</template>

<style></style>
