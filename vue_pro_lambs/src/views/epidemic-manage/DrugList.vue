<script setup>
import { DArrowRight, Search, UploadFilled, InfoFilled } from '@element-plus/icons-vue'
import { onMounted, reactive, ref } from 'vue'
/* 导入日期格式化js */
import dayjs from 'dayjs'
/* 导入用户信息方法 */
import { DrugList, updateDrugMsg, deleteDrugMsg } from '@/apis/drug'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入自定义配置 */
import { drugClassify } from '@/util/other'
import { ElMessage } from 'element-plus'

/* 药品数据对象存储数据 */
const drugTable = ref([])
/* 实例化用户对象 */
const { user } = useUserStore()
/* 抽屉对象 */
const drawer = ref(false)
/* 表单药品对象 */
const ruleFormRef = ref()
/* 表单采购对象 */
const buyFormRef = ref()
/* 表单采购对话框 */
const buyerDialog = ref(false)
/* 图片上传对话框 */
const UploadImg = ref(false)
/* 修改表单信息对话框 */
const alterDrugdia = ref(false)

/* 顶部药品对象 */
const topFeedObject = ref({
  drugName: '',
  category: '',
  specification: '',
  supplier: '',
})
/* 药品和采购对象 */
const drugs = reactive({
  id: '',
  drugName: '',
  drugImg: '',
  category: '',
  specification: '',
  drugCount: '',
  batchNumber: '',
  manufactureDate: '',
  expirationDate: '',
  supplier: '',
  buyerCount: '',
  buyerPrice: '',
  userName: '',
  userId: '',
})

/* 表单药品规则 */
const rules = reactive({
  /* 药品规则 */
  drugName: [{ required: true, message: '请输入药品名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择药品分类', trigger: 'blur' }],
  specification: [{ required: true, message: '请输入药品规格', trigger: 'blur' }],
  drugCount: [{ required: true, message: '请输入药品数量', trigger: 'blur' }],
  batchNumber: [{ required: true, message: '请输入药品批号', trigger: 'blur' }],
  manufactureDate: [{ required: true, message: '请输入生产日期', trigger: 'blur' }],
  expirationDate: [{ required: true, message: '请输入有效期限', trigger: 'blur' }],
  supplier: [{ required: true, message: '请输入供应商', trigger: 'blur' }],
  buyerCount: [{ required: true, message: '请输入采购数量', trigger: 'blur' }],
  buyerPrice: [{ required: true, message: '请输入采购价格', trigger: 'blur' }],
  userName: [{ required: true, message: '请输入采购人员名称', trigger: 'blur' }],
})

/* 生命周期函数、挂载 */
onMounted(() => {
  /* 调用方法获取药品 */
  getDrugList()
})

/* 获取药品方法 */
const getDrugList = async () => {
  /* 临时数据 */
  const data = { userId: user.userId }
  const { data: ref } = await DrugList(data)
  /* 非空判断 */
  if (ref.data == '') {
    ElMessage.info(ref.msg)
  }
  /* 赋值给表单对象 */
  drugTable.value = ref.data
}

/* 点击修改图片按钮 */
const alterImg = (item) => {
  /* 赋值 */
  drugs.id = item.id
  drugs.drugName = item.drugName
  drugs.drugImg = item.drugImg
  drugs.category = item.category
  drugs.specification = item.specification
  drugs.drugCount = item.drugCount
  drugs.batchNumber = item.batchNumber
  drugs.manufactureDate = item.manufactureDate
  drugs.expirationDate = item.expirationDate
  drugs.supplier = item.supplier
  drugs.buyerCount = item.buyerCount
  drugs.buyerPrice = item.buyerPrice
  drugs.userName = item.userName
  drugs.userId = item.userId
  /* 打开对话框 */
  UploadImg.value = true
}

/* 查看详情按钮点击 */
const getDrugs = (item) => {
  /* 将循环的单一对象赋值给药品对象 */
  drugs.id = item.id
  drugs.drugName = item.drugName
  drugs.drugImg = item.drugImg
  drugs.category = item.category
  drugs.specification = item.specification
  drugs.drugCount = item.drugCount
  drugs.batchNumber = item.batchNumber
  drugs.manufactureDate = item.manufactureDate
  drugs.expirationDate = item.expirationDate
  drugs.supplier = item.supplier
  drugs.buyerCount = item.buyerCount
  drugs.buyerPrice = item.buyerPrice
  drugs.userName = item.userName
  drugs.userId = item.userId
  /* 打开抽屉 */
  drawer.value = true
}

