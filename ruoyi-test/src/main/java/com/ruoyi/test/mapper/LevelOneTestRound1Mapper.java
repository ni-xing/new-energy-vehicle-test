package com.ruoyi.test.mapper;

import java.util.List;
import com.ruoyi.test.domain.LevelOneTestRound1;
import com.ruoyi.test.domain.LevelTwoTestRound1;
import org.apache.ibatis.annotations.Mapper;

/**
 * 一级测试用例（第一轮测试）Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-28
 */
@Mapper
public interface LevelOneTestRound1Mapper
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
     * 删除一级测试用例（第一轮测试）
     *
     * @param id 一级测试用例（第一轮测试）主键
     * @return 结果
     */
    public int deleteLevelOneTestRound1ById(Long id);

    /**
     * 批量删除一级测试用例（第一轮测试）
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLevelOneTestRound1ByIds(Long[] ids);

    /**
     * 批量删除二级测试用例（第一轮测试）
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLevelTwoTestRound1ByLevelOneTestIds(Long[] ids);

    /**
     * 批量新增二级测试用例（第一轮测试）
     *
     * @param levelTwoTestRound1List 二级测试用例（第一轮测试）列表
     * @return 结果
     */
    public int batchLevelTwoTestRound1(List<LevelTwoTestRound1> levelTwoTestRound1List);


    /**
     * 通过一级测试用例（第一轮测试）主键删除二级测试用例（第一轮测试）信息
     *
     * @param id 一级测试用例（第一轮测试）ID
     * @return 结果
     */
    public int deleteLevelTwoTestRound1ByLevelOneTestId(Long id);
}
