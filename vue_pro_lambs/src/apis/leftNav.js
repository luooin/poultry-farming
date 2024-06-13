//左侧导航栏相关
/* 导入axios */
import httpInterface from '../util/https'
/**
 * 获取左侧导航栏
 * @returns
 */
export function getLeftNav() {
  return httpInterface({
    url: '/lambs/left',
    method: 'GET',
  })
}
