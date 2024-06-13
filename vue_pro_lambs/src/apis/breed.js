/* 繁育信息方法 */
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 传入用户信息获取繁育列表
 * @param {*} params
 * @returns
 */
export function BreedList(params) {
  return httpInterface({
    url: '/lambs/breed',
    method: 'GET',
    params,
  })
}

/**
 * 传入繁育信息新增繁育列表
 * @param {*} data
 * @returns
 */
export function addBreedByB(data) {
  return httpInterface({
    url: '/lambs/breed',
    method: 'POST',
    data,
  })
}

/**
 * 传入繁育信息修改繁育列表
 * @param {*} data
 * @returns
 */
export function updateBreed(data) {
  return httpInterface({
    url: '/lambs/breed',
    method: 'PUT',
    data,
  })
}

/**
 * 传入繁育信息删除繁育列表
 * @param {*} data
 * @returns
 */
export function deleteBreed(data) {
  return httpInterface({
    url: '/lambs/breed',
    method: 'DELETE',
    data,
  })
}
