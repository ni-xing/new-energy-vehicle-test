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
import com.ruoyi.test.domain.LevelOneTest;
import com.ruoyi.test.service.ILevelOneTestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 一级测试用例 Controller
 */
@RestController
@RequestMapping("/test/levelOneTest")
public class LevelOneTestController extends BaseController
{
    @Autowired
    private ILevelOneTestService levelOneTestService;

    @PreAuthorize("@ss.hasPermi('test:levelOneTest:list')")
    @GetMapping("/list")
    public TableDataInfo list(LevelOneTest levelOneTest)
    {
        startPage();
        List<LevelOneTest> list = levelOneTestService.selectLevelOneTestList(levelOneTest);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('test:levelOneTest:export')")
    @Log(title = "一级测试用例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LevelOneTest levelOneTest)
    {
        List<LevelOneTest> list = levelOneTestService.selectLevelOneTestList(levelOneTest);
        ExcelUtil<LevelOneTest> util = new ExcelUtil<LevelOneTest>(LevelOneTest.class);
        util.exportExcel(response, list, "一级测试用例数据");
    }

    @PreAuthorize("@ss.hasPermi('test:levelOneTest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(levelOneTestService.selectLevelOneTestById(id));
    }

    @PreAuthorize("@ss.hasPermi('test:levelOneTest:add')")
    @Log(title = "一级测试用例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LevelOneTest levelOneTest)
    {
        return toAjax(levelOneTestService.insertLevelOneTest(levelOneTest));
    }

    @PreAuthorize("@ss.hasPermi('test:levelOneTest:edit')")
    @Log(title = "一级测试用例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LevelOneTest levelOneTest)
    {
        return toAjax(levelOneTestService.updateLevelOneTest(levelOneTest));
    }

    @PreAuthorize("@ss.hasPermi('test:levelOneTest:remove')")
    @Log(title = "一级测试用例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(levelOneTestService.deleteLevelOneTestByIds(ids));
    }
}
