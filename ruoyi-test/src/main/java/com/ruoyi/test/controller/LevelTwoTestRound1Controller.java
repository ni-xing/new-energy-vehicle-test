package com.ruoyi.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.ruoyi.test.domain.LevelTwoTestRound1;
import com.ruoyi.test.service.ILevelTwoTestRound1Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 二级测试用例（第一轮测试）Controller
 *
 * @author ruoyi
 * @date 2026-01-30
 */
@RestController
@RequestMapping("/test/levelTwoTest")
public class LevelTwoTestRound1Controller extends BaseController
{
    @Autowired
    private ILevelTwoTestRound1Service levelTwoTestRound1Service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询二级测试用例（第一轮测试）列表
     */
    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:list')")
    @GetMapping("/list")
    public TableDataInfo list(LevelTwoTestRound1 levelTwoTestRound1)
    {
        startPage();
        List<LevelTwoTestRound1> list = levelTwoTestRound1Service.selectLevelTwoTestRound1List(levelTwoTestRound1);
        return getDataTable(list);
    }

    /**
     * 导出二级测试用例（第一轮测试）列表
     */
    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:export')")
    @Log(title = "二级测试用例（第一轮测试）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LevelTwoTestRound1 levelTwoTestRound1)
    {
        List<LevelTwoTestRound1> list = levelTwoTestRound1Service.selectLevelTwoTestRound1List(levelTwoTestRound1);
        ExcelUtil<LevelTwoTestRound1> util = new ExcelUtil<LevelTwoTestRound1>(LevelTwoTestRound1.class);
        util.exportExcel(response, list, "二级测试用例（第一轮测试）数据");
    }

    /**
     * 获取二级测试用例（第一轮测试）详细信息
     */
    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(levelTwoTestRound1Service.selectLevelTwoTestRound1ById(id));
    }

    /**
     * 新增二级测试用例（第一轮测试）
     */
    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:add')")
    @Log(title = "二级测试用例（第一轮测试）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LevelTwoTestRound1 levelTwoTestRound1)
    {
        return toAjax(levelTwoTestRound1Service.insertLevelTwoTestRound1(levelTwoTestRound1));
    }

    /**
     * 修改二级测试用例（第一轮测试）
     */
    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:edit')")
    @Log(title = "二级测试用例（第一轮测试）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LevelTwoTestRound1 levelTwoTestRound1)
    {
        return toAjax(levelTwoTestRound1Service.updateLevelTwoTestRound1(levelTwoTestRound1));
    }

    /**
     * 删除二级测试用例（第一轮测试）
     */
    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:remove')")
    @Log(title = "二级测试用例（第一轮测试）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(levelTwoTestRound1Service.deleteLevelTwoTestRound1ByIds(ids));
    }

    /**
     * 根据子表名查询子表数据
     */
    @PreAuthorize("@ss.hasPermi('test:levelTwoTest:list')")
    @GetMapping(value = "/childtable/{childTableName}")
    public TableDataInfo getChildTableData(@PathVariable("childTableName") String childTableName)
    {
        List<Map<String, Object>> result = levelTwoTestRound1Service.getChildTableData(childTableName);
        return getDataTable( result);
    }

    /**
     * 验证表名是否合法
     */
    private boolean isValidTableName(String tableName) {
        // 简单验证表名是否符合规范（只包含字母、数字、下划线，且以字母开头）
        return tableName != null && tableName.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
    }
}