/* 点击修改表单信息按钮 */
const alterDrugmessage = () => {
  /* 关闭抽屉 */
  drawer.value = false
  /* 打开对话框，将值传递给对话框 */
  alterDrugdia.value = true
}

/* 修改采购信息按钮点击 */
const alterBuyer = () => {
  /* 关闭抽屉 */
  drawer.value = false
  /* 打开采购信息对话框 */
  buyerDialog.value = true
  /* 数据从抽屉中获取 */
}

/* 取消修改药品按钮点击 */
const cancelAlterDrug = () => {
  /* 调用方法清空表单 */
  clearDrug()
  /* 关闭对话框 */
  alterDrugdia.value = false
}

/* 清空表单 */
const clearDrug = () => {
  (drugs.id = ''),
    (drugs.drugName = ''),
    (drugs.drugImg = ''),
    (drugs.category = ''),
    (drugs.specification = ''),
    (drugs.drugCount = ''),
    (drugs.batchNumber = ''),
    (drugs.manufactureDate = ''),
    (drugs.expirationDate = ''),
    (drugs.supplier = ''),
    (drugs.buyerCount = ''),
    (drugs.buyerPrice = ''),
    (drugs.userName = ''),
    (drugs.userId = '')
}

/* 头部查询按钮点击 */
const selectDrug = (item) => {
  /* 非空判断 */
  if (item.drugName == '' && item.category == '' && item.specification == '' && item.supplier == '') {
    /* 刷新页面 */
    getDrugList()
  }
  /* 过滤对象 */
  drugTable.value = drugTable.value.filter((value) =>
    item.drugName != '' ? item.drugName == value.drugName : '' || item.category != '' ? item.category == value.category : '' || item.specification != '' ? item.specification == value.specification : '' || item.supplier != '' ? item.supplier == value.supplier : ''
  )
}

