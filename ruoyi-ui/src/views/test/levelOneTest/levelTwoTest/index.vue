<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="150px">
      <el-form-item label="二级测试用例业务ID" prop="levelTwoTestId">
        <el-input
          v-model="queryParams.levelTwoTestId"
          placeholder="请输入二级测试用例业务ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['test:levelTwoTest:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['test:levelTwoTest:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['test:levelTwoTest:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['test:levelTwoTest:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="levelTwoTestList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键" align="center" prop="id" />
      <el-table-column label="二级测试用例名称" align="center" prop="levelTwoTestContent" />
      <el-table-column label="二级测试用例业务ID" align="center" prop="levelTwoTestId" />
      <el-table-column label="关联一级测试用例" align="center" prop="levelOneTestId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['test:levelTwoTest:list']"
          >查看</el-button>

          <el-button
            size="mini"
            type="text"
            plain
            icon="el-icon-data-analysis"
            @click="handleChart(scope.row)"
          >图表</el-button>

          <el-button
            size="mini"
            type="text"
            plain
            icon="el-icon-warning"
            @click="handleError(scope.row)"
          >异常处理</el-button>

<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['test:levelTwoTest:remove']"-->
<!--          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改二级测试用例（第一轮测试）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="二级测试用例名称">
          <editor v-model="form.levelTwoTestContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="二级测试用例业务ID" prop="levelTwoTestId">
          <el-input v-model="form.levelTwoTestId" placeholder="请输入二级测试用例业务ID" />
        </el-form-item>
        <el-form-item label="关联一级测试用例" prop="levelOneTestId">
          <el-input v-model="form.levelOneTestId" placeholder="请输入关联一级测试用例" />
        </el-form-item>
        <el-form-item label="值类型" prop="valueType">
          <el-select v-model="form.valueType" placeholder="请选择值类型">
            <el-option label="整数" value="int" />
            <el-option label="字符串" value="string" />
            <el-option label="浮点数" value="float" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看二级测试用例详情对话框 -->
    <el-dialog :title="viewTitle" :visible.sync="viewOpen" width="800px" append-to-body @close="cancelView">
      <el-form ref="viewForm" :model="form" label-width="80px">
        <el-table v-loading="childLoading"
                  :data="childTableData"
                  border
                  :row-class-name="tableRowClassName">
          <el-table-column label="自增主键" align="center" prop="id" />
          <el-table-column label="测试值" align="center" prop="measured_value"/>
          <el-table-column label="是否合格" align="center" prop="isQualified">
            <template slot-scope="scope">
              {{ scope.row.isQualified === 1 ? '合格' : '不合格' }}
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="childTotal>0"
          :total="childTotal"
          :page.sync="childPageNum"
          :limit.sync="childPageSize"
          @pagination="handleChildPagination"
          style="margin-top: 10px;"
        />
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelView">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 图表禁用提示对话框 -->
    <el-dialog title="提示" :visible.sync="chartDialogVisible" width="400px" append-to-body>
      <div style="text-align: center;">
        <i class="el-icon-warning" style="color: #E6A23C; font-size: 24px; margin-right: 10px;"></i>
        <span>{{ chartDialogMessage }}</span>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="chartDialogVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLevelTwoTest, getLevelTwoTest, delLevelTwoTest, addLevelTwoTest, updateLevelTwoTest } from "@/api/test/levelTwoTest"
import { listTestData } from "@/api/test/testData"

