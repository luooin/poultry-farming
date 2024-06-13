/* 畜禽列表方法 */
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 传入用户信息获取畜禽列表
 * @param {*} params
 * @returns
 */
export function animalList(params) {
  return httpInterface({
    url: '/lambs/animal',
    method: 'GET',
    params,
  })
}

/**
 * 传入畜禽信息修改畜禽
 * @param {*} data
 * @returns
 */
export function updateAnimalList(data) {
  return httpInterface({
    url: '/lambs/animal',
    method: 'PUT',
    data,
  })
}

/**
 * 传入参数新增畜禽
 * @param {*} data
 * @returns
 */
export function addAnimal(data) {
  return httpInterface({
    url: '/lambs/animal',
    method: 'POST',
    data,
  })
}

/**
 * 传入参数删除数据
 * @param {*} data
 * @returns
 */
export function deleteAnimal(data) {
  return httpInterface({
    url: '/lambs/animal',
    method: 'DELETE',
    data,
  })
}
