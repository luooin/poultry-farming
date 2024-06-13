<script setup>
import * as echarts from 'echarts'
/* 导入获取畜禽列表方法 */
import { animalList, updateAnimalList, addAnimal, deleteAnimal } from '@/apis/animal'
/* 导入获取药品列表方法 */
import { DrugList } from '@/apis/drug'
/* 导入获取饲料列表方法 */
import { FeedList } from '@/apis/feed'
/* 导入用户信息状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入dayjs */
import dayjs from 'dayjs'
import { DArrowRight, Search, Plus, Delete } from '@element-plus/icons-vue'
import { onBeforeMount, onBeforeUnmount, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'

/* 实例化用户 */
const { user } = useUserStore()
/* 修改按钮显示隐藏 */
const showBtn = ref(false)
/* 表格畜禽对象 */
const tableAnimal = ref([])
/* 疫苗列表数组 */
const drugSelectList = ref([])
/* 饲料列表数组 */
const feedSelectList = ref([])
/* 表单畜禽对象 */
const ruleFormRef = ref()
/* 获取元素对象 */
const main = ref(null)
/* 存储echart实例化对象,用于创建和销毁 */
let myChart

/* 表单对象 */
const ruleForm = reactive({
  id: '',
  animalName: '',
  animalSex: '',
  animalDate: '',
  animalStatus: '',
  animalWeight: '',
  isBreed: '',
  feedsId: '',
  drugId: '',
  userId: '',
})

/* 查找畜禽对象 */
const sendform = reactive({
  animalName: '',
  animalSex: '',
  animalDate: '',
  animalStatus: '',
  isBreed: '',
})

/* 表单畜禽规则 */
const rules = reactive({
  animalName: [{ required: true, message: '请输入畜禽名称', trigger: 'blur' }],
  animalSex: [{ required: true, message: '请输入畜禽性别', trigger: 'blur' }],
  animalDate: [{ required: true, message: '请选择出生日期', trigger: 'blur' }],
  animalStatus: [{ required: true, message: '请选择健康状态', trigger: 'blur' }],
  animalWeight: [{ required: true, message: '请输入体重', trigger: 'blur' }],
  isBreed: [{ required: true, message: '请选择是否怀孕', trigger: 'blur' }],
  feedsId: [{ required: true, message: '请选择饲料信息', trigger: 'blur' }],
  drugId: [{ required: true, message: '请选择疫苗信息', trigger: 'blur' }],
})

/* 生命周期函数,挂载前 */
onBeforeMount(() => {
  /* 获取畜禽列表方法 */
  getAnimalList()
  /* 获取药品列表 */
  getDrugList()
  /* 获取饲料列表 */
  getFeedList()
})

/* 生命周期函数挂载 */
onMounted(() => {
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
  /* 定义临时数组 */
  let ringData = []
  const describe = ['畜禽总数量', '怀孕总数量', '不健康总数量']
  /* 定义临时变量 */
  let animalCount=0
  let incalf=0
  let unhealth=0
  /* 初始化环形图 */
  myChart = echarts.init(main.value)
  /* 临时数据 */
  const data = { userId: user.userId }
  /* 获取畜禽列表 */
  const { data: ref } = await animalList(data)
  /* 获取畜禽总量 */
  ref.data.forEach((item) => {
    if (item.animalName != '') {
      animalCount++
    }
  })
  /* 获取怀孕总量 */
  ref.data.forEach((item) => {
    if (item.isBreed == 1) {
      incalf++
    }
  })
  /* 获取不健康总量 */
  ref.data.forEach((item) => {
    if (item.animalStatus == 2) {
      unhealth++
    }
  })
  /* 数组写入数据 */
  ringData.push({value:animalCount,name:describe[0]})
  ringData.push({value:incalf,name:describe[1]})
  ringData.push({value:unhealth,name:describe[2]})
  /* 配置若存在,则调用方法 */
  myChart.setOption({
    title: {
      text: '畜禽信息',
      subtext: '畜禽简介',
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
        name: '名称',
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
        data: ringData,
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

/* 获取畜禽列表方法 */
const getAnimalList = async () => {
  /* 临时数据 */
  const data = { userId: user.userId }
  const { data: ref } = await animalList(data)
  if (ref.code > 0) {
    tableAnimal.value = ref.data
  }
}

/* 获取药品列表 */
const getDrugList = async () => {
  /* 临时数据 */
  const data = { userId: user.userId }
  const { data: ref } = await DrugList(data)
  drugSelectList.value = ref.data.filter((item) => item.category == '疫苗' && parseInt(item.drugCount) > 0)
}

/* 获取饲料列表 */
const getFeedList = async () => {
  /* 临时数据 */
  const data = { userId: user.userId }
  /* 获取方法 */
  const { data: ref } = await FeedList(data)
  /* 赋值 */
  feedSelectList.value = ref.data
}
/* 点击了查询按钮 */
const findAnimal = (value) => {
  /* 非空判断 */
  if (value.animalName == '' && value.animalSex == '' && value.animalDate == '' && value.animalStatus == '' && value.isBreed == '') {
    /* 刷新页面 */
    getAnimalList()
  } else {
    tableAnimal.value = tableAnimal.value.filter((item) =>
      value.animalName != ''
        ? value.animalName == item.animalName
        : '' || value.animalSex != ''
        ? value.animalSex == item.animalSex
        : '' || value.animalDate != ''
        ? value.animalDate == item.animalDate
        : '' || value.animalStatus != ''
        ? value.animalStatus == item.animalStatus
        : '' || value.isBreed != ''
        ? value.isBreed == item.isBreed
        : ''
    )
  }
}
/* 点击了清空按钮 */
const SetNullForm = () => {
  /* 显示查询按钮 */
  showBtn.value = false
  /* 调用清空方法 */
  emptyForm()
}
/* 清空查询表单 */
const emptyForm = () => {
  (ruleForm.animalName = ''), (ruleForm.animalSex = ''), (ruleForm.animalDate = ''), (ruleForm.animalStatus = ''), (ruleForm.animalWeight = ''), (ruleForm.isBreed = ''), (ruleForm.feedsId = ''), (ruleForm.drugId = ''), (ruleForm.userId = ''), (ruleForm.id = '')
}
/* 新增畜禽 */
const addAnimalBtn = (formEl) => {
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 临时赋值 */
      ruleForm.userId = user.userId
      console.log(ruleForm)
      const { data: ref } = await addAnimal(ruleForm)
      /* 是否成功 */
      if (ref.code > 0) {
        /* 清空表单 */
        emptyForm()
        /* 刷新页面 */
        getAnimalList()
        ElMessage.success(ref.msg)
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}
/* 表格中删除 */
const handleAnimDelete = async (item) => {
  /* 临时赋值 */
  item.userId = user.userId
  const { data: ref } = await deleteAnimal(item)
  /* 判断是否成功 */
  if (ref.code > 0) {
    /* 刷新表格 */
    getAnimalList()
    ElMessage.success(ref.msg)
  } else {
    ElMessage.info(ref.msg)
  }
}
/* 表格中修改按钮点击 */
const alterbtnShow = (item) => {
  /* 将指定的值赋值给表单 */
  ruleForm.id = item.id
  ruleForm.animalName = item.animalName
  ruleForm.animalSex = item.animalSex
  ruleForm.animalDate = item.animalDate
  ruleForm.animalStatus = item.animalStatus
  ruleForm.animalWeight = item.animalWeight
  ruleForm.isBreed = item.isBreed
  ruleForm.feedsId = item.feedsId
  ruleForm.drugId = item.drugId
  /* 修改按钮显示 */
  showBtn.value = true
  /* 提示信息 */
  ElMessage.info('信息在控制台中显示')
}

/* 查找的清空按钮 */
const cleanFindtable = () => {
  sendform.animalName = ''
  sendform.animalSex = ''
  sendform.animalDate = ''
  sendform.animalStatus = ''
  sendform.isBreed = ''
}

/* 点击控制台中的修改按钮 */
const updateAnimal = (formEl) => {
  formEl.validate(async (vallid) => {
    if (vallid) {
      /* 临时参数赋值 */
      ruleForm.userId = user.userId
      const { data: ref } = await updateAnimalList(ruleForm)
      /* 非空判断是否成功 */
      if (ref.code > 0) {
        /* 清空表单 */
        emptyForm()
        /* 刷新页面 */
        getAnimalList()
        ElMessage.success(ref.msg)
        /* 修改按钮显示 */
        showBtn.value = false
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请填写相应的信息')
    }
  })
}
</script>

<template>
  <!-- 头部 -->
  <div class="supp-top-text">
    <h3 class="supp-text">添加畜禽</h3>
    <el-icon><DArrowRight /></el-icon>
  </div>
  <!-- 上部 -->
  <el-row>
    <el-col :span="5">
      <!-- 畜禽总数量、不健康畜禽数量、怀孕畜禽数量 -->
      <div class="supp-top-left">
        <div ref="main" style="height: 300px"></div>
      </div>
    </el-col>
    <el-col :span="19">
      <div class="supp-top-center">畜禽控制台</div>
      <el-row>
        <!-- 查找信息 -->
        <el-col :span="7">
          <div class="supp-top-form-left">
            <span class="supp-top-form-span">查找</span>
            <!-- 查找 -->
            <div class="supp-top-form-send">
              <span class="supp-top-form-send-text">畜禽名称</span>
              <el-input v-model="sendform.animalName" clearable placeholder="请输入畜禽名称" :prefix-icon="Search" style="width: 200px; margin: 0px 0px 5px 10px" />
              <span class="supp-top-form-send-text">健康状态</span>
              <el-radio-group v-model="sendform.animalStatus" style="width: 200px; padding-left: 20px">
                <el-radio :label="1">健康</el-radio>
                <el-radio :label="2">不健康</el-radio>
              </el-radio-group>
              <span class="supp-top-form-send-text">出生日期</span>
              <el-date-picker v-model="sendform.animalDate" type="date" label="出生日期" placeholder="请输入畜禽出生日期" style="width: 200px; margin: 0px 0px 5px 10px" />
              <span class="supp-top-form-send-text">是否有孕</span>
              <el-radio-group v-model="sendform.isBreed" style="margin-left: 10px; width: 200px">
                <el-radio :label="0">未怀孕</el-radio>
                <el-radio :label="1">怀孕</el-radio>
              </el-radio-group>
              <span class="supp-top-form-send-text">畜禽性别</span>
              <el-radio-group v-model="sendform.animalSex" style="margin-left: 10px; width: 200px">
                <el-radio :label="1">雌性</el-radio>
                <el-radio :label="2">雄性</el-radio>
              </el-radio-group>
              <div class="supp-top-form-send-button">
                <el-button type="primary" :icon="Search" @click="findAnimal(sendform)">查询</el-button>
                <el-button type="info" :icon="Delete" @click="cleanFindtable()">清空</el-button>
              </div>
            </div>
          </div>
        </el-col>
        <!-- 新增和修改表单 -->
        <el-col :span="17">
          <div class="supp-top-form-right">
            <span class="supp-top-form-span">新增|修改</span>
            <!-- 新增和修改表单 -->
            <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="90px" status-icon :inline="true">
              <el-form-item label="畜禽名称" prop="animalName" class="form-item">
                <el-input v-model.trim="ruleForm.animalName" placeholder="请输入畜禽名称" />
              </el-form-item>
              <el-form-item label="畜禽性别" prop="animalSex" class="form-item">
                <el-radio-group v-model="ruleForm.animalSex">
                  <el-radio :label="1">雌性</el-radio>
                  <el-radio :label="2">雄性</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="出生日期" prop="animalDate" class="form-item">
                <el-date-picker v-model="ruleForm.animalDate" type="date" label="出生日期" placeholder="请输入畜禽出生日期" style="width: 195px" />
              </el-form-item>
              <el-form-item label="健康状态" prop="animalStatus">
                <!-- 单选框 -->
                <el-radio-group v-model="ruleForm.animalStatus">
                  <el-radio :label="0">死亡</el-radio>
                  <el-radio :label="1">健康</el-radio>
                  <el-radio :label="2">不健康</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="畜禽体重" prop="animalWeight" class="form-item">
                <el-input v-model.trim="ruleForm.animalWeight" type="number" placeholder="请输入畜禽体重" />
              </el-form-item>
              <el-form-item label="是否有孕" prop="isBreed">
                <!-- 单选框 -->
                <el-radio-group v-model="ruleForm.isBreed">
                  <el-radio :label="0">未怀孕</el-radio>
                  <el-radio :label="1">怀孕</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="喂养饲料" prop="feedsId">
                <el-select v-model="ruleForm.feedsId" class="m-2" placeholder="请选择喂养饲料" size="small">
                  <el-option v-for="item in feedSelectList" :key="item.id" :label="item.feedName" :value="item.id" />
                </el-select>
              </el-form-item>
              <el-form-item label="注射疫苗" prop="drugId">
                <el-select v-model="ruleForm.drugId" class="m-2" placeholder="请选择注射疫苗" size="small">
                  <el-option v-for="item in drugSelectList" :key="item.id" :label="item.drugName" :value="item.id" />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" style="margin-right: 20px" :icon="Plus" @click="addAnimalBtn(ruleFormRef)" v-show="!showBtn">新增</el-button>
                <el-button type="info" style="margin-right: 20px" @click="SetNullForm()">清空</el-button>
                <el-button type="primary" v-show="showBtn" @click="updateAnimal(ruleFormRef)">修改</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
    </el-col>
  </el-row>

  <!-- 表单内容 -->
  <div class="Addsupplies-table">
    <el-table :data="tableAnimal" style="width: 100%" height="250px" :default-sort="{ prop: 'animalWeight', order: 'descending' }">
      <el-table-column prop="animalName" label="畜禽名称">
        <template #default="scope">
          <el-tag>{{ scope.row.animalName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="animalSex" label="畜禽性别">
        <template #default="scope">
          <el-tag>{{ scope.row.animalSex == 1 ? '雌' : '雄' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="出生日期">
        <template #default="scope">
          <!-- 日期格式化 -->
          <el-tag>{{ dayjs(scope.row.animalDate).format('YYYY-MM-DD') }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="健康状态">
        <template #default="scope">
          <!-- 三元运算符,先判断是否为0,再判断是否为1 -->
          <el-tag>{{ scope.row.animalStatus == 0 ? '死亡' : scope.row.animalStatus == 1 ? '健康' : '不健康' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="animalWeight" sortable="" label="体重">
        <template #default="scope">
          <el-tag>{{ scope.row.animalWeight }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否怀孕">
        <template #default="scope">
          <!-- 三元运算符判断 -->
          <el-tag>{{ scope.row.isBreed == 0 ? '未怀孕' : '怀孕' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="喂养饲料">
        <template #default="scope">
          <el-tag>{{ scope.row.feeds.feedName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="注射疫苗">
        <template #default="scope">
          <el-tag>{{ scope.row.drug.drugName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="alterbtnShow(scope.row)">修改</el-button>
          <el-popconfirm title="确认删除吗?" @confirm="handleAnimDelete(scope.row)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style lang="scss" scoped>
.supp-top-text {
  margin-top: -20px;
  display: flex;
  align-items: center;
  .supp-text {
    margin-right: 5px;
  }
}
.supp-top-left {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 2px 2px 2px skyblue;
  margin-right: 5px;
  padding-bottom: 13px;
}
.supp-top-center {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  text-align: center;
  font-weight: bold;
  font-size: 18px;
  color: rgb(70, 70, 70);
  margin-bottom: 5px;
}
.supp-top-form-left {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  padding-left: 10px;
  height: 283px;
  .supp-top-form-span {
    font-size: 18px;
  }
  .supp-top-form-send {
    margin-top: 5px;
    .supp-top-form-send-text {
      display: inline-block;
    }
    .supp-top-form-send-button {
      margin-top: 25px;
      text-align: center;
      margin-bottom: 5px;
    }
  }
}
.supp-top-form-right {
  border: 1px solid skyblue;
  border-radius: 10px;
  box-shadow: 1px 1px 2px skyblue;
  padding-left: 10px;
  margin-left: 5px;
  padding-bottom: 9px;
  .supp-top-form-span {
    font-size: 18px;
  }
}
.Addsupplies-table {
  margin-top: 10px;
}
</style>
