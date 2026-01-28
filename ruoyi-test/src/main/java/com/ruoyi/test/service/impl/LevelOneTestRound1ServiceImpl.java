package com.ruoyi.test.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.test.domain.LevelTwoTestRound1;
import com.ruoyi.test.mapper.LevelOneTestRound1Mapper;
import com.ruoyi.test.domain.LevelOneTestRound1;
import com.ruoyi.test.service.ILevelOneTestRound1Service;

/**
 * 一级测试用例（第一轮测试）Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-28
 */
@Service
public class LevelOneTestRound1ServiceImpl implements ILevelOneTestRound1Service 
{
    @Autowired
    private LevelOneTestRound1Mapper levelOneTestRound1Mapper;

    /**
     * 查询一级测试用例（第一轮测试）
     * 
     * @param id 一级测试用例（第一轮测试）主键
     * @return 一级测试用例（第一轮测试）
     */
    @Override
    public LevelOneTestRound1 selectLevelOneTestRound1ById(Long id)
    {
        return levelOneTestRound1Mapper.selectLevelOneTestRound1ById(id);
    }

    /**
     * 查询一级测试用例（第一轮测试）列表
     * 
     * @param levelOneTestRound1 一级测试用例（第一轮测试）
     * @return 一级测试用例（第一轮测试）
     */
    @Override
    public List<LevelOneTestRound1> selectLevelOneTestRound1List(LevelOneTestRound1 levelOneTestRound1)
    {
        return levelOneTestRound1Mapper.selectLevelOneTestRound1List(levelOneTestRound1);
    }

    /**
     * 新增一级测试用例（第一轮测试）
     * 
     * @param levelOneTestRound1 一级测试用例（第一轮测试）
     * @return 结果
     */
    @Transactional
    @Override
    public int insertLevelOneTestRound1(LevelOneTestRound1 levelOneTestRound1)
    {
        int rows = levelOneTestRound1Mapper.insertLevelOneTestRound1(levelOneTestRound1);
        insertLevelTwoTestRound1(levelOneTestRound1);
        return rows;
    }

    /**
     * 修改一级测试用例（第一轮测试）
     * 
     * @param levelOneTestRound1 一级测试用例（第一轮测试）
     * @return 结果
     */
    @Transactional
    @Override
    public int updateLevelOneTestRound1(LevelOneTestRound1 levelOneTestRound1)
    {
        levelOneTestRound1Mapper.deleteLevelTwoTestRound1ByLevelOneTestId(levelOneTestRound1.getId());
        insertLevelTwoTestRound1(levelOneTestRound1);
        return levelOneTestRound1Mapper.updateLevelOneTestRound1(levelOneTestRound1);
    }

    /**
     * 批量删除一级测试用例（第一轮测试）
     * 
     * @param ids 需要删除的一级测试用例（第一轮测试）主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteLevelOneTestRound1ByIds(Long[] ids)
    {
        levelOneTestRound1Mapper.deleteLevelTwoTestRound1ByLevelOneTestIds(ids);
        return levelOneTestRound1Mapper.deleteLevelOneTestRound1ByIds(ids);
    }

    /**
     * 删除一级测试用例（第一轮测试）信息
     * 
     * @param id 一级测试用例（第一轮测试）主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteLevelOneTestRound1ById(Long id)
    {
        levelOneTestRound1Mapper.deleteLevelTwoTestRound1ByLevelOneTestId(id);
        return levelOneTestRound1Mapper.deleteLevelOneTestRound1ById(id);
    }

    /**
     * 新增二级测试用例（第一轮测试）信息
     * 
     * @param levelOneTestRound1 一级测试用例（第一轮测试）对象
     */
    public void insertLevelTwoTestRound1(LevelOneTestRound1 levelOneTestRound1)
    {
        List<LevelTwoTestRound1> levelTwoTestRound1List = levelOneTestRound1.getLevelTwoTestRound1List();
        Long id = levelOneTestRound1.getId();
        if (StringUtils.isNotNull(levelTwoTestRound1List))
        {
            List<LevelTwoTestRound1> list = new ArrayList<LevelTwoTestRound1>();
            for (LevelTwoTestRound1 levelTwoTestRound1 : levelTwoTestRound1List)
            {
                levelTwoTestRound1.setLevelOneTestId(id);
                list.add(levelTwoTestRound1);
            }
            if (list.size() > 0)
            {
                levelOneTestRound1Mapper.batchLevelTwoTestRound1(list);
            }
        }
    }
}
