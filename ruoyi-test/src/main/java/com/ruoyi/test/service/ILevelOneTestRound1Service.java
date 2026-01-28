package com.ruoyi.test.service;

import java.util.List;
import com.ruoyi.test.domain.LevelOneTestRound1;

/**
 * 一级测试用例（第一轮测试）Service接口
 * 
 * @author ruoyi
 * @date 2026-01-28
 */
public interface ILevelOneTestRound1Service 
{
    /**
     * 查询一级测试用例（第一轮测试）
     * 
     * @param id 一级测试用例（第一轮测试）主键
     * @return 一级测试用例（第一轮测试）
     */
    public LevelOneTestRound1 selectLevelOneTestRound1ById(Long id);

    /**
     * 查询一级测试用例（第一轮测试）列表
     * 
     * @param levelOneTestRound1 一级测试用例（第一轮测试）
     * @return 一级测试用例（第一轮测试）集合
     */
    public List<LevelOneTestRound1> selectLevelOneTestRound1List(LevelOneTestRound1 levelOneTestRound1);

    /**
     * 新增一级测试用例（第一轮测试）
     * 
     * @param levelOneTestRound1 一级测试用例（第一轮测试）
     * @return 结果
     */
    public int insertLevelOneTestRound1(LevelOneTestRound1 levelOneTestRound1);

    /**
     * 修改一级测试用例（第一轮测试）
     * 
     * @param levelOneTestRound1 一级测试用例（第一轮测试）
     * @return 结果
     */
    public int updateLevelOneTestRound1(LevelOneTestRound1 levelOneTestRound1);

    /**
     * 批量删除一级测试用例（第一轮测试）
     * 
     * @param ids 需要删除的一级测试用例（第一轮测试）主键集合
     * @return 结果
     */
    public int deleteLevelOneTestRound1ByIds(Long[] ids);

    /**
     * 删除一级测试用例（第一轮测试）信息
     * 
     * @param id 一级测试用例（第一轮测试）主键
     * @return 结果
     */
    public int deleteLevelOneTestRound1ById(Long id);
}
