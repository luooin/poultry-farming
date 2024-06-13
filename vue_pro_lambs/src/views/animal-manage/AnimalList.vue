<script setup>
import * as echarts from 'echarts'
import { DArrowRight } from '@element-plus/icons-vue'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入畜禽信息 */
import { animalList } from '@/apis/animal'
/* 导入dayjs */
import dayjs from 'dayjs'
import { onBeforeUnmount, onBeforeMount, onMounted, ref } from 'vue'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 存储畜禽列表 */
const animalData = ref([])

/* 获取元素对象 */
const main = ref(null)
/* 存储echart实例化对象,用于创建和销毁 */
let myChart

onBeforeMount(() => {
  /* 获取畜禽信息 */
  getAnimalData()
})

onMounted(() => {
  /* 调用方法创建环形图 */
  createEcharts()
})

/* 创建环形图 */
const createEcharts = async () => {
  /* 临时数组 */
  let animalArr = []
  /* 临时数据对象 */
  let animalAllcount = 0
  let animalill = 0
  /* 初始化环形图 */
  myChart = echarts.init(main.value)
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await animalList(data)
  /* 动态计算总数量、生病数量 */
  ref.data.forEach((item) => {
    if (item.animalName != '') {
      animalAllcount++
    }
  })
  ref.data.forEach((item) => {
    if (item.animalStatus == 2) {
      animalill++
    }
  })
  /* 数组拼接 */
  animalArr.push({ value: animalAllcount, name: '畜禽总数' })
  animalArr.push({ value: animalill, name: '畜禽得病数' })
  /* 配置若存在,则调用方法 */
  myChart.setOption({
    title: {
      text: '畜禽健康程度',
      subtext: '畜禽总数/畜禽得病数',
      left: 'center',
    },
    tooltip: {
      trigger: 'item',
    },
    legend: {
      itemWidth: 8,
      //图标为圆
      icon: 'circle',
      bottom: 0,
      padding: [0, 0, 0, 0],
    },
    series: [
      {
        name: '数量:只',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: 'center',
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 18,
            fontWeight: 'bold',
          },
        },
        labelLine: {
          show: false,
        },
        data: animalArr,
      },
    ],
  })
}

/* 销毁前 */
onBeforeUnmount(() => {
  /* 调用方法销毁 */
  unloadEchart(myChart)
})

/* 销毁Echart */
const unloadEchart = (myChart) => {
  /* 判断是否存在,存在就销毁 */
  if (myChart) {
    echarts.dispose(myChart)
    /* 赋值为空,方便下次重新赋值 */
    myChart = null
  }
}

/* 获取畜禽信息 */
const getAnimalData = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await animalList(data)
  /* 非空判断 */
  if (ref.code > 0) {
    animalData.value = ref.data
  }
}
</script>

<template>
  <!-- 头部 -->
  <div class="animal-top-text">
    <h3 class="animal-text">畜禽信息</h3>
    <el-icon><DArrowRight /></el-icon>
  </div>
  <!-- 第一行 -->
  <el-row>
    <el-col :span="8">
      <div class="animal-top-weather">
        <div style="background-color: #fff">
          <!-- 环形图 -->
          <div ref="main" style="height: 300px"></div>
        </div>
      </div>
    </el-col>
    <el-col :span="16">
      <div class="animal-top-animal">
        <span class="animal-table-text">大致信息</span>
        <el-table :data="animalData" height="269px">
          <el-table-column prop="animalName" label="畜禽名称" />
          <el-table-column label="性别">
            <template #default="scope">
              <span>{{ scope.row.animalSex == 1 ? '雌性' : '雄性' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="出生日期">
            <template #default="scope">
              <span>{{ dayjs(scope.row.animalDate).format('YYYY-MM-DD') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="健康状态">
            <template #default="scope">
              <span>{{ scope.row.animalStatus == 0 ? '死亡' : scope.row.animalStatus == 1 ? '健康' : '不健康' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="animalWeight" label="体重" />
          <el-table-column label="是否怀孕">
            <template #default="scope">
              <span>{{ scope.row.isBreed == 0 ? '未怀孕' : '怀孕' }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-col>
  </el-row>
  <!-- 第二行 -->
  <el-row class="sence-row">
    <el-col :span="11">
      <!-- 未饲养列表 -->
      <span class="animal-table-text">饲养列表</span>
      <el-table :data="animalData" height="200px">
        <el-table-column label="畜禽名称">
          <template #default="scope">
            <span>{{ scope.row.animalName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="喂养饲料">
          <template #default="scope">
            <span>{{ scope.row.feeds.feedName }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    <el-col :span="2"></el-col>
    <el-col :span="11">
      <span class="animal-table-text">注射疫苗列表</span>
      <el-table :data="animalData" height="200px">
        <el-table-column label="畜禽名称">
          <template #default="scope">
            <span>{{ scope.row.animalName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="需要注射疫苗">
          <template #default="scope">
            <span>{{ scope.row.drug.drugName }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.animal-top-text {
  display: flex;
  align-items: center;
  margin-top: -20px;
  .animal-text {
    margin-right: 5px;
  }
}
.animal-top-weather {
  border: 1px solid skyblue;
  margin-right: 10px;
  box-shadow: 1px 1px 2px skyblue;
}
.animal-top-animal {
  border: 1px solid skyblue;
  margin-right: 5px;
}
.animal-table-text {
  display: block;
  background-color: #fff;
  font-weight: bold;
  padding: 5px 0px 5px 5px;
}
.sence-row {
  margin-top: 10px;
  margin-right: 5px;
}
</style>
