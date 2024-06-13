<script setup>
import { ElMessage } from 'element-plus'
/* 导入登录方法 */
import { login } from '@/apis/login'
/* 导入粒子配置 */
import { config } from '@/util/config'
/* import { loadSlim } from 'tsparticles-slim'*/

/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入用户注册方法 */
import { booleanRegister, applyRegister } from '@/apis/userRegister'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

/* 实例化路由 */
const router = useRouter()
/* 实例化用户状态 */
const { changeUser } = useUserStore()
/* 加载粒子 */
// const particlesInit = async (engine) => {
//   await loadSlim(engine)
// }
/* 表头绑定对象 */
const activeName = ref('first')
/* 表单登录对象 */
const ruleFormRef = ref()
/* 表单注册对象 */
const regFormRef = ref()
/* 表单登录元素 */
const ruleForm = reactive({
  username: '',
  // phone: '',
  password: '',
})
/* 表单注册元素 */
const regruleForm = reactive({
  phone: '',
  email: '',
  username: '',
  password: '',
})
/* 自定义检验用户名 */
const validusername = (rule, value, callback) => {
  /* 如果输入内容不为空 */
  if (value != '') {
    /* 用户名不能超过8个字符 */
    if (value.length > 8) {
      callback(new Error('用户名的长度不能超过8个字或者字符'))
    }
    /* 跳出检验 */
    callback()
  } else {
    callback(new Error('请输入用户名'))
  }
}

/* 自定义表单规则检验密码 */
const validQC = (rule, value, callback) => {
  /* 如果输入内容不为空 */
  if (value !== '') {
    /* 定义正则表达式判断输入内容是否为数字 */
    if (/[\u4E00-\u9FA5]/g.test(value)) {
      callback(new Error('不能输入汉字'))
    } else {
      /* 长度不能小于6位数 */
      if (value.length < 6) {
        callback(new Error('密码长度不小于6位数'))
      } else {
        /* 验证通过 */
        callback()
      }
    }
  } else {
    callback(new Error('请输入密码'))
  }
}
/* 自定义表单规则检验手机号 */
const phoneTest = (rule, value, callback) => {
  /* 定义正则表达式 */
  const phoneN = /^1[3456789]\d{9}$/
  /* 非空判断 */
  if (value !== '') {
    /* 定义正则表达式判断输入内容是否为数字 */
    if (!phoneN.test(value)) {
      callback(new Error('手机号格式错误'))
    } else {
      /* 验证通过 */
      callback()
    }
  } else {
    callback(new Error('请输入手机号'))
  }
} /* 自定义表单规则检验邮箱 */
const EmailTest = (rule, value, callback) => {
  /* 定义正则表达式 */
  const EmailT = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/
  /* 非空判断 */
  if (value !== '') {
    /* 定义正则表达式判断输入内容 */
    if (!EmailT.test(value)) {
      callback(new Error('邮箱格式错误'))
    } else {
      /* 验证通过 */
      callback()
    }
  } else {
    callback(new Error('请输入邮箱'))
  }
}
/* 表单规则 */
const rules = reactive({
  username: [{ required: true, validator: validusername, trigger: 'blur' }],
  phone: [{ required: true, validator: phoneTest, trigger: 'blur' }],
  password: [{ required: true, validator: validQC, trigger: 'blur' }],
  email: [{ required: true, validator: EmailTest, trigger: 'blur' }],
})
/* 表单注册按钮点击 */
const regSubBtn = (formEl) => {
  /* 对其中内容进行检查 */
  formEl.validate(async (vallid) => {
    /* 非空判断 */
    if (vallid) {
      /* 判断该联系方式是否注册 */
      const { data: ref } = await booleanRegister(regruleForm)
      /* 判断返回信息是否为空,是则注册用户 */
      if (ref.msg === '暂无信息') {
        /* 调用方法申请注册 */
        const { data: ref } = await applyRegister(regruleForm)
        /* 状态>0注册成功 */
        if (ref.code > 0) {
          /* 成功提示信息 */
          ElMessage.success(ref.msg)
          /* 清空表单数据 */
          tabchange()
        } else {
          /* 错误提示信息 */
          ElMessage.error(ref.msg)
        }
      } else {
        /* 返回信息不为空,提示后端结果 */
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.error('请检查信息是否有误')
    }
  })
}
/* 表单登录按钮点击 */
const submitForm = (formEl) => {
  /* 对其中内容进行检查 */
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 调用方法登录 */
      const { data: ref } = await login(ruleForm)
      /* 判断返回状态 */
      if (ref.code > 0) {
        /* 持久化存储数据 */
        changeUser(ref.data)
        /* 路由跳转 */
        router.push('/')
        /* 清空表单数据 */
        tabchange()
        /* 提示登录状态 */
        ElMessage.success(ref.msg)
      } else {
        /* 提示错误信息 */
        ElMessage.error(ref.msg)
      }
    } else {
      ElMessage.error('请检查信息是否有未完成')
    }
  })
}
/* 表单状态改变 */
const tabchange = () => {
  /* 清空注册和登录中的值 */
  ruleForm.username = ''
  ruleForm.password = ''
  ruleForm.phone = ''
  regruleForm.phone = ''
  regruleForm.email = ''
  regruleForm.username = ''
  regruleForm.password = ''
}
</script>

