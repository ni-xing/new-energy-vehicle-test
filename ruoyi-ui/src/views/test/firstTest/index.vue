<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="130px">
      <el-form-item label="一级测试项目名称" prop="levelOneTestId">
        <el-input
          v-model="queryParams.levelOneTestId"
          placeholder="请输入一级测试项目名称"
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
          v-hasPermi="['test:firstTest:add']"
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
          v-hasPermi="['test:firstTest:edit']"
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
          v-hasPermi="['test:firstTest:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['test:firstTest:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="firstTestList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键" align="center" prop="id" />
      <el-table-column label="一级测试项目名称" align="center">
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" @click="handleGoToDetail(scope.row)">{{ scope.row.levelOneTestContent }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="一级测试项目ID" align="center" prop="levelOneTestId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['test:firstTest:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['test:firstTest:remove']"
          >删除</el-button>
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

    <!-- 添加或修改一级测试用例（第一轮测试）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="一级测试项目名称">
          <editor v-model="form.levelOneTestContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="一级测试项目ID" prop="levelOneTestId">
          <el-input v-model="form.levelOneTestId" placeholder="请输入一级测试项目ID" />
        </el-form-item>
        <el-divider content-position="center">二级测试项目信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddLevelTwoTestRound1">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteLevelTwoTestRound1">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="levelTwoTestRound1List" :row-class-name="rowLevelTwoTestRound1Index" @selection-change="handleLevelTwoTestRound1SelectionChange" ref="levelTwoTestRound1">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="二级测试项目ID" prop="levelTwoTestId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.levelTwoTestId" placeholder="请输入二级测试项目ID" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFirstTest, getFirstTest, delFirstTest, addFirstTest, updateFirstTest, goToDetail } from "@/api/test/firstTest"

export default {
  name: "FirstTest",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedLevelTwoTestRound1: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 一级测试用例（第一轮测试）表格数据
      firstTestList: [],
      // 二级测试用例（第一轮测试）表格数据
      levelTwoTestRound1List: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        levelOneTestContent: null,
        levelOneTestId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        levelOneTestContent: [
          { required: true, message: "一级测试项目名称不能为空", trigger: "blur" }
        ],
        levelOneTestId: [
          { required: true, message: "一级测试项目ID不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询一级测试项目列表 */
    getList() {
      this.loading = true
      listFirstTest(this.queryParams).then(response => {
        this.firstTestList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        levelOneTestContent: null,
        levelOneTestId: null
      }
      this.levelTwoTestRound1List = []
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
      this.title = "添加一级测试项目"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getFirstTest(id).then(response => {
        this.form = response.data
        this.levelTwoTestRound1List = response.data.levelTwoTestRound1List
        this.open = true
        this.title = "修改一级测试项目"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.levelTwoTestRound1List = this.levelTwoTestRound1List
          if (this.form.id != null) {
            updateFirstTest(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addFirstTest(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除一级测试项目 编号为"' + ids + '"的数据项？').then(function() {
        return delFirstTest(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
	/** 二级测试用例（第一轮测试）序号 */
    rowLevelTwoTestRound1Index({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 二级测试项目添加按钮操作 */
    handleAddLevelTwoTestRound1() {
      let obj = {}
      obj.levelTwoTestContent = ""
      obj.levelTwoTestId = ""
      this.levelTwoTestRound1List.push(obj)
    },
    /** 二级测试项目删除按钮操作 */
    handleDeleteLevelTwoTestRound1() {
      if (this.checkedLevelTwoTestRound1.length == 0) {
        this.$modal.msgError("请先选择要删除的二级测试项目数据")
      } else {
        const levelTwoTestRound1List = this.levelTwoTestRound1List
        const checkedLevelTwoTestRound1 = this.checkedLevelTwoTestRound1
        this.levelTwoTestRound1List = levelTwoTestRound1List.filter(function(item) {
          return checkedLevelTwoTestRound1.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleLevelTwoTestRound1SelectionChange(selection) {
      this.checkedLevelTwoTestRound1 = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('test/firstTest/export', {
        ...this.queryParams
      }, `firstTest_${new Date().getTime()}.xlsx`)
    },
    /** 跳转二级测试目录页面 */
    handleGoToDetail(row) {
        this.$router.push({
          path: '/test/levelTwoTest',
          query: {
            levelOneTestId: row.id
          }
        })
    }
  }
}
</script>
