//权限相关
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 获取权限信息
 * @returns 
 */
export function getPowerList() {
  return httpInterface({
    url: '/lambs/power',
    method: 'GET',
  })
}
