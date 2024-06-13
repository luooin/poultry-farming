// 登录相关
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 登录方法
 * @param {*} data
 * @returns
 */
export function login(data) {
  return httpInterface({
    url: 'lambs/login',
    method: 'POST',
    data,
  })
}
