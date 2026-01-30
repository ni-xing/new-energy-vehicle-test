package com.ruoyi.test.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.test.mapper.LevelTwoTestRound1Mapper;
import com.ruoyi.test.domain.LevelTwoTestRound1;
import com.ruoyi.test.service.ILevelTwoTestRound1Service;

/**
 * 二级测试用例（第一轮测试）Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-30
 */
@Service
public class LevelTwoTestRound1ServiceImpl implements ILevelTwoTestRound1Service
{
    @Autowired
    private LevelTwoTestRound1Mapper levelTwoTestRound1Mapper;

    // 1. 声明日志对象（核心：解决 log 爆红）
    private static final Logger log = LoggerFactory.getLogger(LevelTwoTestRound1ServiceImpl.class);
    /**
     * 查询二级测试用例（第一轮测试）
     *
     * @param id 二级测试用例（第一轮测试）主键
     * @return 二级测试用例（第一轮测试）
     */
    @Override
    public LevelTwoTestRound1 selectLevelTwoTestRound1ById(Long id)
    {
        return levelTwoTestRound1Mapper.selectLevelTwoTestRound1ById(id);
    }

    /**
     * 查询二级测试用例（第一轮测试）列表
     *
     * @param levelTwoTestRound1 二级测试用例（第一轮测试）
     * @return 二级测试用例（第一轮测试）
     */
    @Override
    public List<LevelTwoTestRound1> selectLevelTwoTestRound1List(LevelTwoTestRound1 levelTwoTestRound1)
    {
        return levelTwoTestRound1Mapper.selectLevelTwoTestRound1List(levelTwoTestRound1);
    }

    /**
     * 新增二级测试用例（第一轮测试）
     *
     * @param levelTwoTestRound1 二级测试用例（第一轮测试）
     * @return 结果
     */
    @Override
    public int insertLevelTwoTestRound1(LevelTwoTestRound1 levelTwoTestRound1)
    {
        return levelTwoTestRound1Mapper.insertLevelTwoTestRound1(levelTwoTestRound1);
    }

    /**
     * 修改二级测试用例（第一轮测试）
     *
     * @param levelTwoTestRound1 二级测试用例（第一轮测试）
     * @return 结果
     */
    @Override
    public int updateLevelTwoTestRound1(LevelTwoTestRound1 levelTwoTestRound1)
    {
        return levelTwoTestRound1Mapper.updateLevelTwoTestRound1(levelTwoTestRound1);
    }

    /**
     * 批量删除二级测试用例（第一轮测试）
     *
     * @param ids 需要删除的二级测试用例（第一轮测试）主键
     * @return 结果
     */
    @Override
    public int deleteLevelTwoTestRound1ByIds(Long[] ids)
    {
        return levelTwoTestRound1Mapper.deleteLevelTwoTestRound1ByIds(ids);
    }

    /**
     * 删除二级测试用例（第一轮测试）信息
     *
     * @param id 二级测试用例（第一轮测试）主键
     * @return 结果
     */
    @Override
    public int deleteLevelTwoTestRound1ById(Long id)
    {
        return levelTwoTestRound1Mapper.deleteLevelTwoTestRound1ById(id);
    }

    @Override
    public List<Map<String, Object>> getChildTableData(String childTableName) {
        try {
            List<Map<String, Object>> result = levelTwoTestRound1Mapper.selectChildTableData(childTableName);
            return result;
        } catch (Exception e) {
            log.error("查询子表{}数据失败（二级用例ID：{}）", childTableName, e);
            return new ArrayList<>();
        }
    }
}
