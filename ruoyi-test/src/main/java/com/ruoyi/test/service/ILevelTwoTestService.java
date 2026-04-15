package com.ruoyi.test.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.test.domain.LevelTwoTest;

/**
 * 二级测试用例 Service接口
 */
public interface ILevelTwoTestService
{
    LevelTwoTest selectLevelTwoTestById(Long id);

    List<LevelTwoTest> selectLevelTwoTestList(LevelTwoTest levelTwoTest);

    int insertLevelTwoTest(LevelTwoTest levelTwoTest);

    int updateLevelTwoTest(LevelTwoTest levelTwoTest);

    int deleteLevelTwoTestByIds(Long[] ids);

    int deleteLevelTwoTestById(Long id);
}
