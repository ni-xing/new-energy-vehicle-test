package com.ruoyi.test.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.test.mapper.TestDataMapper;
import com.ruoyi.test.domain.TestData;
import com.ruoyi.test.service.ITestDataService;

/**
 * 统一测试数据（所有二级测试项实测数据）Service业务层处理
 *
 * @author ruoyi
 * @date 2026-04-15
 */
@Service
public class TestDataServiceImpl implements ITestDataService
{
    @Autowired
    private TestDataMapper testDataMapper;

    /**
     * 查询统一测试数据（所有二级测试项实测数据）
     *
     * @param id 统一测试数据（所有二级测试项实测数据）主键
     * @return 统一测试数据（所有二级测试项实测数据）
     */
    @Override
    public TestData selectTestDataById(Long id)
    {
        return testDataMapper.selectTestDataById(id);
    }

    /**
     * 查询统一测试数据（所有二级测试项实测数据）列表
     *
     * @param testData 统一测试数据（所有二级测试项实测数据）
     * @return 统一测试数据（所有二级测试项实测数据）
     */
    @Override
    public List<TestData> selectTestDataList(TestData testData)
    {
        return testDataMapper.selectTestDataList(testData);
    }

    /**
     * 新增统一测试数据（所有二级测试项实测数据）
     *
     * @param testData 统一测试数据（所有二级测试项实测数据）
     * @return 结果
     */
    @Override
    public int insertTestData(TestData testData)
    {
        return testDataMapper.insertTestData(testData);
    }

    /**
     * 修改统一测试数据（所有二级测试项实测数据）
     *
     * @param testData 统一测试数据（所有二级测试项实测数据）
     * @return 结果
     */
    @Override
    public int updateTestData(TestData testData)
    {
        return testDataMapper.updateTestData(testData);
    }

    /**
     * 批量删除统一测试数据（所有二级测试项实测数据）
     *
     * @param ids 需要删除的统一测试数据（所有二级测试项实测数据）主键
     * @return 结果
     */
    @Override
    public int deleteTestDataByIds(Long[] ids)
    {
        return testDataMapper.deleteTestDataByIds(ids);
    }

    /**
     * 删除统一测试数据（所有二级测试项实测数据）信息
     *
     * @param id 统一测试数据（所有二级测试项实测数据）主键
     * @return 结果
     */
    @Override
    public int deleteTestDataById(Long id)
    {
        return testDataMapper.deleteTestDataById(id);
    }
}