<template>
  <vue-particles id="tsparticles"  :options="config" />
  <div>
    <div class="form">
      <img src="/head.png"  class="form-img" />
      <h4 style="margin-left: 60px" class="form-title">家禽饲养平台</h4>
      <el-tabs v-model="activeName" @tab-change="tabchange">

        
        <!-- 表单登录 -->
        <el-tab-pane label="登录" name="first">
          <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="80px" class="form-input" status-icon>
            <el-form-item label="用户名" prop="username" class="form-item">
              <el-input v-model.trim="ruleForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <!-- <el-form-item label="手机号" prop="phone" class="form-item">
              <el-input v-model.trim="ruleForm.phone" type="number" placeholder="请输入手机号" />
            </el-form-item> -->
            <el-form-item label="密码" prop="password" class="form-item">
              <el-input v-model.trim="ruleForm.password" type="password" placeholder="请输入密码" />
            </el-form-item>
            <el-form-item class="form-button">
              <el-button type="primary" @click="submitForm(ruleFormRef)"> 登录 </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="second" class="register">
          <!-- 表单注册 -->
          <el-form ref="regFormRef" :model="regruleForm" :rules="rules" label-width="80px" class="form-input" status-icon>
            <el-form-item label="手机号" prop="phone" class="form-item">
              <el-input v-model.trim="regruleForm.phone" type="number" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email" class="form-item">
              <el-input v-model.trim="regruleForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="用户名" prop="username" class="form-item">
              <el-input v-model.trim="regruleForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password" class="form-item">
              <el-input v-model.trim="regruleForm.password" type="password" placeholder="请输入密码" />
            </el-form-item>
            <el-form-item class="form-button">
              <el-button type="success" @click="regSubBtn(regFormRef)"> 注册 </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.form {
  width: 400px;
  height: 380px;
  background-color: rgb(210, 241, 243);
  position: fixed;
  z-index: 100;
  top: 25%;
  left: 40%;
  border-radius: 20px;

  .form-img {
    width: 35px;
    border-radius: 20px;
    position: absolute;
    top: 24px;
    left: 122px;
  }
  .form-title {
    text-align: center;
    margin-left: 15px;
    position: relative;
    font-size: 24px;
    margin-bottom: 4px;
    text-shadow: 1px 2px 3px black;
  }
  .form-input {
    width: 370px;
  }
  .form-item {
    margin-top: 18px;
  }
  .form-button {
    margin-left: 70px;
  }
  :deep(.el-tabs__item) {
    font-weight: bold;
    padding: 0 140px;
    margin-left: 23px;
  }
  :deep(.el-form-item__error) {
    color: black;
  }
}
</style>
