package com.ruoyi.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.test.mapper.LevelTwoTestMapper;
import com.ruoyi.test.domain.LevelTwoTest;
import com.ruoyi.test.service.ILevelTwoTestService;

/**
 * 二级测试用例 Service业务层处理
 */
@Service
public class LevelTwoTestServiceImpl implements ILevelTwoTestService
{
    @Autowired
    private LevelTwoTestMapper levelTwoTestMapper;

    @Override
    public LevelTwoTest selectLevelTwoTestById(Long id)
    {
        return levelTwoTestMapper.selectLevelTwoTestById(id);
    }

    @Override
    public List<LevelTwoTest> selectLevelTwoTestList(LevelTwoTest levelTwoTest)
    {
        return levelTwoTestMapper.selectLevelTwoTestList(levelTwoTest);
    }

    @Override
    public int insertLevelTwoTest(LevelTwoTest levelTwoTest)
    {
        return levelTwoTestMapper.insertLevelTwoTest(levelTwoTest);
    }

    @Override
    public int updateLevelTwoTest(LevelTwoTest levelTwoTest)
    {
        return levelTwoTestMapper.updateLevelTwoTest(levelTwoTest);
    }

    @Override
    public int deleteLevelTwoTestByIds(Long[] ids)
    {
        return levelTwoTestMapper.deleteLevelTwoTestByIds(ids);
    }

    @Override
    public int deleteLevelTwoTestById(Long id)
    {
        return levelTwoTestMapper.deleteLevelTwoTestById(id);
    }

}
