<script setup>
import { DArrowRight, Search, Plus, UploadFilled } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { onMounted, reactive, ref } from 'vue'
/* 导入日期格式化js */
import dayjs from 'dayjs'
/* 导入繁育列表方法 */
import { BreedList, addBreedByB, updateBreed, deleteBreed } from '@/apis/breed'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 修改对话框控制显示隐藏对象 */
const dialogVisible = ref(false)
/* 问题对话框控制显示隐藏对象 */
const matterdialog = ref(false)
/* 遇到问题对话框控制显示隐藏对象 */
const Hasmatterdialog = ref(false)
/* 新增繁育信息对话框控制显示隐藏对象 */
const Adddialog = ref(false)
/* 图片上传对话框显示和隐藏 */
const imageUpLoadDialog = ref(false)
/* 表单繁育对象 */
const ruleFormRef = ref()
/* 表格繁育对象 */
const tableBreed = ref([])
/* 表单遇到问题对象 */
const hasBreedMatter = ref()

/* 表单繁育规则 */
const rules = reactive({
  breedName: [{ required: true, message: '请输入基地名称', trigger: 'blur' }],
  breedKind: [{ required: true, message: '请输入繁育类型', trigger: 'blur' }],
  breedCount: [{ required: true, message: '请输入繁育数量', trigger: 'blur' }],
  breedStartdate: [{ required: true, message: '请选择繁育开始日期', trigger: 'change' }],
  breedTitle: [{ required: true, message: '请输入问题简介', trigger: 'blur' }],
  breedContent: [{ required: true, message: '请输入问题详情', trigger: 'blur' }],
})

/* 表单繁育信息 */
const ruleForm = reactive({
  id: '',
  breedName: '',
  breedKind: '',
  breedCount: '',
  breedStartdate: '',
  breedTitle: '',
  breedContent: '',
  figure: '',
  userId: '',
})
/* 表单遇到问题信息 */
const hasBreedMatterForm = reactive({
  userId: '',
  breedTitle: '',
  breedContent: '',
  figure: '',
})
/* 图片上传对象 */
const imgUpload = reactive({
  id: '',
  userId: '',
  breedImg: '',
})

/* 头部搜索输入框信息收集 */
const headBreed = reactive({
  breedName: '',
  breedKind: '',
  breedContent: '',
})
/* 问题信息 */
const matter = reactive({
  id: '',
  userId: '',
  breedTitle: '',
  breedContent: '',
  figure: '',
})

