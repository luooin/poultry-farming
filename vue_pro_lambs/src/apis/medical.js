/* 医疗器械方法 */
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 获取所有医疗器械
 * @param {*} params
 * @returns
 */
export function MedicalList(params) {
  return httpInterface({
    url: '/lambs/medical',
    method: 'GET',
    params,
  })
}

/**
 * 传入器械信息新增器械
 * @param {*} data
 * @returns
 */
export function addMedical(data) {
  return httpInterface({
    url: '/lambs/medical',
    method: 'POST',
    data,
  })
}

/**
 * 传入参数修改器械
 * @param {*} data
 * @returns
 */
export function updateMedical(data) {
  return httpInterface({
    url: '/lambs/medical',
    method: 'PUT',
    data,
  })
}
