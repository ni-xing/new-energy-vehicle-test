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
          <el-table-column label="测试值" align="center" prop="measureValue" min-width="120" />
          <el-table-column label="是否合格" align="center" width="120">
            <template slot-scope="scope">
              <span class="text-danger">不合格</span>
            </template>
          </el-table-column>
          <el-table-column label="记录时间" align="center" prop="gmtCreated" width="180" />
          <el-table-column label="处理时间" align="center" prop="gmtModified" width="180" />
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
import { listTestData, updateTestData } from '@/api/test/testData'
import { createNamespacedHelpers } from 'vuex'

const { mapActions: mapTagsViewActions } = createNamespacedHelpers('tagsView')

const PROGRESS_OPTIONS = [
  { label: '未处理', value: 0 },
  { label: '正在处理', value: 1 },
  { label: '解决', value: 2 },
  { label: '无法解决', value: 3 }
]

function isUnqualified(row) {
  const q = row.isQualified
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
          const timeA = new Date(a.gmtCreated).getTime()
          const timeB = new Date(b.gmtCreated).getTime()
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
    getProgressValue(row) {
      return toProgressNumber(row.processStatus)
    },
    saveProgress(row, processStatus) {
      return updateTestData({
        id: row.id,
        processStatus,
        levelTwoTestContent: row.levelTwoTestContent,
        measureValue: row.measureValue,
        valueType: row.valueType,
        isQualified: row.isQualified,
        gmtCreated: row.gmtCreated,
        gmtModified: row.gmtModified
      })
    },
    onProgressChange(row, val) {
      const nextValue = toProgressNumber(val)
      this.rowUpdatingId = row.id
      this.saveProgress(row, nextValue)
        .then(() => {
          row.processStatus = nextValue
          this.$message.success('处理进度已保存')
        })
        .catch(() => {
          this.$message.error('保存处理进度失败')
        })
        .finally(() => {
          this.rowUpdatingId = null
        })
    },
    loadData() {
      const levelTwoTestContent = this.$route.query.levelTwoTestContent
      if (!levelTwoTestContent) {
        this.loading = false
        this.$message.warning('缺少测试项名称')
        return
      }

      this.loading = true
      listTestData({ levelTwoTestContent, isQualified: 0 })
        .then(response => {
          this.rawData = Array.isArray(response && response.rows) ? response.rows.map(item => ({
            ...item,
            measureValue: item.measureValue,
            gmtCreated: item.gmtCreated,
            gmtModified: item.gmtModified,
            processStatus: item.processStatus,
            isQualified: item.isQualified,
            levelTwoTestContent: item.levelTwoTestContent,
            valueType: item.valueType
          })) : []
        })
        .catch(() => {
          this.rawData = []
          this.$message.error('获取测试数据失败')
        })
        .finally(() => {
          this.loading = false
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
