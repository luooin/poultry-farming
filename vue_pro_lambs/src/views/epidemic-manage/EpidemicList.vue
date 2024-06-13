<script setup>
import { DArrowRight, Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { onMounted, reactive, ref } from 'vue'
/* 导入dayjs */
import dayjs from 'dayjs'
/* 导入自定义配置 */
import { drugClassify } from '@/util/other'
/* 导入自定义方法 */
import { addDrugMSg } from '@/apis/drug'
/* 导入用户方法 */
import { UserList } from '@/apis/users'
/* 导入医疗器械方法 */
import { addMedical, MedicalList, updateMedical } from '@/apis/medical'
/* 导入用户信息 */
import { useUserStore } from '@/stores/UserStore'

/* 实例化用户对象 */
const { user } = useUserStore()
/* 表单药品对象 */
const ruleFormRef = ref()
/* 表单器械对象 */
const medicalFormRef = ref()
/* 表单借出信息对象 */
const medicalLendFormRef = ref()
/* 图片上传对象 */
const imageUrl = ref('')
/* 表格医疗器械列表 */
const tableMedicalData = ref([])
/* 新增借出信息用户 */
const LendUser = ref([])
/* 新增借出信息用户列表 */
const tableLendUser = ref([])

/* 药品对象 */
const ruleForm = reactive({
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

/* 医疗器械对象 */
const medicalForm = reactive({
  medicalName: '',
  medicalType: '',
  medicalFirm: '',
  medicalCount: '',
  medicalPrice: '',
  buyTime: '',
  userId: '',
})

/* 借出信息对象 */
const medicalLendForm = reactive({
  medicalName: '',
  isLend: '',
  lendUserId: '',
  lendTime: '',
  lendCount: '',
  returnCount: '',
  isMar: '',
  userId: '',
})

/* 定义校验数字不能小于0的规则 */
const checkNumber = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('请输入规定内容'))
  } else {
    if (value < 1) {
      callback(new Error('必须大于或者等于1'))
    } else {
      callback()
    }
  }
}

/* 表单药品、医疗规则 */
const rules = reactive({
  /* 药品规则 */
  drugName: [{ required: true, message: '请输入药品名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择药品分类', trigger: 'blur' }],
  specification: [{ required: true, message: '请输入药品规格', trigger: 'blur' }],
  drugCount: [{ required: true, validator: checkNumber, trigger: 'blur' }],
  batchNumber: [{ required: true, message: '请输入药品批号', trigger: 'blur' }],
  manufactureDate: [{ required: true, message: '请输入生产日期', trigger: 'blur' }],
  expirationDate: [{ required: true, message: '请输入有效期限', trigger: 'blur' }],
  supplier: [{ required: true, message: '请输入供应商', trigger: 'blur' }],
  buyerCount: [{ required: true, validator: checkNumber, trigger: 'blur' }],
  buyerPrice: [{ required: true, message: '请输入采购价格', trigger: 'blur' }],
  userName: [{ required: true, message: '请输入采购人员名称', trigger: 'blur' }],
  /* 器械规则 */
  medicalName: [{ required: true, message: '请输入器械名称', trigger: 'blur' }],
  medicalType: [{ required: true, message: '请输入器械类型', trigger: 'blur' }],
  medicalFirm: [{ required: true, message: '请输入器械厂商', trigger: 'blur' }],
  medicalCount: [{ required: true, validator: checkNumber, trigger: 'blur' }],
  medicalPrice: [{ required: true, message: '请输入器械价格', trigger: 'blur' }],
  buyTime: [{ required: true, message: '请选择购买时间', trigger: 'blur' }],
  isLend: [{ required: true, message: '请选择是否借出', trigger: 'blur' }],
  lendUserId: [{ required: true, message: '请选择借出用户', trigger: 'blur' }],
  lendTime: [{ required: true, message: '请选择借出时间', trigger: 'blur' }],
  lendCount: [{ required: true, validator: checkNumber, trigger: 'blur' }],
  returnCount: [{ required: true, validator: checkNumber, trigger: 'blur' }],
  isMar: [{ required: true, message: '请选择是否损坏', trigger: 'blur' }],
})

/* 生命周期函数,挂载 */
onMounted(() => {
  /* 获取器械列表 */
  getMedicalList()
  /* 获取用户信息 */
  getUserList()
})

/* 获取器械列表方法 */
const getMedicalList = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await MedicalList(data)
  tableMedicalData.value = ref.data
  /* 过滤器械借出信息 */
  tableLendUser.value = ref.data.filter((item) => item.isLend == 1)
}

