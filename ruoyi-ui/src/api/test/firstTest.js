import request from '@/utils/request'

// 查询一级测试用例（第一轮测试）列表
export function listFirstTest(query) {
  return request({
    url: '/test/firstTest/list',
    method: 'get',
    params: query
  })
}

// 查询一级测试用例（第一轮测试）详细
export function getFirstTest(id) {
  return request({
    url: '/test/firstTest/' + id,
    method: 'get'
  })
}

// 新增一级测试用例（第一轮测试）
export function addFirstTest(data) {
  return request({
    url: '/test/firstTest',
    method: 'post',
    data: data
  })
}

// 修改一级测试用例（第一轮测试）
export function updateFirstTest(data) {
  return request({
    url: '/test/firstTest',
    method: 'put',
    data: data
  })
}

// 删除一级测试用例（第一轮测试）
export function delFirstTest(id) {
  return request({
    url: '/test/firstTest/' + id,
    method: 'delete'
  })
}
