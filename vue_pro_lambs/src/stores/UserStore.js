import { defineStore } from 'pinia'
import { ref } from 'vue'
// 用户信息状态管理
export const useUserStore = defineStore(
  'UserStore',
  () => {
    /* 定义用户信息对象 */
    const user = ref({})
    /* 对用户信息对象提供修改方法 */
    const changeUser = (value) => {
      user.value = value
    }
    /* 返回对象和方法，供外界使用 */
    return {
      user,
      changeUser,
    }
  },
  /* 持久化存储 */
  {
    persist: true,
  }
)
