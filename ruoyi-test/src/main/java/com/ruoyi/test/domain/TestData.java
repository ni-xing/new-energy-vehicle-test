package com.ruoyi.test.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 统一测试数据（所有二级测试项实测数据）对象 test_data
 * 
 * @author ruoyi
 * @date 2026-04-15
 */
public class TestData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 二级测试项名称（直接关联） */
    @Excel(name = "二级测试项名称", readConverterExp = "直=接关联")
    private String levelTwoTestContent;

    /** 测量值 */
    @Excel(name = "测量值")
    private String measureValue;

    /** 0不合格 1合格 */
    @Excel(name = "0不合格 1合格")
    private Long isQualified;

    /** 0未处理 1处理中 2已完成 3无法解决 */
    @Excel(name = "0未处理 1处理中 2已完成 3无法解决")
    private Long processStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreated;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

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

    public void setMeasureValue(String measureValue) 
    {
        this.measureValue = measureValue;
    }

    public String getMeasureValue() 
    {
        return measureValue;
    }

    public void setIsQualified(Long isQualified) 
    {
        this.isQualified = isQualified;
    }

    public Long getIsQualified() 
    {
        return isQualified;
    }

    public void setProcessStatus(Long processStatus) 
    {
        this.processStatus = processStatus;
    }

    public Long getProcessStatus() 
    {
        return processStatus;
    }

    public void setGmtCreated(Date gmtCreated) 
    {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtCreated() 
    {
        return gmtCreated;
    }

    public void setGmtModified(Date gmtModified) 
    {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified() 
    {
        return gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("levelTwoTestContent", getLevelTwoTestContent())
            .append("measureValue", getMeasureValue())
            .append("isQualified", getIsQualified())
            .append("processStatus", getProcessStatus())
            .append("gmtCreated", getGmtCreated())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
