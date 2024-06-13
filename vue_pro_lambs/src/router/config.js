/* 导入所需要的组件 */
import Home from '@/views/home/Home.vue'
/* 畜禽管理 */
import AnimalList from '@/views/animal-manage/AnimalList.vue'
import AddAnimal from '@/views/animal-manage/AddAnimal.vue'
/* 饲料管理 */
import AddFeed from '@/views/feed-manage/AddFeed.vue'
/* 防疫健康 */
import MedicalList from '@/views/epidemic-manage/MedicalList.vue'
import DrugList from '@/views/epidemic-manage/DrugList.vue'
import EpidemicList from '@/views/epidemic-manage/EpidemicList.vue'
/* 繁育管理 */
import BreedList from '@/views/breed-manage/BreedList.vue'
/* 放养管理 */
import FangyangList from '@/views/fangyang-manage/FangyangList.vue'
import AddFangyang from '@/views/fangyang-manage/AddFangyang.vue'
/* 物资管理 */
import SuppliesList from '@/views/supplies-manage/SuppliesList.vue'
import AddSupplies from '@/views/supplies-manage/AddSupplies.vue'
/* 系统管理 */
import UserList from '@/views/system-manage/UserList.vue'
import UserRegister from '@/views/system-manage/UserRegister.vue'
// 定义路由数组配置
const routes = [
  /* 主页面 */
  {
    path: '/index',
    name: 'index',
    component: Home,
  },
  /* 畜禽管理-列表 */
  {
    path: '/animal-manage/list',
    name: 'AnimalList',
    component: AnimalList,
  },
  /* 畜禽管理-添加畜禽 */
  {
    path: '/animal-manage/addanimal',
    name: 'AddAnimal',
    component: AddAnimal,
  },
  /* 饲料管理-添加饲料 */
  {
    path: '/feed-manage/addfeed',
    name: 'AddFeed',
    component: AddFeed,
  },
  /* 防疫健康-药品 */
  {
    path: '/epidemic-manage/drug',
    name: 'DrugList',
    component: DrugList,
  },
  /* 防疫健康-医疗器械 */
  {
    path: '/epidemic-manage/medical',
    name: 'MedicalList',
    component: MedicalList,
  },
  /* 防疫健康-列表 */
  {
    path: '/epidemic-manage/list',
    name: 'EpidemicList',
    component: EpidemicList,
  },
  /* 繁育管理-列表 */
  {
    path: '/breed-manage/list',
    name: 'BreedList',
    component: BreedList,
  },
  /* 放养管理-列表 */
  {
    path: '/fangyang-manage/list',
    name: 'FangyangList',
    component: FangyangList,
  },
  /* 放养管理-添加 */
  {
    path: '/fangyang-manage/addfang',
    name: 'AddFang',
    component: AddFangyang,
  },
  /* 物资管理-列表 */
  {
    path: '/supplies-manage/list',
    name: 'SuppliesList',
    component: SuppliesList,
  },
  /* 物资管理-添加物资 */
  {
    path: '/supplies-manage/addsupplies',
    name: 'AddSupplies',
    component: AddSupplies,
  },
  /* 系统管理-用户 */
  {
    path: '/system-manage/users',
    name: 'Users',
    component: UserList,
  },
  /* 系统管理-用户注册 */
  {
    path: '/system-manage/userigister',
    name: 'UserRigister',
    component: UserRegister,
  },
]

export default routes
