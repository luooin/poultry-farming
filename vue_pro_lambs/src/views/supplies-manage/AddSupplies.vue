<script setup>
/* 导入环形图组件 */
import { DArrowRight, Search, Plus } from '@element-plus/icons-vue'
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
/* 导入物资方法 */
import { SuppliesList, deleteSupplies, updateSupplies, addSuppliesBySupplies } from '@/apis/supplies'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 修改按钮显示隐藏 */
const showBtn = ref(false)
/* 创建物资对象用于存储返回数据 */
const suppliesData = ref([])
/* 表单物资对象 */
const ruleFormRef = ref()
/* 物资对象 */
const ruleForm = reactive({
  id:'',
  suppliesName: '',
  suppliesUnit: '',
  suppliesCount: '',
  suppliesPrice: '',
  provider: '',
  suppliesAttn: '',
  attnPhone: '',
  isMar: '',
  suppliesCause: '',
  marCount: '',
  userId: '',
})
/* 查找物资对象 */
const sendform = reactive({
  suppliesName: '',
  suppliesCount: '',
  suppliesPrice: '',
  attnPhone: '',
  provider: '',
  suppliesAttn: '',
})
/* 表单物资规则 */
const rules = reactive({
  suppliesName: [{ required: true, message: '请输入物资名称', trigger: 'blur' }],
  suppliesUnit: [{ required: true, message: '请输入物资单位', trigger: 'blur' }],
  suppliesCount: [{ required: true, message: '请输入物资数量', trigger: 'blur' }],
  suppliesPrice: [{ required: true, message: '请输入物资价格', trigger: 'blur' }],
  provider: [{ required: true, message: '请输入供应商', trigger: 'blur' }],
  suppliesAttn: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
  attnPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  isMar: [{ required: true, message: '请选择是否损坏', trigger: 'blur' }],
})

/* 钩子函数,挂载时调用获取物资方法 */
onMounted(() => {
  getSuppliesList()
})
/* 获取物资列表方法,使用async和await解构axios数据 */
const getSuppliesList = async () => {
  /* 定义用户id */
  const param = {
    userId: user.userId,
  }
  /* 获取物资方法 */
  const { data: ref } = await SuppliesList(param)
  /* 赋值 */
  suppliesData.value = ref.data
}

/* 采用计算属性计算对应的数量和价格 */
const suppliesCount = computed(() => {
  let supplies = 0
  suppliesData.value.forEach((item) => (supplies += parseInt(item.suppliesCount)))
  return supplies
})
const suppliesPrice = computed(() => {
  let supplies = 0
  suppliesData.value.forEach((item) => (supplies += parseFloat(item.suppliesPrice)))
  return supplies
})
const suppliesBadCount = computed(() => {
  let supplies = 0
  suppliesData.value.forEach((item) => (item.isMar === '1' ? '' : (supplies += parseInt(item.marCount))))
  return supplies
})
/* 联系人和供货商数量相同 */
const provider = computed(() => {
  let supplies = 0
  suppliesData.value.forEach((item) => (supplies += parseFloat(item.userId)))
  return supplies
})

