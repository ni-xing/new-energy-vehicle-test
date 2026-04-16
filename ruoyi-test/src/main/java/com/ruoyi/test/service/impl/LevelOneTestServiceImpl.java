package com.ruoyi.test.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.test.domain.LevelTwoTest;
import com.ruoyi.test.mapper.LevelOneTestMapper;
import com.ruoyi.test.domain.LevelOneTest;
import com.ruoyi.test.service.ILevelOneTestService;

/**
 * 一级测试用例 Service业务层处理
 */
@Service
public class LevelOneTestServiceImpl implements ILevelOneTestService
{
    @Autowired
    private LevelOneTestMapper levelOneTestMapper;

    @Override
    public LevelOneTest selectLevelOneTestById(Long id)
    {
        return levelOneTestMapper.selectLevelOneTestById(id);
    }

    @Override
    public List<LevelOneTest> selectLevelOneTestList(LevelOneTest levelOneTest)
    {
        return levelOneTestMapper.selectLevelOneTestList(levelOneTest);
    }

    @Transactional
    @Override
    public int insertLevelOneTest(LevelOneTest levelOneTest)
    {
        int rows = levelOneTestMapper.insertLevelOneTest(levelOneTest);
        insertLevelTwoTest(levelOneTest);
        return rows;
    }

    @Transactional
    @Override
    public int updateLevelOneTest(LevelOneTest levelOneTest)
    {
        levelOneTestMapper.deleteLevelTwoTestByLevelOneTestId(levelOneTest.getId());
        insertLevelTwoTest(levelOneTest);
        return levelOneTestMapper.updateLevelOneTest(levelOneTest);
    }

    @Transactional
    @Override
    public int deleteLevelOneTestByIds(Long[] ids)
    {
        levelOneTestMapper.deleteLevelTwoTestByLevelOneTestIds(ids);
        return levelOneTestMapper.deleteLevelOneTestByIds(ids);
    }

    @Transactional
    @Override
    public int deleteLevelOneTestById(Long id)
    {
        levelOneTestMapper.deleteLevelTwoTestByLevelOneTestId(id);
        return levelOneTestMapper.deleteLevelOneTestById(id);
    }

    public void insertLevelTwoTest(LevelOneTest levelOneTest)
    {
        List<LevelTwoTest> levelTwoTestList = levelOneTest.getLevelTwoTestList();
        Long id = levelOneTest.getId();
        Integer testRound = levelOneTest.getTestRound();
        if (StringUtils.isNotNull(levelTwoTestList))
        {
            List<LevelTwoTest> list = new ArrayList<LevelTwoTest>();
            for (LevelTwoTest levelTwoTest : levelTwoTestList)
            {
                levelTwoTest.setLevelOneTestId(id);
                levelTwoTest.setTestRound(testRound);
                list.add(levelTwoTest);
            }
            if (list.size() > 0)
            {
                levelOneTestMapper.batchLevelTwoTest(list);
            }
        }
    }
}
