package com.ruoyi.test.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.test.domain.LevelTwoTestRound1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 二级测试用例（第一轮测试）Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-30
 */
@Mapper
public interface LevelTwoTestRound1Mapper
{
    /**
     * 查询二级测试用例（第一轮测试）
     *
     * @param id 二级测试用例（第一轮测试）主键
     * @return 二级测试用例（第一轮测试）
     */
    public LevelTwoTestRound1 selectLevelTwoTestRound1ById(Long id);

    /**
     * 查询二级测试用例（第一轮测试）列表
     *
     * @param levelTwoTestRound1 二级测试用例（第一轮测试）
     * @return 二级测试用例（第一轮测试）集合
     */
    public List<LevelTwoTestRound1> selectLevelTwoTestRound1List(LevelTwoTestRound1 levelTwoTestRound1);

    /**
     * 新增二级测试用例（第一轮测试）
     *
     * @param levelTwoTestRound1 二级测试用例（第一轮测试）
     * @return 结果
     */
    public int insertLevelTwoTestRound1(LevelTwoTestRound1 levelTwoTestRound1);

    /**
     * 修改二级测试用例（第一轮测试）
     *
     * @param levelTwoTestRound1 二级测试用例（第一轮测试）
     * @return 结果
     */
    public int updateLevelTwoTestRound1(LevelTwoTestRound1 levelTwoTestRound1);

    /**
     * 删除二级测试用例（第一轮测试）
     *
     * @param id 二级测试用例（第一轮测试）主键
     * @return 结果
     */
    public int deleteLevelTwoTestRound1ById(Long id);

    /**
     * 批量删除二级测试用例（第一轮测试）
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLevelTwoTestRound1ByIds(Long[] ids);

    /**
     * 动态表名查询子表数据（支持二级用例ID筛选）
     * @param childTableName 子表名（需白名单校验）
     * @return 子表数据列表
     */
    List<Map<String, Object>> selectChildTableData(@Param("childTableName") String childTableName);
}