export default {
  name: "LevelTwoTest",
  data() {
    return {
      // 当前测试轮次（1=第一次测试，2=第二次测试）
      currentRound: 1,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 二级测试用例（第一轮测试）表格数据
      levelTwoTestList: [],
      // 查看加载状态
      childLoading: false,
      // 测试数据
      childTableData: [],
      // 数据总条数
      childTotal: 0,
      // 当前页码
      childPageNum: 1,
      // 每页大小
      childPageSize: 10,
      // 弹出层标题
      title: "",
      // 查看：弹出层标题
      viewTitle: "",
      // 是否显示弹出层
      open: false,
      // 查看：弹出层标题
      viewOpen: false,
      // 图表禁用提示
      chartDialogVisible: false,
      chartDialogMessage: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        levelTwoTestId: null,
        levelOneTestId: null,
        levelTwoTestContent: null,
        testRound: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        levelTwoTestContent: [
          { required: true, message: "二级测试用例名称不能为空", trigger: "blur" }
        ],
        levelTwoTestId: [
          { required: true, message: "二级测试用例业务ID不能为空", trigger: "blur" }
        ],
        levelOneTestId: [
          { required: true, message: "关联一级测试用例不能为空", trigger: "blur" }
        ],
        valueType: [
          { required: true, message: "值类型不能为空", trigger: "change" }
        ]
      }
    }
  },
  watch: {
    '$route.query.levelOneTestId': {
      immediate: true,
      handler() {
        this.initRouteParams()
        this.getList()
      }
    },
    '$route.query.round': {
      immediate: true,
      handler() {
        this.initRouteParams()
        this.getList()
      }
    }
  },

  methods: {
    initRouteParams() {
      this.queryParams.levelOneTestId = this.$route.query.levelOneTestId
      const round = Number(this.$route.query.round)
      this.currentRound = Number.isFinite(round) && round > 0 ? round : 1
      this.queryParams.testRound = this.currentRound
    },
    /** 查询二级测试用例列表 */
    getList() {
      this.initRouteParams()
      this.loading = true
      listLevelTwoTest(this.queryParams).then(response => {
        this.levelTwoTestList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getDetailData(levelTwoTestContent) {
      if (!levelTwoTestContent) {
        this.childLoading = false
        this.$message.warning("测试项名称为空，无法查询")
        return
      }
      this.childLoading = true
      listTestData({ levelTwoTestContent, pageNum: this.childPageNum, pageSize: this.childPageSize }).then(response => {
        this.childTableData = (response.rows || []).map(item => ({
          ...item,
          measured_value: item.measureValue,
          qualifiedText: item.isQualified === 1 ? '合格' : '不合格',
          rowClassName: item.isQualified === 1 ? '' : 'row-red'
        }))
        this.childTotal = response.total || 0
        this.childLoading = false
      }).catch(() => {
        this.childLoading = false
        this.$message.error("查询测试数据失败")
      })
    },
    /** 子表分页事件处理 */
    handleChildPagination() {
      this.getDetailData(this.form.levelTwoTestContent)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    cancelView() {
      this.viewOpen = false
      this.childTableData = []
      this.childPageNum = 1
      this.childTotal = 0
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        levelTwoTestContent: null,
        levelTwoTestId: null,
        levelOneTestId: null,
        valueType: null,
        testRound: this.currentRound
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加二级测试用例（第一轮测试）"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      // 单选模式下 this.ids 为数组，避免传数组导致接口请求异常
      const id = row.id || this.ids[0]
      getLevelTwoTest(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改二级测试用例（第一轮测试）"
      })
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset()
      const id = row.id || this.ids
      this.childLoading = true
      this.childPageNum = 1
      getLevelTwoTest(id).then(response => {
        this.form = response.data
        this.getDetailData(response.data.levelTwoTestContent)
        this.viewTitle = response.data.levelTwoTestContent + "(" + (response.data.expectedValue || '') + ")"
        this.viewOpen = true
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.testRound = this.currentRound
          if (!this.form.levelOneTestId) {
            this.form.levelOneTestId = this.$route.query.levelOneTestId
          }
          if (this.form.id != null) {
            updateLevelTwoTest(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addLevelTwoTest(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除二级测试用例（第一轮测试）编号为"' + ids + '"的数据项？').then(function() {
        return delLevelTwoTest(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('test/levelTwoTest/export', {
        ...this.queryParams
      }, `levelTwoTest_${new Date().getTime()}.xlsx`)
    },
    /** 图表按钮操作 */
    handleChart(row) {
      const levelOneTestId = row.levelOneTestId || ''
      const levelTwoTestId = row.levelTwoTestId || ''
      this.$router.push({
        path: `/chart/index/${levelOneTestId}/${levelTwoTestId}`,
        query: {
          levelTwoTestId: row.id,
          levelTwoTestContent: row.levelTwoTestContent,
          expectedValue: row.expectedValue,
          round: this.currentRound
        }
      })
    },
    // 表格行样式判断
    tableRowClassName({ row }) {
      if (row.isQualified === 1) {
        return ''
      }
      return 'row-red'
    },
    /** 异常处理按钮操作（与图表跳转一致：先校验子表并拉取数据，再进入页面） */
    handleError(row) {
      const levelOneTestId = row.levelOneTestId || ''
      const levelTwoTestId = row.levelTwoTestId || ''
      this.$router.push({
        path: `/test/error/${levelOneTestId}/${levelTwoTestId}`,
        query: {
          levelTwoTestContent: row.levelTwoTestContent,
          expectedValue: row.expectedValue,
        }
      })
    }

  }
}
</script>
<style scoped>
::v-deep .row-red {
  color: red !important;
}
</style>
