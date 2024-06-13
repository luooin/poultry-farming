<script setup>
import * as echarts from 'echarts'
/* 导入dayjs */
import dayjs from 'dayjs'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入繁育信息 */
import { BreedList } from '@/apis/breed'
/* 导入畜禽列表 */
import { animalList } from '@/apis/animal'
/* 导入物资列表 */
import { SuppliesList } from '@/apis/supplies'
/* 获取器械列表 */
import { MedicalList } from '@/apis/medical'
/* 获取药品信息 */
import { DrugList } from '@/apis/drug'
import { onBeforeUnmount, computed, onBeforeMount, ref, onMounted } from 'vue'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 繁育实验室数组 */
const breedTableData = ref([])
/* 畜禽列表数组 */
const animalTableData = ref([])
/* 物资列表数组 */
const suppliesTableData = ref([])
/* 医疗器械列表 */
const medicalTableData = ref([])
/* 药品信息列表 */
const drugTableData = ref([])
/* 药品名称对象 */
const drugName = ref()
/* 获取元素对象 */
const main = ref(null)
/* 存储echart实例化对象,用于创建和销毁 */
let myChart

/* 生命周期,挂载前 */
onBeforeMount(() => {
  /* 获取繁育列表 */
  getbreedTableData()
  /* 获取畜禽列表 */
  getanimalTableData()
  /* 获取物资列表 */
  getsuppliesTableData()
  /* 获取医疗器械列表 */
  getmedicalTableData()
  /* 获取药品信息列表 */
  getdrugTableData()
})

/* 生命周期挂载时 */
onMounted(() => {
  /* 调用方法创建环形图 */
  createEcharts()
})

/* 销毁前 */
onBeforeUnmount(() => {
  /* 调用方法销毁 */
  unloadEchart(myChart)
})

/* 获取药品信息列表 */
const getdrugTableData = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await DrugList(data)
  if (ref.code > 0) {
    drugTableData.value = ref.data
  }
}

/* 创建折现图 */
const createEcharts = async () => {
  /* 定义所有日期 */
  let AllDate = []
  /* 定义死亡数组 */
  let die = []
  /* 初始化折线图 */
  myChart = echarts.init(main.value)
  /* 临时参数 */
  const data = { userId: user.userId }
  /* 调用方法 */
  const { data: ref } = await animalList(data)
  /* 循环得到所有日期 */
  ref.data.forEach((item) => AllDate.push(dayjs(item.animalDate).format('YYYY-MM-DD')))
  /* 循环得到所有数量 */
  ref.data.forEach((item) => {
    if (item.animalStatus == 0) {
      die.push(1)
    }
  })
  /* 配置若存在,则调用方法 */
  myChart.setOption({
    xAxis: {
      type: 'category',
      data: AllDate,
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: die,
        type: 'line',
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

/* 获取医疗器械列表 */
const getmedicalTableData = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await MedicalList(data)
  if (ref.code > 0) {
    medicalTableData.value = ref.data
  }
}
/* 计算器械总数量 */
const medicalAllCount = computed(() => {
  let count = 0
  medicalTableData.value.forEach((item) => {
    if (item.medicalCount != '') {
      count += parseInt(item.medicalCount)
    }
  })
  return count
})

/* 损坏器械数量 */
const medicalBadCount = computed(() => {
  let count = 0
  medicalTableData.value.forEach((item) => {
    if (item.isMar == 0) {
      count++
    }
  })
  return count
})

/* 获取物资列表 */
const getsuppliesTableData = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await SuppliesList(data)
  if (ref.code > 0) {
    suppliesTableData.value = ref.data
  }
}

/* 计算物资总数量 */
const suppliesAllCount = computed(() => {
  let count = 0
  suppliesTableData.value.forEach((item) => {
    if (item.suppliesName != '') {
      count += parseInt(item.suppliesCount)
    }
  })
  return count
})

/* 计算物资损坏数量 */
const suppliesBadCount = computed(() => {
  let count = 0
  suppliesTableData.value.forEach((item) => {
    if (item.marCount != null) {
      count += parseInt(item.marCount)
    }
  })
  return count
})

/* 获取繁育列表 */
const getbreedTableData = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await BreedList(data)
  if (ref.code > 0) {
    breedTableData.value = ref.data
  }
}

/* 计算繁育实验室总数量 */
const breedAllCount = computed(() => {
  let count = 0
  /* 循环遍历 */
  breedTableData.value.forEach((item) => {
    if (item.breedName != null) {
      count++
    }
  })
  return count
})

/* 计算繁育实验室总数量 */
const breedFreeCount = computed(() => {
  let count = 0
  /* 循环遍历 */
  breedTableData.value.forEach((item) => {
    /* 取整 */
    if (parseInt(item.breedCount) == 0) {
      count++
    }
  })
  return count
})

/* 获取畜禽列表 */
const getanimalTableData = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  /* 调用方法 */
  const { data: ref } = await animalList(data)
  if (ref.code > 0) {
    animalTableData.value = ref.data
  }
}

