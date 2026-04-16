package com.ruoyi.test.service;

import java.util.List;
import com.ruoyi.test.domain.TestData;

/**
 * 统一测试数据（所有二级测试项实测数据）Service接口
 *
 * @author ruoyi
 * @date 2026-04-15
 */
public interface ITestDataService
{
    /**
     * 查询统一测试数据（所有二级测试项实测数据）
     *
     * @param id 统一测试数据（所有二级测试项实测数据）主键
     * @return 统一测试数据（所有二级测试项实测数据）
     */
    public TestData selectTestDataById(Long id);

    /**
     * 查询统一测试数据（所有二级测试项实测数据）列表
     *
     * @param testData 统一测试数据（所有二级测试项实测数据）
     * @return 统一测试数据（所有二级测试项实测数据）集合
     */
    public List<TestData> selectTestDataList(TestData testData);


    /**
     * 新增统一测试数据（所有二级测试项实测数据）
     *
     * @param testData 统一测试数据（所有二级测试项实测数据）
     * @return 结果
     */
    public int insertTestData(TestData testData);

    /**
     * 修改统一测试数据（所有二级测试项实测数据）
     *
     * @param testData 统一测试数据（所有二级测试项实测数据）
     * @return 结果
     */
    public int updateTestData(TestData testData);

    /**
     * 批量删除统一测试数据（所有二级测试项实测数据）
     *
     * @param ids 需要删除的统一测试数据（所有二级测试项实测数据）主键集合
     * @return 结果
     */
    public int deleteTestDataByIds(Long[] ids);

    /**
     * 删除统一测试数据（所有二级测试项实测数据）信息
     *
     * @param id 统一测试数据（所有二级测试项实测数据）主键
     * @return 结果
     */
    public int deleteTestDataById(Long id);
}
