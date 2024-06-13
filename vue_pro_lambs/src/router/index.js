import { createRouter, createWebHistory } from 'vue-router'
/* 导入进度条 */
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
/* 登录组件和主盒子组件 */
import Login from '@/views/Login.vue'
import MainBox from '@/views/MainBox.vue'
/* 盒子和用户状态管理 */
import { MainBoxStore } from '@/stores/MainBoxStore'
import { useUserStore } from '@/stores/UserStore'
/* 错误路径 */
import NotFound from '@/views/notfound/NotFound.vue'
/* 路由配置 */
import RouterConfig from './config'
// 定义主要路由
const routes = [
  /* 登录 */
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  /* 主盒子 */
  {
    path: '/mainbox',
    name: 'mainbox',
    component: MainBox,
  },
  /* 重定向到主页面 */
  {
    path: '/',
    redirect: '/index',
  },
  /* 错误路径 */
  {
    path: '/:pathMatch(.*)*',
    name: 'notfont',
    component: NotFound,
  },
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// 前置路由,用于登录和动态添加路由
router.beforeEach((to, from, next) => {
  /* 显示进度条 */
  NProgress.start()
  /* 实例化状态管理 */
  const { isGetterMainBox } = MainBoxStore()
  const { user } = useUserStore()
  /* 未登录 */
  if (to.name === 'login') {
    next()
  } else {
    /* 是否可以获取到用户信息,不行则返回登录 */
    if (!user.phone) {
      next({
        path: '/login',
      })
    } else {
      /* 主盒子不删除则后面添加的路由会拼接到主盒子上 */
      /* 已登录，调用方法动态添加路由,通过状态判断主盒子是否存在 */
      if (!isGetterMainBox) {
        router.removeRoute('mainbox')
        /* 调用方法动态添加路由 */
        AddRouter()
        next({
          path: to.fullPath,
        })
      } else {
        next()
      }
    }
  }
})
// 后置路由
router.afterEach(() => {
  /* 关闭进度条 */
  NProgress.done()
})
//动态添加路由方法
const AddRouter = () => {
  /* 实例化状态管理 */
  const { changeMainBox } = MainBoxStore()
  /* 重新添加路由 */
  router.addRoute({
    path: '/mainbox',
    name: 'mainbox',
    component: MainBox,
  })
  /* 遍历路由配置,循环添加路由 */
  RouterConfig.forEach((item) => {
    BooleanInfo(item.path) && router.addRoute('mainbox', item)
  })
  /* 设置状态管理 */
  changeMainBox(true)
}
//判断用户拥有权限方法
const BooleanInfo = (item) => {
  const { user } = useUserStore()
  return user.power.powers.includes(item)
}

export default router
