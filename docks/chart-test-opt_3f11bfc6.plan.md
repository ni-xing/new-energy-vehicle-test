---
name: chart-test-opt
overview: 对 `chart` 图表页与 `test` 相关页面/接口做一轮代码审查与可落地优化，重点修复潜在边界问题、减少重复请求并提升可维护性。
todos:
  - id: chart-refactor
    content: "Refactor chart/index.vue: refs, route watch, safe parse, tooltip binding, markLine merge"
    status: in_progress
  - id: error-optimize
    content: "Optimize error.vue: cache unqualifiedList, improve error handling"
    status: pending
  - id: level2-navigation
    content: "Simplify levelTwoTest index.vue: remove duplicate fetch, fix selected id"
    status: pending
  - id: backend-validate
    content: "Harden LevelTwoTestRound1Controller: parse validation, range checks"
    status: pending
  - id: chart-components-small-fix
    content: Small maintainability fixes in dashboard chart components
    status: pending
  - id: manual-test
    content: Manual verification for chart/error/progress endpoints
    status: pending
isProject: false
---

## 变更范围与目标

本次优化聚焦于你在仓库中定位到的 `chart` 与 `test` 相关代码：

- 前端图表页：`ruoyi-ui/src/views/chart/index.vue`
- 前端异常处理页：`ruoyi-ui/src/views/test/error.vue`
- 前端二级测试列表/跳转逻辑：`ruoyi-ui/src/views/test/firstTest/levelTwoTest/index.vue`
- 后端接口：`ruoyi-test/src/main/java/com/ruoyi/test/controller/LevelTwoTestRound1Controller.java`
- 前端 ECharts 组件（辅助复用与小修）：`ruoyi-ui/src/views/dashboard/*Chart.vue`

目标优先级（来自你选择）：

- 修复潜在 bug / 边界问题
- 性能优化（减少重复渲染与重复请求）
- 可维护性（抽公共逻辑、减少命名冲突、让代码更清晰）

## 核心修改规格

### 1) `ruoyi-ui/src/views/chart/index.vue`

需要解决的问题与实现要点：

- 组件在路由参数/查询变化时可能未重新拉取数据并重建图表：
  - 以 `$route` 变化为触发条件，增加 `watch`（或使用 `beforeRouteUpdate`）并保证每次切换前 `dispose` 旧 chart。
- ECharts 初始化不安全且不可复用：
  - 使用 `ref` 替代固定 `id="chart"`，并对同一 DOM 使用 `echarts.getInstanceByDom` 防止重复 init。
- 数据校验不够鲁棒：
  - 对 `measured_value` 做统一安全解析（非有限数字时按“不可展示”处理，避免图表出现 NaN/字符串混入导致 tooltip/折线异常）。
  - 预期值 `expectedValue` 解析移除 `console.log`，并可在后续迭代扩展范围格式兼容性。
- tooltip 与数据源的绑定存在脆弱点：
  - 由“索引回查 sortedData”改为“在 series data 中携带 record 信息（id/qualified/date等）”，formatter 直接读取该信息。
- markLine 的 setOption 合并行为不够确定：
  - 将 `markLine` 合并进首次 `setOption`（避免二次 setOption 造成合并/覆盖的不确定性）。
- 去除无用字段/计算属性与命名冲突：
  - 将 import 的 `getChildTableData` 重命名为 `fetchChildTableData`，并移除未使用的 `isCharValue` 与无用 computed。
- 图表自适应：
  - 引入已有 `dashboard/mixins/resize.js` 以统一 resize 行为（要求该组件拥有 `chart` 实例变量）。

### 2) `ruoyi-ui/src/views/test/error.vue`

需要解决的问题与实现要点：

- `unqualifiedList` 当前为 computed 且包含 `.sort()`：
  - 以 `loadData` 成功回调为触发点，在拿到 `rawData` 后只排序一次并写入 data，减少每次渲染的重复排序开销。
- 错误处理体验：
  - `onProgressChange` 的 catch 块当前吞异常；增加兜底提示（例如 `this.$message.error('保存失败')`）。
- 进度与时间类型一致性：
  - 确保 `gmt_modified` 在更新后与 `formatGmtModified` 的预期类型一致（保持为可被 parseTime 处理的格式）。

### 3) `ruoyi-ui/src/views/test/firstTest/levelTwoTest/index.vue`

需要解决的问题与实现要点：

- 多处重复请求：
  - `handleChart` 与 `handleError` 目前会先调用 `getChildTableData` 拉取一次做字符/空数据判断，然后再跳转。
  - 目标是移除这一步重复请求：仅在 `childTableName` 存在时直接导航，让目标页（chart/error）完成拉取与校验。
- selection id 类型混用：
  - `handleUpdate` 当前 `row.id || this.ids` 会导致 `this.ids` 为数组时参与拼接；改为单选时显式使用 `this.ids[0]`。

