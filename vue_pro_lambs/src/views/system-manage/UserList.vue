<script setup>
import { DArrowRight, Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { onMounted, reactive, ref } from 'vue'
/* 导入获取用户方法 */
import { UserList, alterUserStatus, alterUserMsg } from '@/apis/users'
/* 获取权限信息 */
import { getPowerList } from '@/apis/power'
/* 导入自定义方法 */
import { gender } from '@/util/other'



/* 修改信息对话框显示与隐藏 */
const AliterUserdialog = ref(false)
/* 表格数据 */
const tableData = ref([])
/* 权限对象 */
const powerData = ref([])
/* 用户对象 */
const users = reactive({
  username: '',
  name: '',
  phone: '',
})
/* 表单用户对象 */
const ruleFormRef = ref()
/* 表单用户元素 */
const ruleForm = reactive({
  username: '',
  powerId: '',
  userId: '',
  phone: '',
  name: '',
  age: '',
  gender: '',
})
/* 表单用户信息规则 */
const rules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  powerId: [{ required: true, message: '请选择权限', trigger: 'change' }],
  phone: [{ required: true, message: '请输入联系方式', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
})


/* 从后端获取数据方法 */
const getUserFaction = async () => {
  /* 获取所有用户信息方法,并使用async和await解构出data赋值 */
  const { data: ref } = await UserList()
  tableData.value = ref.data
  /* 获取所有权限信息,并解构赋值 */
  const { data: res } = await getPowerList()
  powerData.value = res.data
}
/* 点击了修改按钮 */
const handleEditUser = (item) => {
  /* 将获取到的值赋值给表单元素 */
  ruleForm.username = item.username
  ruleForm.powerId = item.powerId
  ruleForm.phone = item.phone
  ruleForm.name = item.user.name
  ruleForm.age = item.user.age
  ruleForm.gender = item.user.gender
  ruleForm.userId = item.userId
  /* 控制修改信息对话框打开 */
  AliterUserdialog.value = true
}
/* 对话框中修改按钮点击 */
const alterUserBtn = (formEl) => {
  formEl.validate(async (vallid) => {
    if (vallid) {
      const { data: ref } = await alterUserMsg(ruleForm)
      if (ref.code > 0) {
        ElMessage.success(ref.msg)
        /* 控制修改信息对话框关闭 */
        AliterUserdialog.value = false
        /* 调用方法刷新界面 */
        getUserFaction()
      } else {
        ElMessage.warning(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}
/* 点击了禁用按钮 */
const handleDelete = async (item) => {
  item.status = 0
  const { data: ref } = await alterUserStatus(item)
  /* 判断状态是否为1 */
  if (ref.code > 0) {
    ElMessage.info(ref.msg)
    /* 调用方法刷新数据 */
    getUserFaction()
  } else {
    ElMessage.error(ref.msg)
  }
}

/* 点击启用按钮 */
const enableUser = async (item) => {
  item.status = 1
  const { data: ref } = await alterUserStatus(item)
  /* 判断状态是否为1 */
  if (ref.code > 0) {
    ElMessage.info(ref.msg)
    /* 调用方法刷新数据 */
    getUserFaction()
  } else {
    ElMessage.error(ref.msg)
  }
}

/* 点击头部的查询按钮 */
const selectUser = async (value) => {
  /* 全部为空时,查询所有信息 */
  if (value.username == '' && value.name == '' && value.phone == '') {
    getUserFaction()
  }
  /* 采用过滤方法,过滤的数据重新赋值给表格对象 */
  tableData.value = tableData.value.filter((item) => (value.phone != '' ? item.phone == value.phone : '' || value.username != '' ? item.username == value.username : '' || value.name != '' ? item.user.name == value.name : ''))
}

/* 点击查询所有按钮 */
const selectAll = () => {
  /* 调取方法获取所有信息 */
  getUserFaction()
}

/* 生命周期函数,挂载时 */
onMounted(() => {
  getUserFaction()
})
</script>

<template>
  <!-- 头部 -->
  <div class="text-container">
    <h3 class="user-text">用户信息</h3>
    <el-icon><DArrowRight /></el-icon>
  </div>
  <!-- 导航栏、第一行 -->
  <el-row>
    <el-col :span="24">
      <!-- 用户信息查询 -->
      <div class="user-head">
        <span class="user-head-text">用户信息查询</span>
        <!-- 通过多个输入框获取数据 -->
        <div class="user-div-input">
          <el-input v-model="users.username" placeholder="请输入用户名" style="width: 200px" class="user-input" />
          <el-input v-model="users.name" placeholder="请输入姓名" style="width: 200px" class="user-input" />
          <el-input v-model="users.phone" placeholder="请输入联系方式" type="number" style="width: 200px" class="user-input" />
          <!-- 按钮组 -->
          <el-button type="primary" :icon="Search" @click="selectUser(users)">查询</el-button>
          <el-button type="primary" :icon="Search" @click="selectAll">查询所有</el-button>
        </div>
      </div>
    </el-col>
  </el-row>
  <!-- 用户信息以表格显示 -->
  <div class="user-table">
    <!-- :cell-style和:header-cell-style样式用于表格和表头的内容居中 -->
    <el-table :data="tableData" border :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }">
      <el-table-column prop="username" label="用户名">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.username }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="user.name" label="姓名">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.user.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="user.gender" label="性别">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.user.gender == 0 ? '男' : '女' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="user.age" label="年龄">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.user.age }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="user.phone" label="联系方式">
        <template #default="scope">
          <el-tag disable-transitions>{{ scope.row.user.phone }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="scope">
          <el-tag :type="scope.row.status == 0 ? 'danger' : 'success'" disable-transitions>{{ scope.row.status == 0 ? '异常' : '正常' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="权限">
        <template #default="scope">
          <el-tag>{{ scope.row.power.powerName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEditUser(scope.row)">修改</el-button>
          <!-- 通过状态动态添加启用和禁用按钮 -->
          <el-popconfirm title="确定禁用该用户吗?" confirm-button-text="确定" cancel-button-text="取消" @confirm="handleDelete(scope.row)" v-if="scope.row.status === 1 && scope.row.username != 'admin'">
            <template #reference>
              <el-button size="small" type="danger">禁用</el-button>
            </template>
          </el-popconfirm>
          <!-- 状态为0时,启用按钮显示 -->
          <el-button type="success" size="small" v-if="scope.row.status === 0" @click="enableUser(scope.row)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 修改信息对话框 -->
  <el-dialog v-model="AliterUserdialog" title="用户信息修改" width="22%">
    <!-- 表单组 -->
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="80px" class="form-input" status-icon>
      <el-form-item label="用户名" prop="username" class="form-item">
        <el-input v-model.trim="ruleForm.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="选择权限" prop="powerId" class="form-item">
        <el-select v-model="ruleForm.powerId" class="m-2" placeholder="请选择权限">
          <!-- 遍历权限信息 -->
          <el-option v-for="item in powerData" :key="item.id" :label="item.powerName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone" class="form-item">
        <el-input v-model.trim="ruleForm.phone" placeholder="请输入联系方式" />
      </el-form-item>
      <el-form-item label="姓名" prop="name" class="form-item">
        <el-input v-model.trim="ruleForm.name" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="年龄" prop="age" class="form-item">
        <el-input v-model.trim="ruleForm.age" type="number" placeholder="请输入年龄" />
      </el-form-item>
      <el-form-item label="选择性别" prop="gender">
        <el-select v-model.trim="ruleForm.gender" placeholder="请选择性别">
          <!-- 遍历自定义性别数据 -->
          <el-option v-for="item in gender" :key="item.id" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="alterUserBtn(ruleFormRef)">修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

</template>

<style lang="scss" scoped>
.text-container {
  display: flex;
  align-items: center;
  margin-top: -10px;
  .user-text {
    margin-right: 5px;
  }
}
.user-head {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  margin-right: 5px;
  .user-head-text {
    display: block;
    margin: 5px 0px 5px 10px;
    font-weight: bold;
    font-size: 14px;
  }
  .user-div-input {
    .user-input {
      margin: 5px 8px 5px 8px;
    }
  }
}
.user-table {
  margin-top: 10px;
  margin-right: 5px;
  border: 1px solid skyblue;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 1px 1px 2px skyblue;
}
</style>