/* 计算畜禽总量 */
const animalAllCount = computed(() => {
  let count = 0
  animalTableData.value.forEach((item) => {
    if (item.animalName != '') {
      count++
    }
  })
  return count
})

/* 计算畜禽怀孕数量 */
const animalIncalfCount = computed(() => {
  let count = 0
  animalTableData.value.forEach((item) => {
    if (item.isBreed == 1) {
      count++
    }
  })
  return count
})

/* 计算畜禽死亡数量 */
const animalDieCount = computed(() => {
  let count = 0
  animalTableData.value.forEach((item) => {
    if (item.animalStatus == 0) {
      count++
    }
  })
  return count
})

/* 点击了搜索按钮 */
const searchDrug = () => {
  if (drugName.value == '') {
    getdrugTableData()
  } else {
    drugTableData.value = drugTableData.value.filter((item) => (drugName.value != '' ? drugName.value == item.drugName : ''))
  }
}
</script>

<template>
  <el-scrollbar height="560px">
    <!-- 第一行,一行两列 -->
    <el-row class="home-row2-row">
      <el-col :span="6">
        <!-- 第一列,繁育实验室 -->
        <div class="home-row2-col">
          <el-row>
            <el-col :span="12">
              <div class="row2-view">
                <el-statistic title="繁育实验室总数量" :value="breedAllCount" />
                <el-statistic title="繁育空闲实验室数量" :value="breedFreeCount" />
              </div>
            </el-col>
            <el-col :span="12">
              <div class="row2-table">
                <el-table :data="breedTableData" height="200px">
                  <el-table-column prop="breedName" label="所有实验室名称" />
                </el-table>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>

      <el-col :span="6">
        <!-- 第二列,畜禽怀孕 -->
        <div class="home-row2-col">
          <el-row>
            <el-col :span="12">
              <div class="row2-view">
                <el-statistic title="畜禽总数量" :value="animalAllCount" />
                <el-statistic title="繁育怀孕数量" :value="animalIncalfCount" />
              </div>
            </el-col>
            <el-col :span="12">
              <div class="row2-table">
                <el-table :data="animalTableData" height="200px">
                  <el-table-column prop="animalName" label="所有畜禽名称" />
                </el-table>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>

      <el-col :span="6">
        <!-- 第三列,物资损坏 -->
        <div class="home-row2-col">
          <el-row>
            <el-col :span="12">
              <div class="row2-view">
                <el-statistic title="物资总数量" :value="suppliesAllCount" />
                <el-statistic title="损坏物资数量" :value="suppliesBadCount" />
              </div>
            </el-col>
            <el-col :span="12">
              <div class="row2-table">
                <el-table :data="suppliesTableData" height="200px">
                  <el-table-column prop="suppliesName" label="物资名称" />
                </el-table>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>

      <el-col :span="6">
        <!-- 第四列,医疗器械 -->
        <div class="home-row2-col">
          <el-row>
            <el-col :span="12">
              <div class="row2-view">
                <el-statistic title="器械总数量" :value="medicalAllCount" />
                <el-statistic title="损坏器械数量" :value="medicalBadCount" />
              </div>
            </el-col>
            <el-col :span="12">
              <div class="row2-table">
                <el-table :data="medicalTableData" height="200px">
                  <el-table-column prop="medicalName" label="器械名称" />
                </el-table>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>

    <!-- 第二行 -->
    <el-row class="user-row3-row">
      <el-col :span="12">
        <div class="user-row3-col">
          <!-- 第一列 -->
          <el-row>
            <el-col :span="12">
              <div class="row3-view">
                <el-statistic title="畜禽死亡数量" :value="animalDieCount" />
              </div>
            </el-col>
            <el-col :span="12"> <div ref="main" style="height: 250px; margin: -45px 0px -45px 0px"></div></el-col>
          </el-row>
        </div>
      </el-col>
      <!-- 第二列，采用一行两列的方式 -->
      <el-col :span="12">
        <div class="user-row3-col">
          <el-row>
            <el-col :span="10">
              <div class="home-col">
                <span class="home-row3-text">药品搜索</span>
                <div class="home-row3-search">
                  <el-input v-model="drugName" placeholder="请输入药品名称" style="width: 160px" />
                  <el-button type="primary" style="margin-left: 10px" @click="searchDrug">搜索</el-button>
                </div>
                <div class="home-row3-message"></div>
              </div>
            </el-col>
            <el-col :span="14">
              <div class="home-col">
                <el-table :data="drugTableData" height="160px">
                  <el-table-column prop="drugName" label="药品名称" />
                  <el-table-column prop="drugCount" label="药品数量" />
                  <el-table-column label="生产日期">
                    <template #default="scope">
                      <span>{{ dayjs(scope.row.manufactureDate).format('YYYY-MM-DD') }}</span>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>

    <!-- 第三行 -->
    <el-row class="user-row4-row">
      <el-col :span="12">
        <div class="user-row4-col">
          <!-- 图片 -->
          <div class="row4-left">
            <img src="/head.png" class="row4-left-img" />
          </div>
          <!-- 描叙信息 -->
          <div class="row4-left-text">
            <span>用户名:{{ user.user.name }}</span><br />
            <span>年龄:{{ user.user.age }}</span><br />
            <span>性别:{{ user.user.gender==0? '男':'女' }}</span><br />
            <span>手机号:{{ user.user.phone }}</span><br />
          </div>
        </div>
      </el-col>
      <!-- 第二列，采用一行两列的方式 -->
      <el-col :span="12">
        <div class="user-row4-tab">
          <el-table :data="drugTableData" height="159px">
            <el-table-column prop="drugName" label="药品名称" />
            <el-table-column prop="supplier" label="供应商" />
            <el-table-column prop="category" label="药品分类" />
            <el-table-column prop="specification" label="药品规格" />
            <el-table-column prop="buyerCount" label="采购数量" />
          </el-table>
        </div>
      </el-col>
    </el-row>
  </el-scrollbar>
