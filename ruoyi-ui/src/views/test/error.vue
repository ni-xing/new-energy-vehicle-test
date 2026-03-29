<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>{{ headerTitle }}</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="goBack">返回</el-button>
      </div>

      <div v-if="loading" class="loading-wrap">
        <i class="el-icon-loading"></i>
        <span>加载中...</span>
      </div>

      <template v-else>
        <el-table
          v-if="unqualifiedList.length > 0"
          :data="unqualifiedList"
          border
          stripe
          :row-class-name="tableRowClassName"
        >
          <el-table-column label="自增主键" align="center" prop="id" width="100" />
          <el-table-column label="测试值" align="center" prop="measured_value" min-width="120" />
          <el-table-column label="是否合格" align="center" width="120">
            <template slot-scope="scope">
              <span class="text-danger">不合格</span>
            </template>
          </el-table-column>
          <el-table-column label="记录时间" align="center" prop="gmt_created" width="180" />
          <el-table-column label="修改时间" align="center" width="180">
            <template slot-scope="scope">
              <span>{{ formatGmtModified(scope.row.gmt_modified) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="处理进度" align="center" min-width="168" fixed="right">
            <template slot-scope="scope">
              <el-select
                :value="getProgressValue(scope.row)"
                placeholder="请选择"
                size="small"
                clearable
                :disabled="rowUpdatingId === scope.row.id"
                class="progress-select"
                @change="val => onProgressChange(scope.row, val)"
              >
                <el-option
                  v-for="opt in progressOptions"
                  :key="opt.value"
                  :label="opt.label"
                  :value="opt.value"
                />
              </el-select>
            </template>
          </el-table-column>
        </el-table>
        <el-empty v-else description="暂无不合格数据" />
      </template>
    </el-card>
  </div>
</template>

<script>
import { getChildTableData, updateChildTableProgress } from '@/api/test/levelTwoTest'
import { createNamespacedHelpers } from 'vuex'

const { mapActions: mapTagsViewActions } = createNamespacedHelpers('tagsView')

/**
 * 与库字段 process_progress 一致：
 * 0-未处理 1-处理中 2-已处理 3-无法处理
 */
const PROGRESS_OPTIONS = [
  { label: '未处理', value: 0 },
  { label: '正在处理', value: 1 },
  { label: '解决', value: 2 },
  { label: '无法解决', value: 3 }
]

function isUnqualified(row) {
  const q = row.qualified
  return q === false || q === 0 || q === 'false'
}

function toProgressNumber(v) {
  if (v === null || v === undefined || v === '') {
    return 0
  }
  const n = Number(v)
  return Number.isNaN(n) ? 0 : n
}

export default {
  name: 'TestError',
  data() {
    return {
      loading: true,
      rawData: [],
      progressOptions: PROGRESS_OPTIONS,
      rowUpdatingId: null
    }
  },
  computed: {
    headerTitle() {
      const name = this.$route.query.levelTwoTestContent || ''
      const exp = this.$route.query.expectedValue || ''
      return name ? `${name} - 预期值: ${exp}` : '异常处理'
    },
    unqualifiedList() {
      return (this.rawData || [])
        .filter(isUnqualified)
        .sort((a, b) => {
          const timeA = new Date(a.gmt_created).getTime()
          const timeB = new Date(b.gmt_created).getTime()
          if (timeA !== timeB) {
            return timeA - timeB
          }
          return (a.id || 0) - (b.id || 0)
        })
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    ...mapTagsViewActions(['delView']),
    goBack() {
      this.delView(this.$route).then(() => {
        this.$router.go(-1)
      })
    },
    tableRowClassName() {
      return 'row-unqualified'
    },
    formatGmtModified(val) {
      if (!val) {
        return '—'
      }
      return this.parseTime(val) || '—'
    },
    getProgressValue(row) {
      return toProgressNumber(row.process_progress)
    },
    async onProgressChange(row, value) {
      const childTableName = this.$route.query.childTableName
      if (!childTableName) {
        this.$message.warning('缺少子表名称')
        return
      }
      const progress = value === null || value === undefined || value === '' ? 0 : Number(value)
      this.rowUpdatingId = row.id
      try {
        await updateChildTableProgress(childTableName, {
          id: row.id,
          processProgress: progress
        })
        this.$set(row, 'process_progress', progress)
        this.$set(row, 'gmt_modified', new Date())
        this.$message.success('处理进度已保存')
      } catch (e) {
        /* 全局拦截已提示 */
      } finally {
        this.rowUpdatingId = null
      }
    },
    loadData() {
      const childTableName = this.$route.query.childTableName
      if (!childTableName) {
        this.loading = false
        this.$message.warning('缺少子表名称')
        return
      }

      this.loading = true
      getChildTableData(childTableName, { pageNum: 1, pageSize: 1000 })
        .then(response => {
          this.rawData = response.rows || []
          this.loading = false
        })
        .catch(() => {
          this.loading = false
          this.$message.error('获取子表数据失败')
        })
    }
  }
}
</script>

<style scoped lang="scss">
.box-card {
  margin-bottom: 30px;
  min-height: 320px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: '';
}

.clearfix:after {
  clear: both;
}

.loading-wrap {
  text-align: center;
  padding: 48px 0;
  color: #909399;

  .el-icon-loading {
    margin-right: 8px;
    font-size: 18px;
  }
}

.text-danger {
  color: #f56c6c;
  font-weight: 600;
}

::v-deep .row-unqualified {
  color: #f56c6c;
}

.progress-select {
  width: 100%;
  max-width: 200px;
}
</style>
