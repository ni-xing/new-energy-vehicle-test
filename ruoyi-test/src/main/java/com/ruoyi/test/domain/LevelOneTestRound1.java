package com.ruoyi.test.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 一级测试用例（第一轮测试）对象 level_one_test_round1
 * 
 * @author ruoyi
 * @date 2026-01-28
 */
public class LevelOneTestRound1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 一级测试用例名称 */
    @Excel(name = "一级测试用例名称")
    private String levelOneTestContent;

    /** 一级测试用例业务ID */
    @Excel(name = "一级测试用例业务ID")
    private Long levelOneTestId;

    /** 二级测试用例（第一轮测试）信息 */
    private List<LevelTwoTestRound1> levelTwoTestRound1List;

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

    public List<LevelTwoTestRound1> getLevelTwoTestRound1List()
    {
        return levelTwoTestRound1List;
    }

    public void setLevelTwoTestRound1List(List<LevelTwoTestRound1> levelTwoTestRound1List)
    {
        this.levelTwoTestRound1List = levelTwoTestRound1List;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("levelOneTestContent", getLevelOneTestContent())
            .append("levelOneTestId", getLevelOneTestId())
            .append("levelTwoTestRound1List", getLevelTwoTestRound1List())
            .toString();
    }
}
