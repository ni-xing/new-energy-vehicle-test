package com.ruoyi.test.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.test.domain.LevelTwoTestRound1;

/**
 * 二级测试用例（第一轮测试）Service接口
 *
 * @author ruoyi
 * @date 2026-01-30
 */
public interface ILevelTwoTestRound1Service
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
     * 批量删除二级测试用例（第一轮测试）
     *
     * @param ids 需要删除的二级测试用例（第一轮测试）主键集合
     * @return 结果
     */
    public int deleteLevelTwoTestRound1ByIds(Long[] ids);

    /**
     * 删除二级测试用例（第一轮测试）信息
     *
     * @param id 二级测试用例（第一轮测试）主键
     * @return 结果
     */
    public int deleteLevelTwoTestRound1ById(Long id);

    /**
     * 查询子表数据（含白名单校验、分页支持）
     * @param childTableName 子表名
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 子表数据列表
     */
    List<Map<String, Object>> getChildTableData(String childTableName, int pageNum, int pageSize);

    /**
     * 查询子表数据总数
     * @param childTableName 子表名
     * @return 总数
     */
    int getChildTableDataCount(String childTableName);

    /**
     * 更新子表行处理进度（表名白名单校验）
     *
     * @param childTableName 子物理表名
     * @param rowId          子表主键 id
     * @param processProgress 0-3
     * @return 影响行数
     */
    int updateChildTableRowProgress(String childTableName, Long rowId, int processProgress);
}