</template>

<style lang="scss" scoped>
.home-row2-row {
  margin-top: 10px;
  .home-row2-col {
    border: 1px solid skyblue;
    margin-right: 5px;
    border-radius: 10px;
    box-shadow: 1px 1px 2px skyblue;
    overflow: hidden;
    .row2-view {
      display: flex;
      flex-direction: column;
      align-items: center;
      .el-statistic {
        text-align: center;
        margin: 30px 0px 10px 0px;
      }
    }
  }
}
.user-row3-row {
  margin-top: 10px;
  .user-row3-col {
    border: 1px solid skyblue;
    border-radius: 10px;
    box-shadow: 1px 1px 2px skyblue;
    margin-right: 5px;
    .row3-view {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 50px;
    }
    .home-col {
      margin-left: 5px;
      position: relative;
      .home-row3-text {
        display: block;
        position: absolute;
        top: 40px;
        font-weight: bold;
        margin-left: 5px;
      }
      .home-row3-search {
        line-height: 160px;
      }
    }
  }
}
.user-row4-row {
  border-radius: 10px;
  margin-top: 10px;
  margin-right: 5px;
  .user-row4-col {
    border: 1px solid skyblue;
    border-radius: 10px;
    box-shadow: 1px 1px 2px skyblue;
    overflow: hidden;
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-right: 2px;
    .row4-left-img {
      border-radius: 100%;
      width: 150px;
      height: auto;
      margin-top: 5px;
    }
  }
  .user-row4-tab {
    overflow: hidden;
    border-radius: 10px;
    margin-left: 3px;
    border: 1px solid skyblue;
    box-shadow: 1px 1px 2px skyblue;
  }
}
</style>
