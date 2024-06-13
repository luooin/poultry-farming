/* 定义其他数据 */

/* 性别 */
const gender = [
  {
    id: 1,
    label: '男',
    value: 0,
  },
  {
    id: 2,
    label: '女',
    value: 1,
  },
]

/* 药品分类 */
const drugClassify = [
  {
    id: 1,
    label: '消毒剂',
    value: '消毒剂',
  },
  {
    id: 2,
    label: '抗生素',
    value: '抗生素',
  },
  {
    id: 3,
    label: '疫苗',
    value: '疫苗',
  },
  {
    id: 4,
    label: '营养药品',
    value: '营养药品',
  },
  {
    id: 5,
    label: '饲料添加剂',
    value: '饲料添加剂',
  },
  {
    id: 5,
    label: '维生素',
    value: '维生素',
  },
]

/* 饲料状态 */
const feedStatusList = [
  { id: 1, label: '缺乏', value: 0 },
  { id: 2, label: '少量', value: 1 },
  { id: 3, label: '较多', value: 2 },
]

/* 导出 */
export { gender, drugClassify,feedStatusList }
