// axios配置
import axios from 'axios'
import { ElLoading } from 'element-plus'
let loadingInstance = null
/* 创建axios对象 */
const httpInterface = axios.create({
  /* 根源采用vite代理 */
  /* 超时时间17秒 */
  timeout: 17000,
})

// 添加请求拦截器
httpInterface.interceptors.request.use(
  function (config) {
    /* 获取token赋值到请求头 */
    config.headers.token = localStorage.getItem('token')
    loadingInstance = ElLoading.service({ fullscreen: true })
    return config
  },
  function (error) {
    // 对请求错误做些什么
    console.log('发送前错误。。')
    return Promise.reject(error)
  }
)

// 添加响应拦截器
httpInterface.interceptors.response.use(
  function (response) {
    /* 响应数据中存在token则添加到本地浏览器 */
    response.data.data?.token && localStorage.setItem('token', response.data.data.token)
    /* 加载框关闭 */
    loadingInstance && loadingInstance.close()
    return response
  },
  function (error) {
    /* 加载框关闭 */
    loadingInstance && loadingInstance.close()
    /* 获取信息,判断状态是否为401,为401就跳转到登录 */
    const status = error.response.status
    if (status === 401) {
      /* 跳转到首页 */
      window.location.href = '/login'
      /* 清空token */
      localStorage.removeItem('token')
    }
    return Promise.reject(error)
  }
)
/* 默认导出供外界使用 */
export default httpInterface
