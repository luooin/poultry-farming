<script setup>
import { ElMessage } from 'element-plus'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入主盒子状态 */
import { MainBoxStore } from '@/stores/MainBoxStore'
import { ArrowDown } from '@element-plus/icons-vue'
/* 导入路由 */
import { useRouter } from 'vue-router'

import { reactive,ref } from 'vue'

/* 实例化 */
const router = useRouter()
const { user } = useUserStore()
const { changeUser } = useUserStore()
const { changeMainBox } = MainBoxStore()
/* 修改信息对话框显示与隐藏 */
const AliterUserdialog = ref(false)

const ruleForm = reactive({
  oldPassword: '',
  newPassword: '',
})

/* 图片点击事件 */
const ImgClick = () => {
  router.push('/')
}
/* 退出登录 */
const loginOut = () => {
  /* 用户对象设置为空 */
  changeUser({})
  /* 主盒子设置状态，以便后续添加子路由 */
  changeMainBox(false)
  router.push('/login')
  ElMessage.info('退出登录成功')
}
/* 点击了修改按钮 */
const handleEditUser = () => {
  /* 控制修改信息对话框打开 */
  AliterUserdialog.value = true
}

</script>

<template>
  <el-header>
    <div id="app">
      <div class="top-left">
        <img src="/head.png" style="width: 30px; border-radius: 10px; padding-right: 3px" @click="ImgClick" />
        <h3>养殖后台管理</h3>
      </div>
      <div class="top-right">
        <!-- 头像 -->
        <el-avatar shape="square" :size="30" src="/head.png" />
        <!-- 下拉菜单 -->
        <el-dropdown>
          <span class="top-right-title">
            {{ user.username }}
            <el-icon>
              <arrow-down />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <!-- <el-dropdown-item>个人中心</el-dropdown-item> -->
              <el-dropdown-item @click="handleEditUser">修改密码</el-dropdown-item>
              <el-dropdown-item @click="loginOut">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </el-header>

  <el-dialog v-model="AliterUserdialog" title="修改密码" width="22%">
    <!-- 表单组 -->
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="80px" class="form-input" status-icon>
      <el-form-item label="旧密码" prop="oldPassword" class="form-item">
        <el-input v-model.trim="ruleForm.username" placeholder="请输入旧密码" />
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword" class="form-item">
        <el-input v-model.trim="ruleForm.phone" placeholder="请输入新密码" />
      </el-form-item>
      <el-form-item label="确认密码" prop="newPassword" class="form-item">
        <el-input v-model.trim="ruleForm.phone" placeholder="请再次输入新密码" />
      </el-form-item>
      
      <el-form-item>
        <el-button type="primary" @click="alterUserBtn(ruleFormRef)">修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

</template>

<style lang="scss" scoped>
#app {
  height: 60px;
  background-color: rgb(235, 242, 243);
  border-radius: 10px 10px 0 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 10px;
  .top-left {
    display: flex;
    align-items: center;
  }
  .top-right {
    display: flex;
    align-items: center;
    .top-right-title {
      margin-left: 5px;
      cursor: pointer;
    }
  }
}
.el-header {
  --el-header-padding: 0, -20px;
}
:deep(.el-tooltip__trigger:focus-visible) {
  outline: unset;
}
</style>
