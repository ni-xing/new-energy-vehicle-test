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
import com.ruoyi.test.domain.LevelTwoTest;
import com.ruoyi.test.service.ILevelTwoTestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 二级测试用例 Controller
 */
@RestController
@RequestMapping("/test/levelTwoTest")
public class LevelTwoTestController extends BaseController
{
    @Autowired
    private ILevelTwoTestService levelTwoTestService;

    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:list')")
    @GetMapping("/list")
    public TableDataInfo list(LevelTwoTest levelTwoTest)
    {
        startPage();
        List<LevelTwoTest> list = levelTwoTestService.selectLevelTwoTestList(levelTwoTest);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:export')")
    @Log(title = "二级测试用例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LevelTwoTest levelTwoTest)
    {
        List<LevelTwoTest> list = levelTwoTestService.selectLevelTwoTestList(levelTwoTest);
        ExcelUtil<LevelTwoTest> util = new ExcelUtil<LevelTwoTest>(LevelTwoTest.class);
        util.exportExcel(response, list, "二级测试用例数据");
    }

    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(levelTwoTestService.selectLevelTwoTestById(id));
    }

    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:add')")
    @Log(title = "二级测试用例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LevelTwoTest levelTwoTest)
    {
        return toAjax(levelTwoTestService.insertLevelTwoTest(levelTwoTest));
    }

    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:edit')")
    @Log(title = "二级测试用例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LevelTwoTest levelTwoTest)
    {
        return toAjax(levelTwoTestService.updateLevelTwoTest(levelTwoTest));
    }

    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:remove')")
    @Log(title = "二级测试用例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(levelTwoTestService.deleteLevelTwoTestByIds(ids));
    }

}