/* 获取用户信息 */
const getUserList = async () => {
  const { data: ref } = await UserList()
  LendUser.value = ref.data
}

/* 防疫药品点击新增按钮 */
const addEpidemic = (formEl) => {
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 临时参数 */
      ruleForm.userId = user.userId
      const { data: ref } = await addDrugMSg(ruleForm)
      /* 判断状态 */
      if (ref.code > 0) {
        /* 清空表单药品数据 */
        cleanDrugData()
        ElMessage.success(ref.msg)
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请填写完整')
    }
  })
}

/* 清空表单药品数据 */
const cleanDrugData = () => {
  (ruleForm.drugName = ''),
    (ruleForm.drugImg = ''),
    (ruleForm.category = ''),
    (ruleForm.specification = ''),
    (ruleForm.drugCount = ''),
    (ruleForm.batchNumber = ''),
    (ruleForm.manufactureDate = ''),
    (ruleForm.expirationDate = ''),
    (ruleForm.supplier = ''),
    (ruleForm.buyerCount = ''),
    (ruleForm.buyerPrice = ''),
    (ruleForm.userName = ''),
    (ruleForm.userId = ''),
    (imageUrl.value = '')
}

/* 清空借出信息表单数据 */
const cleanLendData = () => {
  (medicalLendForm.isLend = ''), (medicalLendForm.lendUserId = ''), (medicalLendForm.lendTime = ''), (medicalLendForm.lendCount = ''), (medicalLendForm.returnCount = ''), (medicalLendForm.isMar = ''), (medicalLendForm.userId = ''), (medicalLendForm.medicalName = '')
}
/* 清空表单医疗器械数据 */
const cleanMedicalData = () => {
  (medicalForm.medicalName = ''), (medicalForm.medicalType = ''), (medicalForm.medicalFirm = ''), (medicalForm.medicalCount = ''), (medicalForm.medicalPrice = ''), (medicalForm.buyTime = ''), (medicalForm.userId = '')
}

/* 图片上传成功后的函数 */
const handleUploadSuccess = (response) => {
  /* 图片上传成功后后端返回图片名称,前端进行获取图片名称赋值给药品对象 */
  ruleForm.drugImg = response.data
  imageUrl.value = `/lambs/image/download?name=${response.data}&token=`+localStorage.getItem('token')
  ElMessage.success('药品图片上传成功')
}

/* 点击新增医疗器械按钮 */
const addMedicalMsg = (formEl) => {
  /* 非空校验 */
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 临时参数 */
      medicalForm.userId = user.userId
      /* 调用方法 */
      const { data: ref } = await addMedical(medicalForm)
      //是否成功判断
      if (ref.code > 0) {
        /* 清空医疗器械表单 */
        cleanMedicalData()
        /* 刷新器械列表 */
        getMedicalList()
        ElMessage.success(ref.msg)
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请填写相应的消息')
    }
  })
}

