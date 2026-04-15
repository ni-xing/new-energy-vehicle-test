import request from '@/utils/request'

// 查询一级测试用例列表
export function listLevelOneTest(query) {
  return request({
    url: '/test/levelOneTest/list',
    method: 'get',
    params: query
  })
}

// 查询一级测试用例详细
export function getLevelOneTest(id) {
  return request({
    url: '/test/levelOneTest/' + id,
    method: 'get'
  })
}

// 新增一级测试用例
export function addLevelOneTest(data) {
  return request({
    url: '/test/levelOneTest',
    method: 'post',
    data: data
  })
}

// 修改一级测试用例
export function updateLevelOneTest(data) {
  return request({
    url: '/test/levelOneTest',
    method: 'put',
    data: data
  })
}

// 删除一级测试用例
export function delLevelOneTest(id) {
  return request({
    url: '/test/levelOneTest/' + id,
    method: 'delete'
  })
}
