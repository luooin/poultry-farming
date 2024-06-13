<script setup>
import { DArrowRight } from '@element-plus/icons-vue'
/* 导入饲料信息方法 */
import { FeedList } from '@/apis/feed'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入dayjs */
import dayjs from 'dayjs'
import { nextTick, onMounted, ref } from 'vue'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 定义饲料表单数据 */
const tableFeedData = ref([])

/* 生命周期挂载方法 */
onMounted(() => {
  /* 获取饲料列表方法 */
  nextTick(()=>{
    getFeedList()
  })
})

/* 获取饲料列表方法 */
const getFeedList = async () => {
  /* 临时数据 */
  const data = { userId: user.userId }
  /* 调用方法 */
  const { data: ref } = await FeedList(data)
  /* 非空判断 */
  if (ref.code > 0) {
    tableFeedData.value = ref.data
    console.log(ref);
  }
}

</script>

<template>
  <!-- 概要视图,采用3x3布局,内容采用父子组件进行数据传递 -->

  <!-- 表格数据 -->
  <div class="feed-table">
    <div class="table-head">
      <h3 class="feed-text">详细内容</h3>
      <el-icon style="margin-top: 6px"><DArrowRight /></el-icon>
    </div>
    <div>
      <el-scrollbar>
        <!-- 遍历饲料表的数据 -->
        <el-table :data="tableFeedData" height="250px" :stripe="true" :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }">
          <el-table-column prop="feedName" label="饲料名称" />
          <el-table-column prop="feedCount" label="饲料吨数" />
          <el-table-column prop="feedPrice" label="饲料价格" />
          <el-table-column label="饲料状态">
            <template #default="scope">
              <!-- 三元运算符判断,若为0(scope.row.feedStatus==0? '缺乏': ''),则缺乏,不为0则判断是否为1(scope.row.feedStatus==1? '少量':'较多'),为1则为少量,不为1则较多 -->
              <span>{{ scope.row.feedStatus == 0 ? '缺乏' : scope.row.feedStatus == 1 ? '少量' : '较多' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="feedPlace" label="存放位置" />
          <el-table-column label="存放日期">
            <template #default="scope">
              <span>{{ dayjs(scope.row.feedDate).format('YYYY-MM-DD') }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="feedOrigin" label="饲料产地" />
        </el-table>
      </el-scrollbar>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.feed-text {
  margin: 8px 5px 5px 0px;
  color: rgb(70, 70, 70);
}
.table-head {
  display: flex;
  align-items: center;
}
</style>
