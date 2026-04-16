package com.ruoyi.test.mapper;

import java.util.List;

import com.ruoyi.test.domain.LevelTwoTest;
import org.apache.ibatis.annotations.Mapper;

/**
 * 二级测试用例 Mapper接口
 */
@Mapper
public interface LevelTwoTestMapper
{
    LevelTwoTest selectLevelTwoTestById(Long id);

    List<LevelTwoTest> selectLevelTwoTestList(LevelTwoTest levelTwoTest);

    int insertLevelTwoTest(LevelTwoTest levelTwoTest);

    int updateLevelTwoTest(LevelTwoTest levelTwoTest);

    int deleteLevelTwoTestById(Long id);

    int deleteLevelTwoTestByIds(Long[] ids);
}
