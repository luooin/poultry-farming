<script setup>
import { DArrowRight, ChatLineRound, Warning, Box, Star } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
/* 导入医疗器械方法 */
import { MedicalList } from '@/apis/medical'
/* 导入dayjs */
import dayjs from 'dayjs'
/* 导入用户状态信息 */
import { useUserStore } from '@/stores/UserStore'
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 存储器械信息数组 */
const medicalTable = ref([])
const lendMedical = ref([])
/* 获取元素对象 */
const main = ref(null)
/* 存储echart实例化对象,用于创建和销毁 */
let myChart

/* 生命周期函数,挂载 */
onMounted(() => {
  /* 调用方法获取列表 */
  getMedicalList()
  /* 调用方法创建环形图 */
  createEcharts()
})

/* 销毁前 */
onBeforeUnmount(() => {
  /* 调用方法销毁 */
  unloadEchart(myChart)
})

/* 创建环形图 */
const createEcharts = async () => {
  /* 初始化环形图 */
  myChart = echarts.init(main.value)
  /* 临时参数 */
  const data = { userId: user.userId }
  /* 定义临时数组 */
  let tableData = []
  /* 从后端获取数据 */
  const { data: ref } = await MedicalList(data)
  /* 定义方法获取今天的数据 */
  const today = dayjs(new Date()).format('YYYY-MM-DD')
  /* 过滤后端,返回今天的数据 */
  const todayData = ref.data.filter((item) => dayjs(item.buyTime).format('YYYY-MM-DD') == today)
  /* 计算购买数量,借出数量,归还数量 */
  /* 购买数量 */
  const medicalAllBuy = () => {
    let count = 0
    todayData.forEach((item) => (item.medicalCount == '' ? '' : (count += parseInt(item.medicalCount))))
    return count
  }
  /* 医疗器械借走数量 */
  const medicalAllLend = () => {
    let count = 0
    todayData.forEach((item) => (item.lendCount == '' ? '' : (count += parseInt(item.lendCount))))
    return count
  }

  /* 医疗器械归还数量 */
  const medicalAllReturn = () => {
    let count = 0
    todayData.forEach((item) => (item.returnCount == '' ? '' : (count += parseInt(item.returnCount))))
    return count
  }
  /* 数组添加 */
  tableData.push(medicalAllBuy())
  tableData.push(medicalAllLend())
  tableData.push(medicalAllReturn())
  /* 调用配置 */
  myChart.setOption({
    title: {
      text: '今日信息',
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      },
    },
    grid: {
      left: '3%',
      right: '3%',
      bottom: '3%',
      containLabel: true,
    },
    xAxis: [
      {
        type: 'category',
        data: ['购买数量', '借出数量', '归还数量'],
        axisTick: {
          alignWithLabel: true,
        },
      },
    ],
    yAxis: [
      {
        type: 'value',
      },
    ],
    series: [
      {
        name: 'Direct',
        type: 'bar',
        barWidth: '60%',
        data: tableData,
      },
    ],
  })
}

/* 销毁Echart */
const unloadEchart = (myChart) => {
  /* 判断是否存在,存在就销毁 */
  if (myChart) {
    echarts.dispose(myChart)
    /* 赋值为空,方便下次重新赋值 */
    myChart = null
  }
}

/* 获取器械信息 */
const getMedicalList = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await MedicalList(data)
  if (ref.code > 0) {
    medicalTable.value = ref.data
    /* 过滤出用户信息不为空的数据作为借走器械 */
    lendMedical.value = ref.data.filter((item) => item.user != null)
  } else {
    ElMessage.info(ref.msg)
  }
}

/* 定义医疗器械总数量 */
const medicalAllCount = computed(() => {
  let count = 0
  medicalTable.value.forEach((item) => (count += parseInt(item.medicalCount)))
  return count
})

/* 医疗器械总价格 */
const medicalAllPrice = computed(() => {
  let count = 0
  medicalTable.value.forEach((item) => (count += parseFloat(item.medicalPrice)))
  return count
})

/* 医疗器械借走数量 */
const medicalAllLend = computed(() => {
  let count = 0
  medicalTable.value.forEach((item) => (item.lendCount == '' ? '' : (count += parseInt(item.lendCount))))
  return count
})

/* 医疗器械归还数量 */
const medicalAllReturn = computed(() => {
  let count = 0
  medicalTable.value.forEach((item) => (item.returnCount == '' ? '' : (count += parseInt(item.returnCount))))
  return count
})
</script>

