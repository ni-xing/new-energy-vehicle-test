package com.ruoyi.test.mapper;

import java.util.List;
import com.ruoyi.test.domain.LevelOneTest;
import com.ruoyi.test.domain.LevelTwoTest;
import org.apache.ibatis.annotations.Mapper;

/**
 * 一级测试用例 Mapper接口
 */
@Mapper
public interface LevelOneTestMapper
{
    LevelOneTest selectLevelOneTestById(Long id);

    List<LevelOneTest> selectLevelOneTestList(LevelOneTest levelOneTest);

    int insertLevelOneTest(LevelOneTest levelOneTest);

    int updateLevelOneTest(LevelOneTest levelOneTest);

    int deleteLevelOneTestById(Long id);

    int deleteLevelOneTestByIds(Long[] ids);

    int deleteLevelTwoTestByLevelOneTestIds(Long[] ids);

    int batchLevelTwoTest(List<LevelTwoTest> levelTwoTestList);

    int deleteLevelTwoTestByLevelOneTestId(Long id);
}
