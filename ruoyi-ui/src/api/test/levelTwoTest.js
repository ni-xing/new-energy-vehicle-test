import request from '@/utils/request'

// 查询二级测试用例（第一轮测试）列表
export function listLevelTwoTest(query) {
  return request({
    url: '/test/levelTwoTest/list',
    method: 'get',
    params: query
  })
}

// 查询二级测试用例（第一轮测试）详细
export function getLevelTwoTest(id) {
  return request({
    url: '/test/levelTwoTest/' + id,
    method: 'get'
  })
}

// 新增二级测试用例（第一轮测试）
export function addLevelTwoTest(data) {
  return request({
    url: '/test/levelTwoTest',
    method: 'post',
    data: data
  })
}

// 修改二级测试用例（第一轮测试）
export function updateLevelTwoTest(data) {
  return request({
    url: '/test/levelTwoTest',
    method: 'put',
    data: data
  })
}

// 删除二级测试用例（第一轮测试）
export function delLevelTwoTest(id) {
  return request({
    url: '/test/levelTwoTest/' + id,
    method: 'delete'
  })
}
