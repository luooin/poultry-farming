// 物资列表
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 获取物资列表
 * @returns
 */
export function SuppliesList(params) {
  return httpInterface({
    url: '/lambs/supplies',
    method: 'GET',
    params,
  })
}
/**
 * 传入物资信息删除物资
 * @param {*} data
 * @returns
 */
export function deleteSupplies(data) {
  return httpInterface({
    url: '/lambs/supplies',
    method: 'DELETE',
    data,
  })
}

/**
 * 传入物资信息修改物资
 * @param {*} data
 * @returns
 */
export function updateSupplies(data) {
  return httpInterface({
    url: '/lambs/supplies',
    method: 'PUT',
    data,
  })
}

/**
 * 传入信息添加数据
 * @param {*} data
 * @returns
 */
export function addSuppliesBySupplies(data) {
  return httpInterface({
    url: '/lambs/supplies',
    method: 'POST',
    data,
  })
}
