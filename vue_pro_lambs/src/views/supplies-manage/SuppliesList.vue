<script setup>
import { DArrowRight } from '@element-plus/icons-vue'
import { onBeforeUnmount, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
/* 导入物资方法 */
import { SuppliesList } from '@/apis/supplies'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 创建物资对象用于存储返回数据 */
const suppliesData = ref([])
/* 较少物资对象 */
const lessSupplies = ref([])
/* 获取元素对象 */
const main = ref(null)
/* 存储echart实例化对象,用于创建和销毁 */
let myChart

/* 钩子函数,挂载时调用获取物资方法 */
onMounted(() => {
  getSuppliesList()
  /* 调用方法创建环形图 */
  createEcharts()
})

/* 获取物资列表方法,使用async和await解构axios数据 */
const getSuppliesList = async () => {
  /* 定义用户id */
  const param = {
    userId: user.userId,
  }
  /* 获取物资方法 */
  const { data: ref } = await SuppliesList(param)
  /* 赋值 */
  suppliesData.value = ref.data
  /* 过滤出物资数量小于5的 */
  lessSupplies.value = ref.data.filter((item) => parseInt(item.suppliesCount) <= 5)
}

/* 销毁前 */
onBeforeUnmount(() => {
  /* 调用方法销毁 */
  unloadEchart(myChart)
})

/* 创建环形图 */
const createEcharts = async () => {
  /* 定义数组存储数据 */
  let chartData = []
  /* 定义物资总数量 */
  let allCount = 0
  /* 物资损坏数量 */
  let badCount = 0
  /* 初始化环形图 */
  myChart = echarts.init(main.value)
  /* 定义用户id */
  const param = {
    userId: user.userId,
  }
  /* 获取物资方法 */
  const { data: ref } = await SuppliesList(param)
  /* 循环添加物资总数量、物资损坏数量 */
  ref.data.forEach((item) => (allCount += parseInt(item.suppliesCount)))
  ref.data.forEach((item) => (item.isMar === '1' ? '' : (badCount += parseInt(item.marCount))))
  /* 定义描述数组 */
  let descore = ['物资总量', '物资损坏量']
  chartData.push({ name: descore[0], value: allCount })
  chartData.push({ name: descore[1], value: badCount })
  /* 调用配置 */
  myChart.setOption({
    title: {
      text: '物资损坏率',
      subtext: '物资总量/物资损坏量',
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
        name: '单位:件',
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
        data: chartData,
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
</script>

<template>
  <!-- 头部 -->
  <div class="supp-top-text">
    <h3 class="supp-text">物资信息</h3>
    <el-icon><DArrowRight /></el-icon>
  </div>
  <!-- 图表信息 -->
  <div class="supp-chart">
    <el-row>
      <el-col :span="6">
        <div class="supp-table">
          <span class="supp-table-text">物资基本信息</span>
          <el-table :data="suppliesData" height="200px">
            <el-table-column prop="suppliesName" label="物资名称" />
            <el-table-column prop="suppliesUnit" label="物资单位" />
            <el-table-column prop="suppliesCount" label="物资数量" />
          </el-table>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="supp-table">
          <span class="supp-table-text">物资价格</span>
          <el-table :data="suppliesData" height="200px">
            <el-table-column prop="suppliesName" label="物资名称">
              <template #default="scope">
                <el-tag disable-transitions>{{ scope.row.suppliesName }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="suppliesPrice" label="物资价格">
              <template #default="scope">
                <el-tag disable-transitions>{{ scope.row.suppliesPrice }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="supp-table">
          <span class="supp-table-text">物资供应商</span>
          <el-table :data="suppliesData" height="200px">
            <el-table-column prop="suppliesName" label="物资名称" />
            <el-table-column prop="provider" label="物资供应商" />
            <el-table-column prop="attnPhone" label="供应商电话" />
          </el-table>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="supp-table">
          <span class="supp-table-text">物资状态</span>
          <el-table :data="suppliesData" height="200px">
            <el-table-column prop="suppliesName" label="物资名称">
              <template #default="scope">
                <el-tag disable-transitions>{{ scope.row.suppliesName }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="isMar" label="物资状态">
              <template #default="scope">
                <!-- 三元表达式判断,第一个为true,第二个为false -->
                <el-tag disable-transitions>{{ scope.row.isMar == 0 ? '损坏' : '未损坏' }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="suppliesCause" label="损坏原因">
              <template #default="scope">
                <!-- 三元表达式判断,第一个为true,第二个为false -->
                <el-tag disable-transitions>{{ scope.row.suppliesCause !== '' ? scope.row.suppliesCause : '无' }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>

  <!-- 警示信息 -->
  <el-row style="margin-top: 10px">
    <el-col :span="5">
      <div class="supp-table">
        <!-- 损坏图 -->
        <div ref="main" style="height: 300px"></div>
      </div>
    </el-col>
    <el-col :span="19">
      <div class="supp-table">
        <span class="supp-table-text">物资较少数量</span>
        <el-table :data="lessSupplies" height="270px">
          <el-table-column prop="suppliesName" label="物资名称" />
          <el-table-column prop="suppliesUnit" label="物资单位" />
          <el-table-column prop="suppliesCount" label="物资数量" />
          <el-table-column prop="provider" label="物资供货商" />
          <el-table-column prop="suppliesAttn" label="物资联系人" />
          <el-table-column prop="attnPhone" label="联系人手机号" />
        </el-table>
      </div>
    </el-col>
  </el-row>

</template>

<style lang="scss" scoped>
.supp-top-text {
  display: flex;
  align-items: center;
  margin-top: -20px;
  .supp-text {
    margin-right: 5px;
  }
}
.supp-table {
  border: 1px solid skyblue;
  margin-right: 15px;
  box-shadow: 1px 1px 2px skyblue;
}
.supp-table-text {
  display: block;
  background-color: #fff;
  padding: 5px 0px 5px 5px;
  font-weight: bold;
}
</style>
