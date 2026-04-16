import request from '@/utils/request'

// 查询测试数据列表
export function listTestData(query) {
  return request({
    url: '/test/testData/list',
    method: 'get',
    params: query
  })
}

// 查询测试数据详细
export function getTestData(id) {
  return request({
    url: '/test/testData/' + id,
    method: 'get'
  })
}

// 新增测试数据
export function addTestData(data) {
  return request({
    url: '/test/testData',
    method: 'post',
    data: data
  })
}

// 修改测试数据
export function updateTestData(data) {
  return request({
    url: '/test/testData',
    method: 'put',
    data: data
  })
}

// 删除测试数据
export function delTestData(id) {
  return request({
    url: '/test/testData/' + id,
    method: 'delete'
  })
}
