/* 放养信息方法 */
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 传入参数添加放养信息
 * @param {*} data
 * @returns
 */
export function addFangyang(data) {
  return httpInterface({
    url: '/lambs/fang',
    method: 'POST',
    data,
  })
}

/**
 * 获取所有放养信息
 * @param {*} params
 * @returns
 */
export function getFangyang(params) {
  return httpInterface({
    url: '/lambs/fang',
    method: 'GET',
    params,
  })
}
