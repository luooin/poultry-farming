//用户信息相关
/* 导入axios */
import httpInterface from '../util/https'
/**
 * 获取用户的所有信息
 * @returns
 */
export function UserList() {
  return httpInterface({
    url: '/lambs/user',
    method: 'GET',

  })
}

/**
 * 传入参数修改用户状态
 * @param {*} data 
 * @returns 
 */
export function alterUserStatus(data){
  return httpInterface({
    url:'/lambs/user',
    method:'PUT',
    data
  })
}

/**
 * 传入参数修改用户login和users
 * @param {*} data 
 * @returns 
 */
export function alterUserMsg(data){
  return httpInterface({
    url:'/lambs/user/upusemsg',
    method:'PUT',
    data
  })
}
