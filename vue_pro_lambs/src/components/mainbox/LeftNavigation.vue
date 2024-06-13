<script setup>
import { Key, User, List, HomeFilled } from '@element-plus/icons-vue'
/* 导入请求 */
import { getLeftNav } from '@/apis/leftNav'
/* 导入状态 */
import { useUserStore } from '@/stores/UserStore'

import { onMounted, ref } from 'vue'
/* 导入路由 */
import { useRoute } from 'vue-router'

/* 定义变量存储获取到的导航栏数据 */
const leftData = ref([])
/* 实例化用户状态 */
const { user } = useUserStore()

/* 实例化路由 */
const route = useRoute()
/* 缩小侧边导航栏 */
const isCollapse = ref(false)
/* 钩子函数 */
onMounted(() => {
  /* 调用方法 */
  getLeftNavs()
})
/* 获取导航栏方法 */
const getLeftNavs = async () => {
  const { data: ref } = await getLeftNav()
  /* 获取到的值赋给变量 */
  leftData.value = ref.data
}
/* 判断用户是否拥有指定的权限,遍历本地的状态 */
/* 解决权限是否显示 */
const BooleanInfo = (item) => {
  return user.power.powers.includes(item)
}
/* 采用图标映射法映射图标 */
const IconMap = {
  HomeFilled,
  User,
  List,
  Key,
}
</script>

<template>
  <div class="left-nav-container">
    <!-- 左侧导航栏 -->
    <el-aside width="170px">
      <el-radio-group v-model="isCollapse" class="left-radio">
        <el-radio-button :label="false">展开</el-radio-button>
        <el-radio-button :label="true">折叠</el-radio-button>
      </el-radio-group>
      <el-scrollbar>
        <el-menu style="height: 83vh" :default-active="route.fullPath" :collapse="isCollapse" :router="true">
          <template v-for="data in leftData" :key="data.path">
            <!-- 判断是否有子路由 -->
            <el-sub-menu :index="data.path" v-if="data.child.length > 0 && BooleanInfo(data.path)">
              <template #title>
                <el-icon><component :is="IconMap[data.icon]"></component></el-icon>
                <span>{{ data.title }}</span>
              </template>
              <!-- 遍历子路由 -->
              <template v-for="item in data.child" :key="item.path">
                <el-menu-item :index="item.path" v-if="BooleanInfo(item.path)">
                  <el-icon><component :is="IconMap[item.icon]"></component></el-icon>
                  <span>{{ item.title }}</span>
                </el-menu-item>
              </template>
            </el-sub-menu>
            <!-- 没有子路由 -->
            <el-menu-item :index="data.path" v-else-if="BooleanInfo(data.path)">
              <el-icon><component :is="IconMap[data.icon]"></component></el-icon>
              <span>{{ data.title }}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </el-scrollbar>
    </el-aside>
  </div>
</template>

<style lang="scss" scoped>
.left-radio {
  display: block;
  text-align: center;
  padding-top: 5px;
  padding-bottom: 5px;
  border-bottom: 1px solid black;
}
</style>
