// 用户注册列表
/* 导入axios */
import httpInterface from '../util/https'

/**
 * 获取用户注册列表方法
 * @param {*} data
 * @returns
 */
export function getList() {
  return httpInterface({
    url: '/lambs/register/list',
    method: 'GET',
  })
}
/**
 * 判断该用户是否注册
 * @param {*} params
 * @returns
 */
export function booleanRegister(params) {
  return httpInterface({
    url: '/lambs/register',
    method: 'GET',
    params,
  })
}

/**
 * 同意注册
 * @param {*} data
 * @returns
 */
export function agreeRegister(data) {
  return httpInterface({
    url: '/lambs/register/agree',
    method: 'POST',
    data,
  })
}

/**
 * 拒绝注册
 * @param {*} data
 * @returns
 */
export function regectRegister(data) {
  return httpInterface({
    url: '/lambs/register',
    method: 'PUT',
    data,
  })
}

/**
 * 申请注册
 * @param {*} data
 * @returns
 */
export function applyRegister(data) {
  return httpInterface({
    url: '/lambs/register/apply',
    method: 'POST',
    data,
  })
}
