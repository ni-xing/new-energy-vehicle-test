<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="二级测试用例业务ID" prop="levelTwoTestId">
        <el-input
          v-model="queryParams.levelTwoTestId"
          placeholder="请输入二级测试用例业务ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联一级测试用例" prop="levelOneTestId">
        <el-input
          v-model="queryParams.levelOneTestId"
          placeholder="请输入关联一级测试用例"
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['test:levelTwoTest:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['test:levelTwoTest:remove']"
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLevelTwoTest, getLevelTwoTest, delLevelTwoTest, addLevelTwoTest, updateLevelTwoTest } from "@/api/test/levelTwoTest"

export default {
  name: "LevelTwoTest",
  data() {
    return {
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        levelTwoTestContent: null,
        levelTwoTestId: null,
        levelOneTestId: null
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
        ]
      }
    }
  },
  watch: {
    '$route.query.levelOneTestId': {
      immediate: true, // 组件挂载时立即执行一次
      handler(newVal) {
        if (newVal) {
          this.queryParams.levelOneTestId = newVal
          this.getList() // 每次参数变化时调用list
        }
      }
    }
  },

  methods: {
    /** 查询二级测试用例（第一轮测试）列表 */
    getList() {
      this.queryParams.levelOneTestId = this.$route.query.levelOneTestId
      this.loading = true
      listLevelTwoTest(this.queryParams).then(response => {
        this.levelTwoTestList = response.rows
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
        levelTwoTestContent: null,
        levelTwoTestId: null,
        levelOneTestId: null
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
      const id = row.id || this.ids
      getLevelTwoTest(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改二级测试用例（第一轮测试）"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
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
    }
  }
}
</script>
