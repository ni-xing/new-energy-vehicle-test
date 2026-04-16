package com.ruoyi.test.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 一级测试用例对象 level_one_test
 */
public class LevelOneTest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "一级测试用例名称")
    private String levelOneTestContent;

    @Excel(name = "一级测试用例业务ID")
    private Long levelOneTestId;

    @Excel(name = "测试轮次", readConverterExp = "1=第一次测试,2=第二次测试")
    private Integer testRound;

    private List<LevelTwoTest> levelTwoTestList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setLevelOneTestContent(String levelOneTestContent)
    {
        this.levelOneTestContent = levelOneTestContent;
    }

    public String getLevelOneTestContent()
    {
        return levelOneTestContent;
    }

    public void setLevelOneTestId(Long levelOneTestId)
    {
        this.levelOneTestId = levelOneTestId;
    }

    public Long getLevelOneTestId()
    {
        return levelOneTestId;
    }

    public void setTestRound(Integer testRound)
    {
        this.testRound = testRound;
    }

    public Integer getTestRound()
    {
        return testRound;
    }

    public List<LevelTwoTest> getLevelTwoTestList()
    {
        return levelTwoTestList;
    }

    public void setLevelTwoTestList(List<LevelTwoTest> levelTwoTestList)
    {
        this.levelTwoTestList = levelTwoTestList;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("levelOneTestContent", getLevelOneTestContent())
            .append("levelOneTestId", getLevelOneTestId())
            .append("testRound", getTestRound())
            .append("levelTwoTestList", getLevelTwoTestList())
            .toString();
    }
}