<template>
  <!-- 头部 -->
  <div class="medical-header">
    <h3 class="medical-text">器械列表</h3>
    <el-icon style="margin-top: 3px"><DArrowRight /></el-icon>
  </div>
  <!-- 统计医疗器械 -->
  <div class="medical-statistics">
    <!-- 统计列表,统计总数量、总价格、借出数量、归还数量 -->
    <el-row style="text-align: center">
      <el-col :span="6">
        <el-statistic title="医疗器械总数量" :value="medicalAllCount">
          <template #suffix>
            <el-icon style="vertical-align: -0.125em"><Box /></el-icon>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic :value="medicalAllPrice">
          <template #title>
            <div style="display: inline-flex; align-items: center">医疗器械总价格</div>
          </template>
          <template #suffix>
            <el-icon><Star /></el-icon>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic title="医疗器械借出数量" :value="medicalAllLend">
          <template #suffix>
            <el-icon style="vertical-align: -0.125em"><Warning /></el-icon>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic title="医疗器械归还数量" :value="medicalAllReturn">
          <template #suffix>
            <el-icon style="vertical-align: -0.125em">
              <ChatLineRound />
            </el-icon>
          </template>
        </el-statistic>
      </el-col>
    </el-row>
  </div>

  <el-scrollbar height="495px">
    <!-- 第二行,借走数量详情、今日购买器械、器械数量 -->
    <el-row class="sence-row">
      <el-col :span="8">
        <!-- 借走数量详情 -->
        <div class="sence-row-one">
          <span class="sence-row-text">借走器械数量</span>
          <el-table :data="lendMedical" height="281px">
            <el-table-column label="姓名">
              <template #default="scope">
                <span>{{ scope.row.user.name }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="medicalName" label="借走器械名称" />
            <el-table-column prop="lendCount" label="借走器械数量" />
            <el-table-column label="联系方式">
              <template #default="scope">
                <span>{{ scope.row.user.phone }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="sence-row-two">
          <!-- 今日购买器械 -->
          <div ref="main" style="height: 300px"></div>
        </div>
      </el-col>
      <el-col :span="8">
        <!-- 器械数量 -->
        <div class="sence-row-three">
          <span class="sence-row-text">器械数量</span>
          <el-table :data="medicalTable" height="280px" :default-sort="{ prop: 'date', order: 'descending' }">
            <el-table-column prop="medicalName" label="器械名称" />
            <el-table-column prop="medicalCount" sortable label="器械数量" />
            <el-table-column prop="medicalFirm" label="器械厂商" />
          </el-table>
        </div>
      </el-col>
    </el-row>
    <!-- 第三行 -->
    <el-row class="three-row">
      <el-col>
        <div>
          <el-table :data="medicalTable" height="170px" :default-sort="{ prop: 'medicalCount', order: 'descending' }">
            <el-table-column prop="medicalName" label="器械名称" />
            <el-table-column prop="medicalType" label="器械类型" />
            <el-table-column prop="medicalFirm" label="器械厂商" />
            <el-table-column prop="medicalCount" sortable label="器械数量" />
            <el-table-column prop="medicalPrice" label="器械价格" />
            <el-table-column label="购买时间">
              <template #default="scope">
                <span>{{ dayjs(scope.row.buyTime).format('YYYY-MM-DD') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否借出">
              <template #default="scope">
                <span>{{ scope.row.isLend != 0 ? '借出' : '未借出' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否损坏">
              <template #default="scope">
                <span>{{ scope.row.isMar != 0 ? '损坏' : '未损坏' }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </el-scrollbar>
</template>

<style lang="scss" scoped>
.medical-header {
  display: flex;
  align-items: center;
  margin-top: -10px;
  .medical-text {
    margin-right: 5px;
  }
}
.medical-statistics {
  display: block;
  background-color: #fff;
  box-shadow: 1px 1px 2px skyblue;
  border-radius: 10px;
  padding: 10px;
  margin-right: 5px;
}
.sence-row {
  margin-top: 10px;
  margin-right: 5px;
  .sence-row-text {
    display: block;
    background-color: #fff;
    font-size: 14px;
    padding-left: 5px;
    font-weight: bold;
  }
  .sence-row-one {
    border: 1px solid skyblue;
    box-shadow: 1px 1px 2px skyblue;
    margin-right: 10px;
    border-radius: 10px;
    overflow: hidden;
  }
  .sence-row-two {
    border: 1px solid skyblue;
    box-shadow: 1px 1px 2px skyblue;
    margin-right: 10px;
    border-radius: 10px;
    overflow: hidden;
  }
  .sence-row-three {
    border: 1px solid skyblue;
    box-shadow: 1px 1px 2px skyblue;
    border-radius: 10px;
    overflow: hidden;
  }
}
.three-row {
  margin-top: 10px;
  border: 1px solid skyblue;
  box-shadow: 1px 1px 2px skyblue;
  margin-right: 5px;
  border-radius: 10px;
  overflow: hidden;
}
</style>
