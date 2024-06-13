/* 饲料列表方法 */
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 传入信息获取饲料列表
 * @param {*} params
 * @returns
 */
export function FeedList(params) {
  return httpInterface({
    url: '/lambs/feeds',
    method: 'GET',
    params,
  })
}

/**
 * 传入参数删除数据
 * @param {*} data
 * @returns
 */
export function deleteFeed(data) {
  return httpInterface({
    url: '/lambs/feeds',
    method: 'DELETE',
    data,
  })
}

/**
 * 传入参数新增饲料
 * @param {*} data
 * @returns
 */
export function addFeed(data) {
  return httpInterface({
    url: '/lambs/feeds',
    method: 'POST',
    data,
  })
}

/**
 * 传入信息修改饲料
 * @param {*} data
 * @returns
 */
export function updateFeed(data) {
  return httpInterface({
    url: '/lambs/feeds',
    method: 'PUT',
    data,
  })
}
