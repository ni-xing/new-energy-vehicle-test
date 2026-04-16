package com.ruoyi.test.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.test.domain.TestData;
import com.ruoyi.test.service.ITestDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 统一测试数据（所有二级测试项实测数据）Controller
 *
 * @author ruoyi
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/test/testData")
public class TestDataController extends BaseController
{
    @Autowired
    private ITestDataService testDataService;

    /**
     * 查询统一测试数据（所有二级测试项实测数据）列表
     */
    @PreAuthorize("@ss.hasPermi('test:testData:list')")
    @GetMapping("/list")
    public TableDataInfo list(TestData testData)
    {
        startPage();
        List<TestData> list = testDataService.selectTestDataList(testData);
        return getDataTable(list);
    }

    /**
     * 导出统一测试数据（所有二级测试项实测数据）列表
     */
    @PreAuthorize("@ss.hasPermi('test:testData:export')")
    @Log(title = "统一测试数据（所有二级测试项实测数据）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TestData testData)
    {
        List<TestData> list = testDataService.selectTestDataList(testData);
        ExcelUtil<TestData> util = new ExcelUtil<TestData>(TestData.class);
        util.exportExcel(response, list, "统一测试数据（所有二级测试项实测数据）数据");
    }

    /**
     * 获取统一测试数据（所有二级测试项实测数据）详细信息
     */
    @PreAuthorize("@ss.hasPermi('test:testData:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(testDataService.selectTestDataById(id));
    }

    /**
     * 新增统一测试数据（所有二级测试项实测数据）
     */
    @PreAuthorize("@ss.hasPermi('test:testData:add')")
    @Log(title = "统一测试数据（所有二级测试项实测数据）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TestData testData)
    {
        return toAjax(testDataService.insertTestData(testData));
    }

    /**
     * 修改统一测试数据（所有二级测试项实测数据）
     */
    @PreAuthorize("@ss.hasPermi('test:testData:edit')")
    @Log(title = "统一测试数据（所有二级测试项实测数据）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TestData testData)
    {
        return toAjax(testDataService.updateTestData(testData));
    }

    /**
     * 删除统一测试数据（所有二级测试项实测数据）
     */
    @PreAuthorize("@ss.hasPermi('test:testData:remove')")
    @Log(title = "统一测试数据（所有二级测试项实测数据）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(testDataService.deleteTestDataByIds(ids));
    }
}
