import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: 'http://localhost:8080/Electrochemical_Analysis_System_war/operation/list',
    method: 'get',
    params: query
  })
}
