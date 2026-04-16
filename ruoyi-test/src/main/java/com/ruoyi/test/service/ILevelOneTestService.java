package com.ruoyi.test.service;

import java.util.List;
import com.ruoyi.test.domain.LevelOneTest;

/**
 * 一级测试用例 Service接口
 */
public interface ILevelOneTestService
{
    LevelOneTest selectLevelOneTestById(Long id);

    List<LevelOneTest> selectLevelOneTestList(LevelOneTest levelOneTest);

    int insertLevelOneTest(LevelOneTest levelOneTest);

    int updateLevelOneTest(LevelOneTest levelOneTest);

    int deleteLevelOneTestByIds(Long[] ids);

    int deleteLevelOneTestById(Long id);
}