/* 生命周期函数,挂载时 */
onMounted(() => {
  /* 调用方法获取繁育信息 */
  getBreedList()
})
const getBreedList = async () => {
  /* 临时封装用户id */
  const params = { userId: user.userId }
  /* 获取返回信息 */
  const { data: ref } = await BreedList(params)
  /* 赋值给表格对象 */
  tableBreed.value = ref.data
}
const clickrow = (item) => {
  /* 将获取的值返回给表单繁育信息 */
  ruleForm.breedName = item.breedName
  ruleForm.breedKind = item.breedKind
  ruleForm.breedCount = item.breedCount
  ruleForm.breedStartdate = item.breedStartdate
  ruleForm.breedTitle = item.breedTitle
  ruleForm.breedContent = item.breedContent
  ruleForm.figure = item.figure
  ruleForm.userId = item.userId
  ruleForm.id = item.id
  /* 控制对话框显示 */
  dialogVisible.value = true
}
/* 修改繁育信息按钮点击 */
const AlterBreed = (formEl) => {
  /* 表单信息非空校验 */
  formEl.validate(async (vallid) => {
    /* 若有一项为空 */
    if (vallid) {
      ruleForm.userId = user.userId
      const { data: ref } = await updateBreed(ruleForm)
      if (ref.code > 0) {
        /* 调用方法刷新页面 */
        getBreedList()
        ElMessage.success(ref.msg)
        /* 关闭对话框 */
        dialogVisible.value = false
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}
/* 新增基地中的新增按钮点击 */
const addBreedMsg = (formEl) => {
  /* 表单信息非空校验 */
  formEl.validate(async (vallid) => {
    /* 若有一项为空 */
    if (vallid) {
      ruleForm.userId = user.userId
      const { data: ref } = await addBreedByB(ruleForm)
      if (ref.code > 0) {
        /* 调用方法刷新页面 */
        getBreedList()
        ElMessage.success(ref.msg)
        /* 清空表单内容 */
        clear()
        /* 关闭对话框 */
        Adddialog.value = false
      } else {
        ElMessage.warning(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}
/* 取消信息修改 */
const cancelAlter = () => {
  /* 清空内容 */
  clear()
  /* 关闭对话框 */
  dialogVisible.value = false
}
/* 有问题文字点击 */
const hasMatter = (title, content, id) => {
  /* 数据赋值 */
  matter.breedTitle = title
  matter.breedContent = content
  matter.id = id
  matterdialog.value = true
}
/* 遇到问题按钮点击 */
const hasMatterClick = (item) => {
  /* 数据赋值 */
  hasBreedMatterForm.id = item.id
  /* 打开对话框 */
  Hasmatterdialog.value = true
}
/* 新增繁育信息按钮点击 */
const addBreed = () => {
  Adddialog.value = true
}
/* 清空表单内容 */
const clear = () => {
  (ruleForm.breedName = ''), (ruleForm.breedKind = ''), (ruleForm.breedCount = ''), (ruleForm.breedStartdate = ''), (ruleForm.breedTitle = ''), (ruleForm.breedContent = ''), (ruleForm.figure = ''), (ruleForm.userId = '')
}
/* 清空表单遇到问题内容 */
const clearhasBreedMatterForm = () => {
  (hasBreedMatterForm.userId = ''), (hasBreedMatterForm.breedContent = '')
  ;(hasBreedMatterForm.breedTitle = ''), (hasBreedMatterForm.figure = '')
}
/* 清空问题对话框 */
const cleanslolveProblem = () => {
  (matter.id = ''), (matter.userId = ''), (matter.breedTitle = ''), (matter.breedContent = '')
}
/* 查询繁育基地 */
const selBreed = () => {
  /* 非空判断 */
  if (headBreed.breedName == '' && headBreed.breedKind == '' && headBreed.breedContent == '') {
    /* 调用方法刷新页面 */
    getBreedList()
  }
  /* 调用filter方法过滤 */
  tableBreed.value = tableBreed.value.filter((item) => (headBreed.breedName != '' ? item.breedName == headBreed.breedName : '' || headBreed.breedKind != '' ? item.breedKind == headBreed.breedKind : '' || headBreed.breedContent != '' ? item.breedCount == headBreed.breedContent : ''))
}
/* 添加所遇到的问题点击提交按钮 */
const commitMatter = (formEl) => {
  /* 表单信息非空校验 */
  formEl.validate(async (vallid) => {
    /* 临时赋值 */
    hasBreedMatterForm.userId = user.userId
    hasBreedMatterForm.figure = 0
    console.log(hasBreedMatterForm)
    /* 非空判断 */
    if (vallid) {
      /* 调用方法更新 */
      const { data: ref } = await updateBreed(hasBreedMatterForm)
      if (ref.code > 0) {
        /* 调用方法刷新页面 */
        getBreedList()
        /* 清空表单数据 */
        clearhasBreedMatterForm()
        ElMessage.success('添加问题成功')
        /* 关闭对话框 */
        Hasmatterdialog.value = false
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.info('请将信息填写完整')
    }
  })
}
/* 点击了删除繁育基地的按钮 */
const deleteSelBreed = (item) => {
  ElMessageBox.confirm('确定删除该基地吗?', 'Warning', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      /* 临时参数 */
      item.userId = user.userId
      /* 调用方法执行删除 */
      const { data: ref } = await deleteBreed(item)
      if (ref.code > 0) {
        /* 调用方法刷新页面 */
        getBreedList()
        ElMessage.success('删除成功')
      } else {
        ElMessage.info(ref.msg)
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

/* 解决问题按钮点击 */
const solveProblem = async (item) => {
  /* 临时数据 */
  item.userId = user.userId
  item.figure = 1
  const { data: ref } = await updateBreed(item)
  if (ref.code > 0) {
    /* 清空表单数据 */
    cleanslolveProblem()
    /* 关闭对话框 */
    matterdialog.value = false
    ElMessage.success('问题成功解决')
    /* 刷新页面 */
    getBreedList()
  } else {
    ElMessage.success(ref.msg)
  }
}

/* 修改图片按钮点击 */
const upDateImage = (item) => {
  /* 临时赋值给图片上传对象 */
  (imgUpload.id = item.id), (imageUpLoadDialog.value = true)
}

/* 图片上传前检查 */
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('图片必须为JPG格式!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('上传文件大小不能超过2MB!')
    return false
  }
  return true
}

/* 图片上传成功后的回调 */
const imageUpLoadSuccess = async (item) => {
  /* 临时赋值 */
  imgUpload.breedImg = item.data
  imgUpload.userId = user.userId
  const { data: ref } = await updateBreed(imgUpload)
  if (ref.code > 0) {
    /* 关闭对话框 */
    imageUpLoadDialog.value = false
    /* 刷新页面 */
    getBreedList()
    ElMessage.success('图片上传成功')
  }
}

/* 显示图片信息 */
const showImg = (item) => {
  return `/lambs/image/download?name=${item.breedImg}&token=`+localStorage.getItem('token')
}
const data={
  token:localStorage.getItem('token')
}
</script>

<template>
  <!-- 头部 -->
  <div class="breed-header">
    <h3 class="breed-text">繁育列表</h3>
    <el-icon style="margin-top: 3px"><DArrowRight /></el-icon>
  </div>
  <!-- 头部的搜索栏 -->
  <div class="breed-top-input">
    <div class="breed-top-input-border">
      <span class="feed-top-input-text">基地名称</span>
      <el-input v-model.trim="headBreed.breedName" placeholder="请输入基地名称" :prefix-icon="Search" style="width: 160px" />
      <span class="feed-top-input-text">基地繁育类型</span>
      <el-input v-model.trim="headBreed.breedKind" placeholder="请输入基地繁育类型" :prefix-icon="Search" style="width: 180px" />
      <span class="feed-top-input-text">基地繁育数量</span>
      <el-input v-model.trim="headBreed.breedContent" placeholder="请输入基地繁育数量" type="number" :prefix-icon="Search" style="width: 190px" />
    </div>
    <!-- 头部搜索栏右边 -->
    <div class="breed-top-right">
      <el-button type="primary" :icon="Search" @click="selBreed">查询</el-button>
      <el-button type="primary" :icon="Plus" @click="addBreed">新增繁育基地</el-button>
    </div>
  </div>
  <!-- 分割线 -->
  <el-divider />
  <!-- 主体 -->
  <div class="breed-container">
    <el-row :gutter="29" style="margin-left: 0px; margin-right: 0px">
      <template v-for="item in tableBreed" :key="item.id">
        <!-- 单个体 -->
        <el-col :xs="12" :sm="8" :md="6">
          <div class="breed-item">
            <div class="breed-item-head">
              <!-- 给实验室名称添加了下拉菜单 -->
              <el-dropdown placement="bottom-start">
                <span class="breed-item-head-text">{{ item.breedName }}</span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="clickrow(item)">修改繁育信息</el-dropdown-item>
                    <el-dropdown-item @click="upDateImage(item)">修改图片信息</el-dropdown-item>
                    <el-dropdown-item @click="hasMatterClick(item)" v-if="item.figure != 0">遇到问题</el-dropdown-item>
                    <el-dropdown-item @click="deleteSelBreed(item)">删除基地</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              <!-- 返回的指定字段不为空时展示 -->
              <span class="breed-item-head-matter" @click="hasMatter(item.breedTitle, item.breedContent, item.id)" v-if="item.figure != 1">有问题</span>
            </div>
            <div class="breed-item-body">
              <!-- 图片信息 -->
              <el-image :src="showImg(item)" fit="fill" class="breed-item-img"/>
            </div>
            <div class="breed-item-food">
              <div class="breed-item-food-head">
                <span>{{ item.breedKind }}</span>
                <span>{{ item.breedCount }}只</span>
              </div>
              <div class="breed-item-food-start">
                <span
                  ><span class="breed-item-foot-date">{{ dayjs(item.breedStartdate).format('YYYY-MM-DD') }}</span
                  >开始繁育</span
                >
              </div>
            </div>
          </div>
        </el-col>
      </template>
    </el-row>
    <!-- 修改繁育信息对话框 -->
    <el-dialog v-model="dialogVisible" title="修改基地信息" width="30%">
      <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="110px" status-icon>
        <el-form-item label="繁育基地名称" prop="breedName" class="form-item">
          <el-input v-model.trim="ruleForm.breedName" placeholder="请输入繁育基地名称" />
        </el-form-item>
        <el-form-item label="繁育种类" prop="breedkind" class="form-item">
          <el-input v-model.trim="ruleForm.breedKind" placeholder="请输入繁育种类" />
        </el-form-item>
        <el-form-item label="繁育数量" prop="breedCount" class="form-item">
          <el-input v-model.trim="ruleForm.breedCount" type="number" placeholder="请输入繁育数量" />
        </el-form-item>
        <el-form-item label="繁育开始时间" prop="breedStartdate" class="form-item">
          <el-date-picker v-model.trim="ruleForm.breedStartdate" type="date" placeholder="请选择繁育开始时间" style="width: 100%" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-right: 20px" @click="AlterBreed(ruleFormRef)">修改</el-button>
          <el-button type="info" @click="cancelAlter">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 问题对话框 -->
    <el-dialog v-model="matterdialog" :title="matter.breedTitle" width="30%">
      <span>{{ matter.breedContent }}</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="solveProblem(matter)">解决</el-button>
          <el-button @click="matterdialog = false"> 未解决 </el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 遇到问题对话框 -->
    <el-dialog v-model="Hasmatterdialog" title="添加所遇问题" width="30%">
      <el-form ref="hasBreedMatter" :model="hasBreedMatterForm" :rules="rules" label-width="80px" status-icon>
        <el-form-item label="问题简介" prop="breedTitle" class="form-item">
          <el-input v-model.trim="hasBreedMatterForm.breedTitle" placeholder="请输入问题简介" />
        </el-form-item>
        <el-form-item label="问题详情" prop="breedContent" class="form-item">
          <el-input v-model.trim="hasBreedMatterForm.breedContent" type="textarea" maxlength="90" show-word-limit placeholder="请输入问题详情" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="commitMatter(hasBreedMatter)">提交</el-button>
          <!-- 同时设置对话框关闭和清空表单 -->
          <el-button @click=";(Hasmatterdialog = false), clearhasBreedMatterForm()"> 取消 </el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 新增繁育信息对话框 -->
    <el-dialog v-model="Adddialog" title="新增基地信息" width="30%">
      <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="110px" status-icon>
        <el-form-item label="繁育基地名称" prop="breedName" class="form-item">
          <el-input v-model.trim="ruleForm.breedName" placeholder="请输入繁育基地名称" />
        </el-form-item>
        <el-form-item label="繁育种类" prop="breedKind" class="form-item">
          <el-input v-model.trim="ruleForm.breedKind" placeholder="请输入繁育种类" />
        </el-form-item>
        <el-form-item label="繁育数量" prop="breedCount" class="form-item">
          <el-input v-model.trim="ruleForm.breedCount" type="number" placeholder="请输入繁育数量" />
        </el-form-item>
        <el-form-item label="繁育开始时间" prop="breedStartdate" class="form-item">
          <el-date-picker v-model.trim="ruleForm.breedStartdate" type="date" placeholder="请选择繁育开始时间" style="width: 100%" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-right: 20px" @click="addBreedMsg(ruleFormRef)">新增</el-button>
          <el-button type="info" @click="Adddialog = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 图片上传对话框 -->
    <el-dialog v-model="imageUpLoadDialog" title="图片修改" width="30%">
      <el-upload class="upload-demo" drag action="/lambs/image/upload" :data="data" multiple :on-success="imageUpLoadSuccess" :before-upload="beforeAvatarUpload">
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">图片拖拽到此处或者<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip">jpg/png 文件最小500kb</div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<style lang="scss" scoped>
.breed-header {
  display: flex;
  align-items: center;
  .breed-text {
    margin-right: 5px;
  }
}
.breed-top-input {
  .breed-top-input-border {
    display: flex;
    align-items: center;
    .feed-top-input-text {
      margin: 0px 10px;
    }
  }
  .breed-top-right {
    display: flex;
    justify-content: space-between;
    margin: 5px 5px 10px 5px;
  }
}
.el-divider--horizontal {
  margin: 10px 0px;
}
.breed-container {
  .breed-item {
    border: 1px solid skyblue;
    border-radius: 10px;
    box-shadow: 2px 2px 3px skyblue;
    margin-bottom: 10px;
    width: 200px;
    .breed-item-head {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 5px;
      .breed-item-head-text {
        font-size: 18px;
        color: black;
        cursor: pointer; //鼠标悬浮时变成小手
      }
      .breed-item-head-matter {
        color: red;
        cursor: pointer; //鼠标悬浮时变成小手
      }
    }
    .breed-item-body {
      width: 130px;
      height: 110px;
      margin: 5px auto;
      .breed-item-img{
        display: block;
        width: 100%;
        height: 130px;
      }
    }
    .breed-item-food {
      .breed-item-food-head {
        display: flex;
        justify-content: space-evenly;
        margin-top: 20px;
        margin-bottom: 10px;
        font-size: 18px;
      }
      .breed-item-food-start {
        text-align: center;
        .breed-item-foot-date {
          margin-right: 5px;
        }
      }
    }
  }
}
:deep(.el-tooltip__trigger:focus-visible) {
  //去除鼠标悬浮时显示黑边的问题
  outline: unset;
}
</style>