/* 新增借出信息 */
const addLendMsg = (formEl) => {
  /* 非空校验 */
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 临时数据 */
      medicalLendForm.isLend = '1'
      medicalLendForm.userId = user.userId
      console.log(medicalLendForm)
      const { data: ref } = await updateMedical(medicalLendForm)
      /* 是否成功 */
      if (ref.code > 0) {
        /* 清空数据 */
        cleanLendData()
        ElMessage.success('新增成功')
      } else {
        ElMessage.warning(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}
/* 上传图片数据 */
const data={
  token:localStorage.getItem('token')
}
</script>

<template>
  <!-- 头部 -->
  <div class="epidemic-header">
    <h3 class="epidemic-text">健康新增列表</h3>
    <el-icon style="margin-top: 3px"><DArrowRight /></el-icon>
  </div>
  <el-scrollbar height="570px">
    <!-- 防疫药品控制台 -->
    <div class="epidemic-row1">
      <el-row>
        <el-col :span="24">
          <div class="epidemic-controller">
            <!-- 新增防疫药品 -->
            <span class="epidemic-row1-text">新增防疫药品</span>
            <el-row>
              <el-col :span="21">
                <!-- 新增防疫药品表单 -->
                <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="110px" status-icon :inline="true">
                  <el-form-item label="药品名称" prop="drugName" class="form-item">
                    <el-input v-model.trim="ruleForm.drugName" placeholder="请输入药品名称" />
                  </el-form-item>
                  <el-form-item label="药品分类" prop="category" label-width="85px">
                    <el-select v-model="ruleForm.category" clearable placeholder="请选择分类">
                      <el-option v-for="item in drugClassify" :key="item.id" :label="item.label" :value="item.value" size="small" />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="药品规格" prop="specification" class="form-item">
                    <el-input v-model.trim="ruleForm.specification" placeholder="请输入药品规格" />
                  </el-form-item>
                  <el-form-item label="药品数量" prop="drugCount" class="form-item">
                    <el-input v-model.trim="ruleForm.drugCount" type="number" placeholder="请输入药品数量" />
                  </el-form-item>
                  <el-form-item label="药品批号" prop="batchNumber" class="form-item">
                    <el-input v-model.trim="ruleForm.batchNumber" placeholder="请输入药品批号" />
                  </el-form-item>
                  <el-form-item label="生产日期" prop="manufactureDate" class="form-item">
                    <el-date-picker v-model="ruleForm.manufactureDate" type="date" placeholder="请选择生产日期" />
                  </el-form-item>
                  <el-form-item label="有效期限" prop="expirationDate">
                    <el-date-picker v-model="ruleForm.expirationDate" type="date" placeholder="请选择有效期限" />
                  </el-form-item>
                  <el-form-item label="供应商" prop="supplier">
                    <el-input v-model.trim="ruleForm.supplier" placeholder="请输入供应商" />
                  </el-form-item>
                  <el-form-item label="采购数量" prop="buyerCount">
                    <el-input v-model.trim="ruleForm.buyerCount" type="number" placeholder="请输入采购数量" />
                  </el-form-item>
                  <el-form-item label="采购价格" prop="buyerPrice">
                    <el-input v-model.trim="ruleForm.buyerPrice" type="number" placeholder="请输入采购价格" />
                  </el-form-item>
                  <el-form-item label="采购员名" prop="userName">
                    <el-input v-model.trim="ruleForm.userName" placeholder="请输入采购员姓名" />
                  </el-form-item>
                </el-form>
              </el-col>
              <el-col :span="3">
                <!-- 图片上传 -->
                <div class="epidemic-upload">
                  <span class="epidemic-upload-text">药品图片上传</span>
                  <el-upload action="/lambs/image/upload" :data="data" :show-file-list="false" :on-success="handleUploadSuccess">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                  </el-upload>
                </div>
                <div class="epidemic-button">
                  <el-button type="primary" style="margin-right: 20px" :icon="Plus" @click="addEpidemic(ruleFormRef)">新增</el-button>
                  <el-button type="info" style="margin-right: 20px" @click="cleanDrugData()">清空</el-button>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 新增医疗器械 -->
    <div class="epidemic-row2">
      <el-row>
        <!-- 新增器械 -->
        <el-col :span="14">
          <div class="epidemic-row2-container">
            <span class="epidemic-row2-text">新增医疗器械</span>
            <el-form ref="medicalFormRef" :model="medicalForm" :rules="rules" label-width="110px" status-icon :inline="true">
              <el-form-item label="器械名称" prop="medicalName" class="form-item">
                <el-input v-model.trim="medicalForm.medicalName" placeholder="请输入器械名称" />
              </el-form-item>
              <el-form-item label="器械类型" prop="medicalType" class="form-item">
                <el-input v-model.trim="medicalForm.medicalType" placeholder="请输入器械类型" />
              </el-form-item>
              <el-form-item label="器械厂商" prop="medicalFirm" class="form-item">
                <el-input v-model.trim="medicalForm.medicalFirm" placeholder="请输入器械厂商" />
              </el-form-item>
              <el-form-item label="器械数量" prop="medicalCount" class="form-item">
                <el-input v-model.trim="medicalForm.medicalCount" type="number" placeholder="请输入器械数量" />
              </el-form-item>
              <el-form-item label="器械价格" prop="medicalPrice" class="form-item">
                <el-input v-model.trim="medicalForm.medicalPrice" type="number" placeholder="请输入器械价格" />
              </el-form-item>
              <el-form-item label="购买时间" prop="buyTime" class="form-item">
                <el-date-picker v-model="medicalForm.buyTime" type="date" placeholder="请选择购买时间" />
              </el-form-item>
              <el-form-item style="margin-left: 200px">
                <el-button type="primary" @click="addMedicalMsg(medicalFormRef)">新增</el-button>
                <el-button @click="cleanMedicalData()">清空</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <!-- 新增医疗器械简介 -->
        <el-col :span="10">
          <div class="epidemic-introduce">
            <el-table :data="tableMedicalData" height="231px" :default-sort="{ prop: 'medicalCount', order: 'descending' }">
              <el-table-column prop="medicalName" label="器械名称" />
              <el-table-column prop="medicalType" label="器械类型" />
              <el-table-column prop="medicalPrice" sortable label="器械价格" />
              <el-table-column prop="medicalCount" sortable label="器械数量" />
            </el-table>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 新增借出信息 -->
    <div class="epidemic-row3">
      <el-row>
        <el-col :span="14">
          <div class="epidemic-row3-container">
            <span class="epidemic-row3-text">新增借出信息</span>
            <el-form ref="medicalLendFormRef" :model="medicalLendForm" :rules="rules" label-width="110px" status-icon :inline="true">
              <el-form-item label="借出器械" prop="medicalName" style="width: 310px">
                <el-select v-model="medicalLendForm.medicalName" placeholder="请选择借出器械" clearable>
                  <el-option v-for="item in tableMedicalData" :key="item.id" :label="item.medicalName" :value="item.medicalName" />
                </el-select>
              </el-form-item>
              <el-form-item label="借出用户" prop="lendUserId" class="form-item">
                <el-select v-model="medicalLendForm.lendUserId" placeholder="请选择借出用户" clearable>
                  <el-option v-for="item in LendUser" :key="item.id" :label="item.username" :value="item.id" />
                </el-select>
              </el-form-item>
              <el-form-item label="借出时间" prop="lendTime" style="width: 310px">
                <el-date-picker v-model="medicalLendForm.lendTime" type="date" placeholder="请选择借出时间" />
              </el-form-item>
              <el-form-item label="借出数量" prop="lendCount" class="form-item">
                <el-input v-model.trim="medicalLendForm.lendCount" type="number" placeholder="请输入借出数量" />
              </el-form-item>
              <el-form-item label="归还数量" prop="returnCount" style="width: 310px">
                <el-input v-model.trim="medicalLendForm.returnCount" type="number" placeholder="请输入归还数量" />
              </el-form-item>
              <el-form-item label="是否损坏" prop="isMar" class="form-item">
                <el-radio-group v-model="medicalLendForm.isMar">
                  <el-radio label="0">损坏</el-radio>
                  <el-radio label="1">未损坏</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item style="margin-left: 200px">
                <el-button type="primary" @click="addLendMsg(medicalLendFormRef)">新增</el-button>
                <el-button @click="cleanLendData()">清空</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="epidemic-introduce">
            <el-table :data="tableLendUser" height="231px">
              <el-table-column prop="medicalName" label="器械名称" />
              <el-table-column label="借出时间">
                <template #default="scope">
                  <span>{{ dayjs(scope.row.lendTime).format('YYYY-MM-DD') }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="lendCount" label="借出数量" />
              <el-table-column prop="returnCount" label="归还数量" />
              <el-table-column label="是否损坏">
                <template #default="scope">
                  <span>{{ scope.row.isMar==0? '损坏':'未损坏'}}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
    </div>
  </el-scrollbar>
</template>

<style lang="scss" scoped>
.epidemic-header {
  display: flex;
  align-items: center;
  margin-top: -15px;
  .epidemic-text {
    margin-right: 5px;
  }
}
.epidemic-row1 {
  .epidemic-controller {
    border: 1px solid skyblue;
    border-radius: 10px;
    box-shadow: 1px 1px 2px skyblue;
    margin-right: 5px;
    .epidemic-row1-text {
      display: block;
      font-weight: bold;
      margin: 5px;
    }
  }
}
.epidemic-upload {
  .epidemic-upload-text {
    display: block;
    font-size: 14px;
    margin: -10px 0px 5px 0px;
    color: rgb(96, 98, 102);
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 140px;
    height: 140px;
    text-align: center;
    border: 1px solid skyblue;
  }
  .avatar {
    width: 98%;
    height: auto;
  }
}

.epidemic-button {
  margin-top: 5px;
  display: flex;
  margin-left: -18px;
}
.epidemic-row2 {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  margin-top: 10px;
  margin-right: 5px;
  overflow: hidden;
  .epidemic-row2-container {
    border-right: 1px solid skyblue;
    .epidemic-row2-text {
      display: block;
      font-weight: bold;
      margin: 5px;
    }
  }
}
.epidemic-row3 {
  margin-top: 10px;
  border: 1px solid skyblue;
  border-radius: 10px;
  overflow: hidden;
  margin-right: 5px;
  box-shadow: 1px 1px 2px skyblue;
  .epidemic-row3-container {
    border-right: 1px solid skyblue;
    .epidemic-row3-text {
      display: block;
      font-weight: bold;
      margin: 5px;
    }
  }
}
</style>
