/* 药品信息方法 */
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 传入用户信息获取药品列表
 * @param {*} params
 * @returns
 */
export function DrugList(params) {
  return httpInterface({
    url: '/lambs/drug',
    method: 'GET',
    params,
  })
}

/**
 * 传入参数修改药品信息
 * @param {*} data
 * @returns
 */
export function updateDrugMsg(data) {
  return httpInterface({
    url: '/lambs/drug',
    method: 'PUT',
    data,
  })
}
/**
 * 调用方法删除
 * @param {*} data
 * @returns
 */
export function deleteDrugMsg(data) {
  return httpInterface({
    url: '/lambs/drug',
    method: 'DELETE',
    data,
  })
}

/**
 * 传入参数新增药品信息
 * @param {*} data
 * @returns
 */
export function addDrugMSg(data) {
  return httpInterface({
    url: '/lambs/drug',
    method: 'POST',
    data,
  })
}
