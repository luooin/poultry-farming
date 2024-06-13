<script setup>
import { ArrowDown } from '@element-plus/icons-vue'
import TopVue from '@/components/mainbox/TopNavigation.vue'
import LeftVue from '@/components/mainbox/LeftNavigation.vue'
import { ref, watch } from 'vue'
/* 导入路由 */
import { useRoute, useRouter } from 'vue-router'
/* 导入左侧导航栏配置 */
import leftPage from '@/util/leftNavigation'

/* 实例化路由,用于获取参数 */
const route = useRoute()
/* 实例化路由,用于跳转页面 */
const router = useRouter()

/* tab标签数组 */
const dynamicTags = ref([])

/* 监听路由路径的变化,把变化的新值赋给tab标签数组,存在则不添加 */
watch(
  () => route.path,
  (newPath) => !Booleantag(newPath) && dynamicTags.value.push(getLeftName(newPath))
)
/* 关闭单个标签方法 */
const handleClose = (tag) => {
  /* tag中的数组指定项替换为1 */
  dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1)
}
/* 单个tag标签点击 */
const handleTagClick = (name) => {
  /* 过滤得到名称相同的的对象的路径,使用路由进行跳转 */
  const path = leftPage.filter((item) => item.name == name)[0].path
  router.push(path)
}
/* 定义方法，遍历左侧导航栏配置,依靠路径获取名称 */
const getLeftName = (path) => {
  return leftPage.filter((item) => item.path == path)[0].name
}
/* 定义方法,通过判断标签数组中是否有指定的路径名称返回 */
const Booleantag = (path) => {
  return dynamicTags.value.includes(getLeftName(path))
}
/* 关闭所有标签 */
const CloseAllTag = () => {
  /* tab标签数组设置为空 */
  dynamicTags.value = []
}
</script>

<template>
  <div>
    <el-container direction="vertical">
      <!-- 顶部导航栏 -->
      <TopVue></TopVue>
      <el-container>
        <!-- 左侧导航栏 -->
        <LeftVue></LeftVue>
        <el-main>
          <!-- 主体内容 -->
          <!-- 滚动面板 -->
          <el-scrollbar>
            <!-- 不等于空时才显示 -->
            <div class="tag-box" v-if="dynamicTags.length > 0">
              <!-- 滚动面板 -->
              <el-scrollbar>
                <!-- tag标签,采用循环方式添加 -->
                <el-tag v-for="tag in dynamicTags" :key="tag" closable class="tag-hover" @close="handleClose(tag)" @click="handleTagClick(tag)">
                  {{ tag }}
                </el-tag>
              </el-scrollbar>
              <div>
                <!-- 下拉菜单,通过点击才会下拉 -->
                <el-dropdown trigger="click">
                  <el-button type="primary" size="small">
                    标签选项<el-icon class="el-icon--right"><arrow-down /></el-icon>
                  </el-button>
                  <template #dropdown>
                    <!-- 下拉菜单项 -->
                    <el-dropdown-menu>
                      <el-dropdown-item @click="CloseAllTag">关闭所有</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </div>
            <!-- 路由视图 -->
            <router-view></router-view>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.el-main {
  padding: 0px 0px 0px 10px;
  background-color: rgb(235, 242, 243);
}
.tag-box {
  background-color: white;
  border-radius: 5px;
  padding: 5px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .tag-hover:hover {
    cursor: pointer;
    background-color: skyblue;
  }
}
.el-tag {
  margin-right: 5px;
}

</style>