/* 点击了修改药品信息按钮 */
const updateDrugY = async (item, formEl) => {
  /* 对其中内容进行检查 */
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 调用方法 */
      const { data: ref } = await updateDrugMsg(item)
      if (ref.code > 0) {
        /* 刷新页面 */
        getDrugList()
        /* 表单清空 */
        clearDrug()
        /* 关闭对话框 */
        alterDrugdia.value = false
        ElMessage.success(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}

/* 点击修改采购信息按钮 */
const updateDrugB = async (item, formEl) => {
  /* 非空检查 */
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 调用方法 */
      const { data: ref } = await updateDrugMsg(item)
      if (ref.code > 0) {
        /* 刷新页面 */
        getDrugList()
        /* 表单清空 */
        clearDrug()
        /* 关闭对话框 */
        buyerDialog.value = false
        ElMessage.success(ref.msg)
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}

/* 点击了删除按钮 */
const deleteDrug = async (item) => {
  /* 调用方法删除 */
  const { data: ref } = await deleteDrugMsg(item)
  if (ref.code > 0) {
    /* 调用方法刷新页面 */
    getDrugList()
    /* 表单清空 */
    clearDrug()
    /* 关闭抽屉 */
    drawer.value = false
    ElMessage.success(ref.msg)
  } else {
    ElMessage.info(ref.msg)
  }
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

/* 图片上传成功后回调 */
const imageUpLoadSuccess = async (response) => {
  /* 临时赋值 */
  drugs.drugImg = response.data
  /* 调用方法修改 */
  const { data: ref } = await updateDrugMsg(drugs)
  /* 判断是否成功 */
  if (ref.code > 0) {
    /* 刷新页面 */
    getDrugList()
    /* 表单清空 */
    clearDrug()
    /* 关闭对话框 */
    UploadImg.value = false
    ElMessage.success(ref.msg)
  } else {
    ElMessage.info(ref.msg)
  }
}

/* 显示图片信息 */
const showImg = (item) => {
  return `/lambs/image/download?name=${item.drugImg}&token=`+localStorage.getItem('token')
}

/* 上传图片 */
const data = {
  token: localStorage.getItem('token'),
}
</script>

<template>
  <!-- 头部 -->
  <div class="drug-header">
    <h3 class="drug-text">药品列表</h3>
    <el-icon style="margin-top: 3px"><DArrowRight /></el-icon>
  </div>
  <!-- 第一行 -->
  <el-row>
    <el-col :span="24">
      <span class="drug-search-text">简单搜索</span>
      <div class="drug-search">
        <div class="drug-input">
          <span class="drug-input-text">药品名称</span>
          <el-input v-model="topFeedObject.drugName" clearable placeholder="请输入药品名称" :prefix-icon="Search" style="width: 160px" />
          <span class="drug-input-text">药品分类</span>
          <el-select v-model="topFeedObject.category" clearable class="m-2" placeholder="请选择分类">
            <el-option v-for="item in drugClassify" :key="item.id" :label="item.label" :value="item.value" />
          </el-select>
          <span class="drug-input-text">药品规格</span>
          <el-input v-model="topFeedObject.specification" clearable placeholder="请输入药品规格" :prefix-icon="Search" style="width: 160px" />
          <span class="drug-input-text">药品供应商</span>
          <el-input v-model="topFeedObject.supplier" clearable placeholder="请输入药品供应商" :prefix-icon="Search" style="width: 180px" />
        </div>
        <div class="drug-button">
          <el-button type="primary" :icon="Search" @click="selectDrug(topFeedObject)">查询</el-button>
        </div>
      </div>
    </el-col>
  </el-row>

  <!-- 第二行 -->
  <el-scrollbar height="508px">
    <el-row class="sence-row">
      <!-- for循环输出内容 -->
      <template v-for="item in drugTable" :key="item.id">
        <el-col :span="4">
          <div class="sence-row-container">
            <el-card :body-style="{ padding: '0px' }" style="border-radius: 10px">
              <!-- 图片信息 -->
              <el-image :src="showImg(item)" class="sence-row-img" />
              <div>
                <div class="sence-body">
                  <span>{{ item.drugName }}</span>
                  <span>库存:{{ item.drugCount }}</span>
                </div>
                <div class="sence-bottom">
                  <el-button type="primary" round size="small" @click="alterImg(item)">修改图片</el-button>
                  <el-button type="primary" round size="small" @click="getDrugs(item)">点击查看详情</el-button>
                </div>
              </div>
            </el-card>
          </div>
        </el-col>
      </template>
    </el-row>
  </el-scrollbar>

  <!-- 抽屉 -->
  <el-drawer v-model="drawer" direction="rtl">
    <template #header>
      <h4>药品详情</h4>
    </template>
    <template #default>
      <!-- 详细信息垂直展示 -->
      <el-descriptions direction="vertical">
        <el-descriptions-item label="药品名称"
          ><el-tag size="small">{{ drugs.drugName }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="药品分类"
          ><el-tag size="small">{{ drugs.category }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="药品规格"
          ><el-tag size="small">{{ drugs.specification }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="库存药品数量"
          ><el-tag size="small">{{ drugs.drugCount }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="药品批号"
          ><el-tag size="small">{{ drugs.batchNumber }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="生产日期"
          ><el-tag size="small">{{ dayjs(drugs.manufactureDate).format('YYYY-MM-DD') }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="有效期限"
          ><el-tag size="small">{{ dayjs(drugs.expirationDate).format('YYYY-MM-DD') }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="供应商"
          ><el-tag size="small">{{ drugs.supplier }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="采购人员名称"
          ><el-tag size="small">{{ drugs.userName }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="采购数量"
          ><el-tag size="small">{{ drugs.buyerCount }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="采购价格"
          ><el-tag size="small">{{ drugs.buyerPrice }}</el-tag></el-descriptions-item
        >
      </el-descriptions>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="alterDrugmessage(drugs)">修改药品信息</el-button>
        <el-button type="primary" @click="alterBuyer(drugs)">修改采购信息</el-button>
        <el-popconfirm width="220" confirm-button-text="确认删除" cancel-button-text="取消删除" :icon="InfoFilled" icon-color="#626AEF" title="确认删除该药品吗?" :hide-after="0" @confirm="deleteDrug(drugs)">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </div>
    </template>
  </el-drawer>

  <!-- 修改药品信息对话框 -->
  <el-dialog v-model="alterDrugdia" title="修改药品信息" width="25%">
    <el-form ref="ruleFormRef" :model="drugs" :rules="rules" label-width="110px" status-icon :inline="true">
      <el-form-item label="药品名称" prop="drugName" class="form-item">
        <el-input v-model.trim="drugs.drugName" placeholder="请输入药品名称" />
      </el-form-item>
      <el-form-item label="药品分类" prop="category" class="form-item">
        <el-select v-model="drugs.category" clearable class="m-2" placeholder="请选择分类">
          <el-option v-for="item in drugClassify" :key="item.id" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="药品规格" prop="specification" class="form-item">
        <el-input v-model.trim="drugs.specification" placeholder="请输入药品规格" />
      </el-form-item>
      <el-form-item label="药品数量" prop="drugCount" class="form-item">
        <el-input v-model.trim="drugs.drugCount" placeholder="请输入药品数量" />
      </el-form-item>
      <el-form-item label="药品批号" prop="batchNumber" class="form-item">
        <el-input v-model.trim="drugs.batchNumber" placeholder="请输入药品批号" />
      </el-form-item>
      <el-form-item label="生产日期" prop="manufactureDate" class="form-item">
        <el-input v-model.trim="drugs.manufactureDate" type="number" placeholder="请输入生产日期" />
      </el-form-item>
      <el-form-item label="有效期限" prop="expirationDate">
        <el-input v-model.trim="drugs.expirationDate" placeholder="请输入有效期限" />
      </el-form-item>
      <el-form-item label="供应商" prop="supplier">
        <el-input v-model.trim="drugs.supplier" placeholder="请输入供应商" />
      </el-form-item>
      <div class="dialog-footer">
        <el-button @click="cancelAlterDrug">取消</el-button>
        <el-button type="primary" @click="updateDrugY(drugs, ruleFormRef)"> 修改 </el-button>
      </div>
    </el-form>
  </el-dialog>

  <!-- 修改采购信息对话框 -->
  <el-dialog v-model="buyerDialog" title="修改采购信息" width="25%" @close="clearDrug()">
    <el-form ref="buyFormRef" :model="drugs" :rules="rules" label-width="110px" status-icon :inline="true">
      <el-form-item label="采购数量" prop="buyerCount">
        <el-input v-model.trim="drugs.buyerCount" placeholder="请输入采购数量" />
      </el-form-item>
      <el-form-item label="采购价格" prop="buyerPrice">
        <el-input v-model.trim="drugs.buyerPrice" placeholder="请输入采购价格" />
      </el-form-item>
      <el-form-item label="采购员名" prop="userName">
        <el-input v-model.trim="drugs.userName" placeholder="请输入采购员姓名" />
      </el-form-item>
      <div class="dialog-footer">
        <el-button type="primary" @click="updateDrugB(drugs, buyFormRef)"> 修改采购信息 </el-button>
      </div>
    </el-form>
  </el-dialog>

  <!-- 图片上传对话框 -->
  <el-dialog v-model="UploadImg" title="图片上传" width="25%">
    <!-- 图片上传 -->
    <el-upload drag action="/lambs/image/upload" :data="data" multiple :on-success="imageUpLoadSuccess" :before-upload="beforeAvatarUpload">
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">拖入图片或者 <em>点击上传</em></div>
      <template #tip>
        <div class="el-upload__tip">jpg/png文件大小为500kb</div>
      </template>
    </el-upload>
  </el-dialog>
</template>

<style lang="scss" scoped>
.drug-header {
  display: flex;
  align-items: center;
  margin-top: -15px;
  .drug-text {
    margin-right: 5px;
  }
}
.drug-search-text {
  font-weight: bold;
  display: inline-block;
  margin-bottom: 10px;
}
.drug-search {
  display: flex;
  justify-content: space-between;
  margin: 0px 8px 0px 0px;
  .drug-input {
    .drug-input-text {
      margin: 0px 8px 0px 8px;
    }
  }
}
.sence-row {
  margin: 10px;
  .sence-row-container {
    border: 1px solid skyblue;
    margin-right: 5px;
    box-shadow: 1px 1px 2px skyblue;
    border-radius: 10px;
    margin-bottom: 5px;
    .sence-row-img {
      width: 100%;
      height: 140px;
    }
    .sence-body {
      display: flex;
      justify-content: space-between;
    }
    .sence-bottom {
      display: flex;
      justify-content: space-between;
    }
  }
}
.dialog-footer {
  text-align: center;
}
</style>
