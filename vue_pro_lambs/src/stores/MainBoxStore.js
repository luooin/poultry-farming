import { defineStore } from 'pinia'
import { ref } from 'vue'
// 主盒子状态管理
export const MainBoxStore = defineStore('mainbox', () => {
  /* 定义判断值判断是否创建 */
  const isGetterMainBox = ref(false)
  /* 定义方法改变判断值 */
  const changeMainBox = (value) => {
    isGetterMainBox.value = value
  }
  /* 返回值和方法，供外界使用 */
  return {
    isGetterMainBox,
    changeMainBox,
  }
})
