package com.ruoyi.test.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 二级测试用例对象 level_two_test
 */
public class LevelTwoTest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "二级测试用例名称")
    private String levelTwoTestContent;

    @Excel(name = "二级测试用例业务ID")
    private BigDecimal levelTwoTestId;

    @Excel(name = "关联一级测试用例")
    private Long levelOneTestId;

    @Excel(name = "测试轮次")
    private Integer testRound;

    @Excel(name = "对应预期值")
    private String expectedValue;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLevelTwoTestContent() { return levelTwoTestContent; }
    public void setLevelTwoTestContent(String levelTwoTestContent) { this.levelTwoTestContent = levelTwoTestContent; }

    public BigDecimal getLevelTwoTestId() { return levelTwoTestId; }
    public void setLevelTwoTestId(BigDecimal levelTwoTestId) { this.levelTwoTestId = levelTwoTestId; }

    public Long getLevelOneTestId() { return levelOneTestId; }
    public void setLevelOneTestId(Long levelOneTestId) { this.levelOneTestId = levelOneTestId; }

    public Integer getTestRound() { return testRound; }
    public void setTestRound(Integer testRound) { this.testRound = testRound; }

    public String getExpectedValue() { return expectedValue; }
    public void setExpectedValue(String expectedValue) { this.expectedValue = expectedValue; }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("levelTwoTestContent", getLevelTwoTestContent())
            .append("levelTwoTestId", getLevelTwoTestId())
            .append("levelOneTestId", getLevelOneTestId())
            .append("testRound", getTestRound())
            .append("expectedValue", getExpectedValue())
            .toString();
    }
}
