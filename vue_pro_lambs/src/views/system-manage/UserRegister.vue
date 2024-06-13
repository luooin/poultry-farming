<script setup>
import { Close, User, Check, InfoFilled } from '@element-plus/icons-vue'
import { computed, onMounted, ref } from 'vue'
/* 导入日期格式化js */
import dayjs from 'dayjs'
/* 导入获取注册列表方法 */
import { getList, agreeRegister, regectRegister } from '@/apis/userRegister'
import { ElMessage } from 'element-plus'

/* 存储返回数据 */
const retirnData = ref([])
/* 存储表单数据 */
const tableData = ref([])

/* 挂载生命周期钩子函数 */
onMounted(() => {
  /* 调用获取注册列表方法 */
  getRegisterList()
})

/* 获取注册列表方法 */
const getRegisterList = async () => {
  const { data: ref } = await getList()
  /* 返回数据用于计算 */
  retirnData.value = ref.data
  /* 过滤出状态为1的数据 */
  tableData.value = ref.data.filter((item) => item.status == 1)
}

/* 动态计算用户注册量,返回数据长度 */
const UserCount = computed(() => {
  return retirnData.value.length
})
/* 动态计算同意注册量,返回数据的状态为2的 */
const AgreeCount = computed(() => {
  let count = 0
  retirnData.value.forEach((item) => {
    if (item.status == 2) {
      count++
    }
  })
  return count
})
/* 动态计算拒绝注册量,返回数据的状态为0的 */
const refuse = computed(() => {
  let count = 0
  retirnData.value.forEach((item) => {
    if (item.status == 0) {
      count++
    }
  })
  return count
})
/* 点击了同意注册按钮 */
const handleAgree = async (row) => {
  /* 设置状态 */
  row.status = 2
  const { data: ref } = await agreeRegister(row)
  /* 判断是否注册成功 */
  if (ref.code > 0) {
    /* 重新调用获取注册列表方法刷新列表 */
    getRegisterList()
    ElMessage.success(ref.msg + ',已发送邮件')
  }
}
/* 点击了拒绝按钮 */
const handleReject = async (row) => {
  /* 设置状态 */
  row.status = 0
  const { data: ref } = await regectRegister(row)
  if (ref.code > 0) {
    /* 重新调用获取注册列表方法刷新列表 */
    getRegisterList()
    ElMessage.info(ref.msg + ',已发送邮件')
  }
}
</script>

<template>
  <div>
    <el-container>
      <!-- 头部 -->
      <el-header>
        <div class="header-container">
          <div class="header-item">
            <div class="header-img">
              <el-icon :size="50" color="white">
                <User />
              </el-icon>
            </div>
            <div class="header-text">
              <span class="text-number">{{ UserCount }}</span>
              <span class="text-info">用户注册总数</span>
            </div>
          </div>
          <div class="header-item">
            <div class="header-img">
              <el-icon :size="50" color="white">
                <Check />
              </el-icon>
            </div>
            <div class="header-text">
              <span class="text-number">{{ AgreeCount }}</span>
              <span class="text-info">批准注册总数</span>
            </div>
          </div>

          <div class="header-item">
            <div class="header-img">
              <el-icon :size="50" color="white">
                <Close />
              </el-icon>
            </div>
            <div class="header-text">
              <span class="text-number">{{ refuse }}</span>
              <span class="text-info">拒绝注册总数</span>
            </div>
          </div>
        </div>
      </el-header>

      <!-- 主要内容 -->
      <el-main>
        <el-scrollbar max-height="540px">
          <!-- 注册人员表单 -->
          <el-table :data="tableData" :border="true" style="width: 100%" :stripe="true">
            <el-table-column prop="username" label="用户名" width="100" />
            <el-table-column prop="phone" label="手机号" />
            <el-table-column prop="email" label="邮箱" />
            <el-table-column label="注册时间">
              <template #default="scope">
                <el-text>{{ dayjs(scope.row.date).format('YYYY-MM-DD') }}</el-text>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="scope">
                <el-button size="small" type="success" @click="handleAgree(scope.row)">同意</el-button>
                <!-- 气泡确认框 -->
                <el-popconfirm width="220" confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#626AEF" title="确定拒绝注册吗?" @confirm="handleReject(scope.row)">
                  <template #reference>
                    <el-button size="small" type="danger">拒绝</el-button>
                  </template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </el-main>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.el-header {
  padding: 0;
}
.el-main {
  padding: 0px;
  margin-top: 25px;
}
.header-container {
  height: 75px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .header-item {
    height: 100%;
    display: flex;
    align-items: center;
    .header-img {
      background-color: skyblue;
      width: 75px;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      border-radius: 5px 0 0 5px;
    }
    .header-text {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background-color: white;
      height: 100%;
      padding: 0 25px;
      border-radius: 0 5px 5px 0;
      .text-number {
        font-size: 30px;
        font-weight: bold;
        margin-bottom: 3px;
      }
      .text-info {
        font-size: 14px;
        color: rgb(111, 108, 108);
      }
    }
  }
}
:deep(.el-table .cell) {
  text-align: center;
}
</style>
