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
import com.ruoyi.test.domain.LevelOneTestRound1;
import com.ruoyi.test.service.ILevelOneTestRound1Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 一级测试用例（第一轮测试）Controller
 * 
 * @author ruoyi
 * @date 2026-01-28
 */
@RestController
@RequestMapping("/test/firstTest")
public class LevelOneTestRound1Controller extends BaseController
{
    @Autowired
    private ILevelOneTestRound1Service levelOneTestRound1Service;

    /**
     * 查询一级测试用例（第一轮测试）列表
     */
    @PreAuthorize("@ss.hasPermi('test:firstTest:list')")
    @GetMapping("/list")
    public TableDataInfo list(LevelOneTestRound1 levelOneTestRound1)
    {
        startPage();
        List<LevelOneTestRound1> list = levelOneTestRound1Service.selectLevelOneTestRound1List(levelOneTestRound1);
        return getDataTable(list);
    }

    /**
     * 导出一级测试用例（第一轮测试）列表
     */
    @PreAuthorize("@ss.hasPermi('test:firstTest:export')")
    @Log(title = "一级测试用例（第一轮测试）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LevelOneTestRound1 levelOneTestRound1)
    {
        List<LevelOneTestRound1> list = levelOneTestRound1Service.selectLevelOneTestRound1List(levelOneTestRound1);
        ExcelUtil<LevelOneTestRound1> util = new ExcelUtil<LevelOneTestRound1>(LevelOneTestRound1.class);
        util.exportExcel(response, list, "一级测试用例（第一轮测试）数据");
    }

    /**
     * 获取一级测试用例（第一轮测试）详细信息
     */
    @PreAuthorize("@ss.hasPermi('test:firstTest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(levelOneTestRound1Service.selectLevelOneTestRound1ById(id));
    }

    /**
     * 新增一级测试用例（第一轮测试）
     */
    @PreAuthorize("@ss.hasPermi('test:firstTest:add')")
    @Log(title = "一级测试用例（第一轮测试）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LevelOneTestRound1 levelOneTestRound1)
    {
        return toAjax(levelOneTestRound1Service.insertLevelOneTestRound1(levelOneTestRound1));
    }

    /**
     * 修改一级测试用例（第一轮测试）
     */
    @PreAuthorize("@ss.hasPermi('test:firstTest:edit')")
    @Log(title = "一级测试用例（第一轮测试）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LevelOneTestRound1 levelOneTestRound1)
    {
        return toAjax(levelOneTestRound1Service.updateLevelOneTestRound1(levelOneTestRound1));
    }

    /**
     * 删除一级测试用例（第一轮测试）
     */
    @PreAuthorize("@ss.hasPermi('test:firstTest:remove')")
    @Log(title = "一级测试用例（第一轮测试）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(levelOneTestRound1Service.deleteLevelOneTestRound1ByIds(ids));
    }
}