### 4) `LevelTwoTestRound1Controller.java`

需要解决的问题与实现要点：

- 参数解析的健壮性：
  - `getChildTableData` 对 `pageNum/pageSize` 的 `Integer.parseInt` 增加 try-catch，非法值返回明确错误或使用默认值。
  - `updateChildTableProgress` 对 body 中 `id/processProgress` 增加类型/范围校验：
    - `processProgress` 限制在 0~3
    - 非法值返回 `error("参数...不能为空/格式不正确")`，避免抛出 500
- 维持现有权限声明以避免权限系统联动风险：
  - 如权限字符串是否需要调整需联动后端/权限配置确认，因此本次只做参数校验与安全兜底。

### 5) `ruoyi-ui/src/views/dashboard/*Chart.vue`

需要解决的问题与实现要点（小范围、低风险）：

- 仅做可维护性小修：
  - 修正明显拼写错误（如 BarChart stack 名称）但不影响图表结构。
  - 保持现有 props/数据模式不大改，避免引入不确定 UI 变化。

## 实施策略

- 所有前端修改保持与现有 API 契约兼容：
  - `GET /test/levelTwoTest/childtable/{childTableName}`
  - `PUT /test/levelTwoTest/childtable/{childTableName}/progress`（body: `id`, `processProgress`）
- 对 chart 页：
  - 若检测到 `measured_value` 存在不可解析值，明确提示“暂不支持展示该数据”，并保持页面不崩溃。
- 对 error 页：
  - progress 更新成功后同步刷新对应行的 `process_progress` 与 `gmt_modified`。

## Implementation Checklist

1. 更新 `ruoyi-ui/src/views/chart/index.vue`：将静态 DOM `id` 改为 `ref`，并使用 `echarts.getInstanceByDom`/`echarts.init` 安全初始化。
2. 更新 `ruoyi-ui/src/views/chart/index.vue`：引入 `dashboard/mixins/resize.js`，确保 `chart` 实例可触发 `resize()`。
3. 更新 `ruoyi-ui/src/views/chart/index.vue`：新增 `$route` 监听（或 `beforeRouteUpdate`），路由/查询变化时调用 `dispose` + 重新拉取数据并重建图表。
4. 更新 `ruoyi-ui/src/views/chart/index.vue`：重命名导入的 `getChildTableData` 为 `fetchChildTableData`，消除命名冲突；删除未使用字段/计算属性与 `console.log`。
5. 更新 `ruoyi-ui/src/views/chart/index.vue`：对 `measured_value` 做统一安全解析；若存在非有限数字则给出提示并阻止图表渲染。
6. 更新 `ruoyi-ui/src/views/chart/index.vue`：构造 series data 时携带 `id/qualified/date` 等记录信息，tooltip formatter 直接读取该信息（避免用数组索引回查 sortedData）。
7. 更新 `ruoyi-ui/src/views/chart/index.vue`：将 markLine 逻辑合并进首次 `setOption`（避免二次 setOption 的合并不确定性）。
8. 更新 `ruoyi-ui/src/views/test/error.vue`：将 `unqualifiedList` 从 computed 改为在 `loadData()` 成功后只排序一次写入 data。
9. 更新 `ruoyi-ui/src/views/test/error.vue`：为 `onProgressChange` 的 catch 增加兜底错误提示，并确认 `gmt_modified` 更新后的类型可被 `formatGmtModified` 正确解析。
10. 更新 `ruoyi-ui/src/views/test/firstTest/levelTwoTest/index.vue`：修复 `handleUpdate` 中单选 id 的取值，显式使用 `this.ids[0]`。
11. 更新 `ruoyi-ui/src/views/test/firstTest/levelTwoTest/index.vue`：移除 `handleChart`、`handleError` 中重复的 `getChildTableData` 拉取与前置校验，改为直接导航（由目标页完成校验与加载）。
12. 更新 `ruoyi-test/src/main/java/com/ruoyi/test/controller/LevelTwoTestRound1Controller.java`：为 `pageNum/pageSize` parse 增加 try-catch 或默认值策略。
13. 更新 `ruoyi-test/src/main/java/com/ruoyi/test/controller/LevelTwoTestRound1Controller.java`：为 `updateChildTableProgress` 增加 body 类型/范围校验（`processProgress` 限制 0~3），非法输入返回友好 error。
14. 轻量更新 `ruoyi-ui/src/views/dashboard/*Chart.vue`：修正明显拼写错误/小范围可维护性问题（不改变图表数据与布局的外观逻辑）。
15. 执行手工验证：
  - chart 页：切换不同 childTableName/expectedValue 时是否能正确刷新并保持 tooltip/markLine 正常。
    - error 页：修改进度后表格行样式与数据是否正确更新。
    - 后端：传入非法 `processProgress`/分页参数时是否返回预期错误而非 500。

