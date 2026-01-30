package com.ruoyi.test.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 二级测试用例（第一轮测试）对象 level_two_test_round1
 * 
 * @author ruoyi
 * @date 2026-01-29
 */
public class LevelTwoTestRound1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 二级测试用例名称 */
    @Excel(name = "二级测试用例名称")
    private String levelTwoTestContent;

    /** 二级测试用例业务ID（如 1.1, 2.3） */
    @Excel(name = "二级测试用例业务ID", readConverterExp = "如=,1=.1,,2=.3")
    private BigDecimal levelTwoTestId;

    /** 关联一级测试用例 */
    @Excel(name = "关联一级测试用例")
    private Long levelOneTestId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setLevelTwoTestContent(String levelTwoTestContent) 
    {
        this.levelTwoTestContent = levelTwoTestContent;
    }

    public String getLevelTwoTestContent() 
    {
        return levelTwoTestContent;
    }

    public void setLevelTwoTestId(BigDecimal levelTwoTestId) 
    {
        this.levelTwoTestId = levelTwoTestId;
    }

    public BigDecimal getLevelTwoTestId() 
    {
        return levelTwoTestId;
    }

    public void setLevelOneTestId(Long levelOneTestId) 
    {
        this.levelOneTestId = levelOneTestId;
    }

    public Long getLevelOneTestId() 
    {
        return levelOneTestId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("levelTwoTestContent", getLevelTwoTestContent())
            .append("levelTwoTestId", getLevelTwoTestId())
            .append("levelOneTestId", getLevelOneTestId())
            .toString();
    }
}