/* 点击了查询按钮 */
const findSupplies = async (item) => {
  /* 非空判断 */
  if (item.suppliesName == '' && item.suppliesCount == '' && item.suppliesPrice == '' && item.attnPhone == '' && item.provider == '' && item.suppliesAttn == '') {
    /* 调用方法刷新页面 */
    getSuppliesList()
  } else {
    /* 通过条件过滤数据并赋值给表格 */
    suppliesData.value = suppliesData.value.filter((val) => val.attnPhone == item.attnPhone || val.provider == item.provider || val.suppliesAttn == item.suppliesAttn || val.suppliesCount == item.suppliesCount || val.suppliesName == item.suppliesName || val.suppliesPrice == item.suppliesPrice)
  }
}
/* 清空查询表单 */
const emptyForm = () => {
  (ruleForm.suppliesName = ''), (ruleForm.suppliesUnit = ''), (ruleForm.suppliesCount = ''), (ruleForm.suppliesPrice = ''), (ruleForm.provider = ''), (ruleForm.suppliesAttn = ''), (ruleForm.attnPhone = ''), (ruleForm.isMar = ''), (ruleForm.suppliesCause = ''), (ruleForm.userId = '')
  /* 修改按钮隐藏 */
  showBtn.value = false
}
/* 新增物资 */
const addSupplies = (formEl) => {
  ruleForm.userId = user.userId
  formEl.validate(async (vallid) => {
    if (vallid) {
      const { data: ref } = await addSuppliesBySupplies(ruleForm)
      /* 判断返回状态 */
      if (ref.code > 0) {
        ElMessage.success(ref.msg)
        /* 调用方法刷新页面 */
        getSuppliesList()
        /* 清空数据 */
        cleanSupplies()
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}
/* 表格中删除 */
const handleSuppDelete = async (item) => {
  const { data: ref } = await deleteSupplies(item)
  /* 判断是否删除成功 */
  if (ref.code > 0) {
    ElMessage.success(ref.msg)
    /* 调用方法刷新页面 */
    getSuppliesList()
  } else {
    ElMessage.success(ref.msg)
  }
}
/* 表格中修改按钮点击 */
const alterbtnShow = (item) => {
  /* 将指定的值赋值给表单 */
  (ruleForm.suppliesName = item.suppliesName), (ruleForm.suppliesUnit = item.suppliesUnit)
  ruleForm.suppliesCount = item.suppliesCount
  ruleForm.suppliesPrice = item.suppliesPrice
  ruleForm.provider = item.provider
  ruleForm.suppliesAttn = item.suppliesAttn
  ruleForm.attnPhone = item.attnPhone
  ruleForm.isMar = item.isMar
  ruleForm.suppliesCause = item.suppliesCause
  ruleForm.userId = item.userId
  ruleForm.id = item.id
  ruleForm.marCount = item.marCount
  /* 修改按钮显示 */
  showBtn.value = true
}
/* 控制台中的修改按钮点击 */
const alterSupplies = async (item) => {
  const { data: ref } = await updateSupplies(item)
  /* 判断返回状态 */
  if (ref.code > 0) {
    ElMessage.success(ref.msg)
    /* 调用方法刷新页面 */
    getSuppliesList()
  } else {
    ElMessage.info(ref.msg)
  }
}

/* 清空数据方法 */
const cleanSupplies = () => {
  ruleForm.suppliesName = ''
  ;(ruleForm.suppliesUnit = ''), (ruleForm.suppliesCount = ''), (ruleForm.suppliesPrice = ''), (ruleForm.provider = ''), (ruleForm.suppliesAttn = ''), (ruleForm.attnPhone = ''), (ruleForm.isMar = ''), (ruleForm.suppliesCause = ''), (ruleForm.userId = '')
}
</script>

<template>
  <!-- 头部 -->
  <div class="supp-top-text">
    <h3 class="supp-text">添加物资</h3>
    <el-icon><DArrowRight /></el-icon>
  </div>
  <!-- 上部 -->
  <el-row class="supp-top-container">
    <el-col :span="5">
      <!-- 物资总数量、损坏数量、物资总价格 -->
      <div class="supp-top-left">
        <!-- 描述组件 -->
        <el-descriptions direction="vertical" title="简述">
          <el-descriptions-item label="物资总数量"
            ><el-tag size="small">{{ suppliesCount }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="物资总价格"
            ><el-tag size="small">{{ suppliesPrice }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="未损坏数量"
            ><el-tag size="small">{{ suppliesBadCount }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="供货商数量"
            ><el-tag size="small">{{ provider }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="联系人数量"
            ><el-tag size="small">{{ provider }}</el-tag></el-descriptions-item
          >
        </el-descriptions>
      </div>
    </el-col>
    <el-col :span="19">
      <div class="supp-top-center">物资控制台</div>
      <el-row>
        <el-col :span="7">
          <div class="supp-top-form-left">
            <span class="supp-top-form-span">查找</span>
            <!-- 查找 -->
            <div class="supp-top-form-send">
              <span class="supp-top-form-send-text">物资名称</span>
              <el-input v-model="sendform.suppliesName" clearable placeholder="请输入物资名称" :prefix-icon="Search" style="width: 200px; margin: 0px 0px 5px 10px" />
              <span class="supp-top-form-send-text">物资数量</span>
              <el-input v-model="sendform.suppliesCount" clearable placeholder="请输入物资数量" :prefix-icon="Search" style="width: 200px; margin: 0px 0px 5px 10px" />
              <span class="supp-top-form-send-text">物资价格</span>
              <el-input v-model="sendform.suppliesPrice" clearable placeholder="请输入物资价格" :prefix-icon="Search" style="width: 200px; margin: 0px 0px 5px 10px" />
              <span class="supp-top-form-send-text">联系电话</span>
              <el-input v-model="sendform.attnPhone" clearable placeholder="请输入联系电话" :prefix-icon="Search" style="width: 200px; margin: 0px 0px 5px 10px" />
              <span class="supp-top-form-send-text">供货商</span>
              <el-input v-model="sendform.provider" clearable placeholder="请输入供货商" :prefix-icon="Search" style="width: 200px; margin: 0px 0px 5px 10px" />
              <span class="supp-top-form-send-text">联系人</span>
              <el-input v-model="sendform.suppliesAttn" clearable placeholder="请输入联系人" :prefix-icon="Search" style="width: 200px; margin: 0px 0px 5px 10px" />
              <div class="supp-top-form-send-button">
                <el-button type="primary" :icon="Search" @click="findSupplies(sendform)">查询</el-button>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="17">
          <div class="supp-top-form-right">
            <span class="supp-top-form-span">新增|修改</span>
            <!-- 新增 -->
            <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="90px" status-icon :inline="true">
              <el-form-item label="id" prop="id" class="form-item" v-if="false">
                <el-input v-model.trim="ruleForm.id"  />
              </el-form-item>
              <el-form-item label="物资名称" prop="suppliesName" class="form-item">
                <el-input v-model.trim="ruleForm.suppliesName" placeholder="请输入物资名称" />
              </el-form-item>
              <el-form-item label="物资单位" prop="suppliesUnit" class="form-item">
                <el-input v-model.trim="ruleForm.suppliesUnit" placeholder="请输入物资单位" />
              </el-form-item>
              <el-form-item label="物资数量" prop="suppliesCount" class="form-item">
                <el-input v-model.trim="ruleForm.suppliesCount" type="number" placeholder="请输入物资数量" />
              </el-form-item>
              <el-form-item label="物资价格" prop="suppliesPrice" class="form-item">
                <el-input v-model.trim="ruleForm.suppliesPrice" type="number" placeholder="请输入物资价格" />
              </el-form-item>
              <el-form-item label="供应商" prop="provider" class="form-item">
                <el-input v-model.trim="ruleForm.provider" placeholder="请输入供应商" />
              </el-form-item>
              <el-form-item label="联系人" prop="suppliesAttn" class="form-item">
                <el-input v-model.trim="ruleForm.suppliesAttn" placeholder="请输入联系人" />
              </el-form-item>
              <el-form-item label="联系电话" prop="attnPhone" class="form-item">
                <el-input v-model.trim="ruleForm.attnPhone" type="number" placeholder="请输入供应商" />
              </el-form-item>
              <el-form-item label="是否损坏" prop="isMar">
                <el-radio-group v-model="ruleForm.isMar">
                  <el-radio :label="0">损坏</el-radio>
                  <el-radio :label="1">未损坏</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="损坏原因" prop="suppliesCause" v-show="ruleForm.isMar == 0">
                <el-input v-model.trim="ruleForm.suppliesCause" placeholder="请输入损坏原因" />
              </el-form-item>
              <el-form-item label="损坏数量" prop="marCount" v-show="ruleForm.isMar == 0">
                <el-input v-model.trim="ruleForm.marCount" placeholder="请输入损坏数量" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" style="margin-right: 20px" :icon="Plus" v-show="!showBtn" @click="addSupplies(ruleFormRef)">新增</el-button>
                <el-button type="info" style="margin-right: 20px" @click="emptyForm">清空</el-button>
                <el-button type="primary" v-show="showBtn" @click="alterSupplies(ruleForm)">修改</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <div class="Addsupplies-table">
    <!-- 表单 -->
    <el-table :data="suppliesData" style="width: 100%" height="245px">
      <el-table-column prop="suppliesName" label="物资名称">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.suppliesName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="suppliesCount" label="物资数量">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.suppliesCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="suppliesPrice" label="物资价格">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.suppliesPrice }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="provider" label="供应商">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.provider }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="suppliesAttn" label="联系人">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.suppliesAttn }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="attnPhone" label="联系电话">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.attnPhone }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isMar" label="是否受损">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.isMar == 0 ? '损坏' : '未损坏' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="marCount" label="损坏数量">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.marCount}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="alterbtnShow(scope.row)">修改</el-button>
          <el-popconfirm title="确认删除吗?" @confirm="handleSuppDelete(scope.row)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style lang="scss" scoped>
.supp-top-text {
  margin-top: -20px;
  display: flex;
  align-items: center;
  .supp-text {
    margin-right: 5px;
  }
}
.supp-top-container {
  margin-top: -10px;
}
.supp-top-left {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 2px 2px 2px skyblue;
  margin-right: 5px;
  height: 258px;
  background-color: #fff;
  padding-top: 55px;
}
.supp-top-center {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  text-align: center;
  font-weight: bold;
  font-size: 18px;
  color: rgb(70, 70, 70);
  margin-bottom: 5px;
  margin-right: 5px;
}
.supp-top-form-left {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  padding-left: 10px;
  .supp-top-form-span {
    font-size: 18px;
  }
  .supp-top-form-send {
    margin-top: 5px;
    .supp-top-form-send-text {
      display: inline-block;
    }
    .supp-top-form-send-button {
      text-align: center;
    }
  }
}
.supp-top-form-right {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  padding-left: 10px;
  margin-left: 5px;
  margin-right: 5px;
  padding-bottom: 9px;
  .supp-top-form-span {
    font-size: 18px;
  }
}
.Addsupplies-table {
  margin-top: 10px;
  margin-right: 5px;
}
</style>
