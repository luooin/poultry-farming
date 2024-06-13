<script setup>
import { DArrowRight, Search, Plus } from '@element-plus/icons-vue'
/* 导入饲料列表方法 */
import { FeedList, deleteFeed, addFeed, updateFeed } from '@/apis/feed'
/* 导入用户状态 */
import { useUserStore } from '@/stores/UserStore'
/* 导入dayjs */
import dayjs from 'dayjs'
/* 导入自定义方法 */
import { feedStatusList } from '@/util/other'
import { onMounted, ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

/* 实例化用户状态 */
const { user } = useUserStore()
/* 新增对话框控制显示隐藏对象 */
const dialogVisible = ref(false)
/* 修改对话框控制显示隐藏对象 */
const Aliterdialog = ref(false)
/* 表单饲料对象 */
const ruleFormRef = ref()
/* 表格饲料数组 */
const tableFeedData = ref([])

/* 表单饲料元素 */
const ruleForm = reactive({
  id: '',
  feedName: '',
  feedCount: '',
  feedPrice: '',
  feedOrigin: '',
  feedStatus: '',
  feedPlace: '',
  feedDate: '',
  describle: '',
  userId: '',
})
/* 顶部饲料对象 */
const topFeedObject = ref({
  feedName: '',
  feedCount: '',
  feedPrice: '',
  feedPlace: '',
})
/* 表单饲料规则 */
const rules = reactive({
  feedName: [{ required: true, message: '请输入饲料名称', trigger: 'blur' }],
  feedCount: [{ required: true, message: '请输入饲料吨数', trigger: 'blur' }],
  feedPrice: [{ required: true, message: '请输入饲料价格', trigger: 'blur' }],
  feedOrigin: [{ required: true, message: '请输入饲料产地', trigger: 'blur' }],
  feedStatus: [{ required: true, message: '请选择饲料状态', trigger: 'blur' }],
  feedPlace: [{ required: true, message: '请输入饲料存放位置', trigger: 'blur' }],
  feedDate: [{ required: true, message: '请选择饲料存放时间', trigger: 'blur' }],
  describle: [{ required: true, message: '请输入饲料描述', trigger: 'blur' }],
})

/* 获取饲料列表方法 */
const getFeedList = async () => {
  /* 临时参数 */
  const data = { userId: user.userId }
  const { data: ref } = await FeedList(data)
  /* 判断是否返回成功 */
  if (ref.code > 0) {
    tableFeedData.value = ref.data
  } else {
    ElMessage.info(ref.msg)
  }
}
/* 页面挂载时 */
onMounted(() => {
  /* 获取饲料列表方法 */
  getFeedList()
})

/* 弹窗中新增按钮点击 */
const addFeedMessage = (formEl) => {
  formEl.validate(async (vallid) => {
    /* 若有一项为空 */
    if (vallid) {
      /* 临时赋值 */
      ruleForm.userId = user.userId
      const { data: ref } = await addFeed(ruleForm)
      /* 判断是否成功 */
      if (ref.code > 0) {
        /* 刷新页面 */
        getFeedList()
        /* 清空表单 */
        setNull()
        /* 关闭对话框 */
        cancelAddFeed()
        ElMessage.success(ref.msg)
      } else {
        ElMessage.info(ref.msg)
      }
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}

/* 头部新增按钮点击 */
const addFeedBtn = () => {
  /* 控制对话框是否显示 */
  dialogVisible.value = true
}

/* 头部查询按钮点击 */
const selectFeed = (item) => {
  /* 非空判断 */
  if (item.feedName == '' && item.feedPrice == '' && item.feedCount == '' && item.feedPlace == '') {
    /* 刷新页面 */
    getFeedList()
  } else {
    /* 过滤出符合要求的数据 */
    tableFeedData.value = tableFeedData.value.filter((value) =>
      item.feedName != '' ? item.feedName == value.feedName : '' || item.feedPrice != '' ? item.feedPrice == value.feedPrice : '' || item.feedCount != '' ? item.feedCount == value.feedCount : '' || item.feedPlace != '' ? item.feedPlace == value.feedPlace : ''
    )
  }
}

/* 饲料表点击了删除按钮 */
const handleFeedDelete = async (item) => {
  /* 设置参数 */
  item.userId = user.userId
  const { data: ref } = await deleteFeed(item)
  if (ref.code > 0) {
    /* 刷新页面 */
    getFeedList()
    ElMessage.success(ref.msg)
  } else {
    ElMessage.info(ref.msg)
  }
}

/* 关闭对话框 */
const closeDialog = () => {
  /* 对话框状态设置为false */
  dialogVisible.value = false
  /* 设置表单饲料为空 */
  setNull()
}

/* 点击了对话框中的取消按钮 */
const cancelAddFeed = () => {
  /* 对话框状态设置为false */
  dialogVisible.value = false
  /* 设置表单饲料为空 */
  setNull()
}

/* 设置表单饲料元素为空 */
const setNull = () => {
  (ruleForm.feedName = ''), (ruleForm.feedCount = ''), (ruleForm.feedPrice = ''), (ruleForm.feedOrigin = ''), (ruleForm.feedStatus = ''), (ruleForm.feedPlace = ''), (ruleForm.feedDate = ''), (ruleForm.describle = ''), (ruleForm.userId = '')
}

/* 修改表单数据 */
const handleFeedEdit = (item) => {
  /* 赋值 */
  (ruleForm.id = item.id),
    (ruleForm.feedName = item.feedName),
    (ruleForm.feedCount = item.feedCount),
    (ruleForm.feedPrice = item.feedPrice),
    (ruleForm.feedOrigin = item.feedOrigin),
    (ruleForm.feedStatus = item.feedStatus),
    (ruleForm.feedPlace = item.feedPlace),
    (ruleForm.feedDate = item.feedDate),
    (ruleForm.describle = item.describle),
    (ruleForm.userId = item.userId)
  /* 打开对话框 */
  Aliterdialog.value = true
}

/* 修改表单的数据 */
const AliterFeedMessage = (formEl) => {
  /* 表单校验 */
  formEl.validate(async (vallid) => {
    /* 若有一项为空弹出错误信息 */
    if (vallid) {
      /* 临时赋值 */
      ruleForm.userId = user.userId
      const { data: ref } = await updateFeed(ruleForm)
      /* 是否成功 */2
      if (ref.code > 0) {
        /* 刷新页面 */
        getFeedList()
        /* 清空表单,关闭对话框 */
        cancelAliterFeed()
      }else{
        ElMessage.info(ref.msg)
      }
      console.log(ruleForm)
    } else {
      ElMessage.warning('请将信息填写完整')
    }
  })
}

/* 修改表单中的取消按钮 */
const cancelAliterFeed = () => {
  Aliterdialog.value = false
  /* 设置表单饲料为空 */
  setNull()
}
</script>

<template>
  <!-- 顶部组件 -->
  <div class="feed-top">
    <div class="feed-top-text">
      <h3 class="feed-text">查找内容</h3>
      <el-icon style="margin-top: 6px"><DArrowRight /></el-icon>
    </div>
    <div class="feed-top-container">
      <!-- 顶部左边 -->
      <div class="feed-top-left">
        <div class="feed-top-input">
          <span class="feed-top-input-text">饲料名称</span>
          <el-input v-model="topFeedObject.feedName" clearable placeholder="请输入饲料名称" :prefix-icon="Search" style="width: 160px" />
          <span class="feed-top-input-text">饲料数量</span>
          <el-input v-model="topFeedObject.feedCount" clearable placeholder="请输入饲料数量" :prefix-icon="Search" style="width: 160px" />
          <span class="feed-top-input-text">饲料价格</span>
          <el-input v-model="topFeedObject.feedPrice" clearable placeholder="请输入饲料价格" :prefix-icon="Search" style="width: 160px" />
          <span class="feed-top-input-text">饲料存放位置</span>
          <el-input v-model="topFeedObject.feedPlace" clearable placeholder="请输入饲料存放位置" :prefix-icon="Search" style="width: 180px" />
        </div>
      </div>
      <!-- 顶部右边 -->
      <div class="feed-top-right">
        <el-button type="primary" :icon="Search" style="margin-bottom: 10px" @click="selectFeed(topFeedObject)">查询</el-button>
        <el-button type="primary" :icon="Plus" @click="addFeedBtn">新增</el-button>
      </div>
    </div>
  </div>
  <div class="feed-container">
    <!-- 表格 -->
    <div class="feed-right">
      <!-- 遍历饲料表的数据,吨数和日期加了排序,排序暂时没有修改 -->
      <el-table :data="tableFeedData" height="435px" :stripe="true" :default-sort="{ prop: 'feedCount', order: 'descending' }" :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }">
        <el-table-column label="饲料名称">
          <template #default="scope">
            <el-tag>{{ scope.row.feedName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="feedCount" label="饲料吨数" sortable>
          <template #default="scope">
            <el-tag>{{ scope.row.feedCount }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="feedPrice" label="饲料价格" sortable>
          <template #default="scope">
            <el-tag>{{ scope.row.feedPrice }}￥</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="饲料状态">
          <template #default="scope">
            <!-- 三元运算符判断,若为0(scope.row.feedStatus==0? '缺乏': ''),则缺乏,不为0则判断是否为1(scope.row.feedStatus==1? '少量':'较多'),为1则为少量,不为1则较多 -->
            <el-tag>{{ scope.row.feedStatus == 0 ? '缺乏' : scope.row.feedStatus == 1 ? '少量' : '较多' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="存放位置">
          <template #default="scope">
            <el-tag>{{ scope.row.feedPlace }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="存放日期" sortable>
          <template #default="scope">
            <el-tag>{{ dayjs(scope.row.feedDate).format('YYYY-MM-DD') }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="饲料产地">
          <template #default="scope">
            <el-tag>{{ scope.row.feedOrigin }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" @click="handleFeedEdit(scope.row)" style="margin-right: 10px">修改</el-button>
            <el-popconfirm title="确认删除吗?" @confirm="handleFeedDelete(scope.row)">
              <template #reference>
                <el-button size="small" type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>

  <!-- 新增对话框 -->
  <el-dialog v-model="dialogVisible" title="新增饲料信息" width="29%" class="add-dialog" @close="closeDialog">
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="80px" class="form-input" status-icon>
      <el-form-item label="饲料名称" prop="feedName" class="form-item">
        <el-input v-model.trim="ruleForm.feedName" placeholder="请输入饲料名称" />
      </el-form-item>
      <el-form-item label="饲料数量" prop="feedCount" class="form-item">
        <el-input v-model.trim="ruleForm.feedCount" type="number" placeholder="请输入饲料数量" />
      </el-form-item>
      <el-form-item label="饲料价格" prop="feedPrice" class="form-item">
        <el-input v-model.trim="ruleForm.feedPrice" type="number" placeholder="请输入饲料价格" />
      </el-form-item>
      <el-form-item label="饲料产地" prop="feedOrigin" class="form-item">
        <el-input v-model.trim="ruleForm.feedOrigin" placeholder="请输入饲料产地" />
      </el-form-item>
      <el-form-item label="饲料状态" prop="feedStatus">
        <el-select v-model.trim="ruleForm.feedStatus" placeholder="请选择饲料状态">
          <el-option label="缺乏" value="0" />
          <el-option label="少量" value="1" />
          <el-option label="较多" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="存放位置" prop="feedPlace" class="form-item">
        <el-input v-model.trim="ruleForm.feedPlace" placeholder="请输入存放位置" />
      </el-form-item>
      <el-form-item label="存放时间" prop="feedDate" class="form-item">
        <el-date-picker v-model.trim="ruleForm.feedDate" type="date" placeholder="请选择时间" style="width: 100%" />
      </el-form-item>
      <el-form-item label="饲料描叙" prop="describle" style="width: 383px">
        <el-input v-model.trim="ruleForm.describle" maxlength="80" show-word-limit type="textarea" placeholder="请输入饲料描叙" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="margin-right: 20px" @click="addFeedMessage(ruleFormRef)">新增</el-button>
        <el-button type="info" @click="cancelAddFeed">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <!-- 修改对话框 -->
  <el-dialog v-model="Aliterdialog" title="修改饲料信息" width="28%" class="add-dialog" @close="closeDialog">
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="80px" class="form-input" status-icon>
      <el-form-item label="饲料名称" prop="feedName" class="form-item">
        <el-input v-model.trim="ruleForm.feedName" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="饲料吨数" prop="feedCount" class="form-item">
        <el-input v-model.trim="ruleForm.feedCount" type="number" placeholder="请输入饲料吨数" />
      </el-form-item>
      <el-form-item label="饲料价格" prop="feedPrice" class="form-item">
        <el-input v-model.trim="ruleForm.feedPrice" type="number" placeholder="请输入饲料价格" />
      </el-form-item>
      <el-form-item label="饲料状态" prop="feedStatus">
        <el-select v-model.trim="ruleForm.feedStatus" placeholder="请选择饲料状态">
          <el-option v-for="item in feedStatusList" :key="item.id" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="存放位置" prop="feedPlace" class="form-item">
        <el-input v-model.trim="ruleForm.feedPlace" placeholder="请输入存放位置" />
      </el-form-item>
      <el-form-item label="存放时间" prop="feedDate" class="form-item">
        <el-date-picker v-model.trim="ruleForm.feedDate" type="date" placeholder="请选择时间" style="width: 100%" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="margin-right: 20px" @click="AliterFeedMessage(ruleFormRef)">修改</el-button>
        <el-button type="info" @click="cancelAliterFeed">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style lang="scss" scoped>
.el-button + .el-button {
  margin-left: 0px;
}
:deep(.el-input__wrapper) {
  flex-grow: 0;
  width: 280px;
}
.feed-top {
  .feed-top-text {
    display: flex;
    align-items: center;
  }
  .feed-top-container {
    display: flex;
    justify-content: space-between;
    .feed-top-left {
      .feed-top-input {
        padding: 5px;
        margin-bottom: 5px;
        .feed-top-input-text {
          margin: 0px 5px 0px 5px;
          font-size: 14px;
        }
      }
    }
    .feed-top-right {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin-right: 20px;
    }
  }
}
.feed-container {
  .feed-right {
    margin-top: 25px;
    margin-right: 10px;
  }
}
</style>
