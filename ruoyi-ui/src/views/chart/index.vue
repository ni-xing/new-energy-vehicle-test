<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>{{ $route.query.levelTwoTestContent || '' }} - 预期值: {{ $route.query.expectedValue || '' }}</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="goBack">返回</el-button>
      </div>

      <div v-if="rawData.length > 0">
        <!-- 图表 -->
        <div ref="chartEl" style="width: 100%; height: 400px;"></div>
      </div>

      <div v-else-if="loading">
        <div style="text-align: center; padding: 50px;">
          <i class="el-icon-loading"></i> 加载中...
        </div>
      </div>

      <el-empty v-else description="暂无图表数据"></el-empty>
    </el-card>
  </div>
</template>

<script>
import { listTestData } from "@/api/test/testData";
import { getLevelTwoTest } from "@/api/test/levelTwoTest";
import { createNamespacedHelpers } from 'vuex';
import * as echarts from 'echarts';
import resize from '../dashboard/mixins/resize'

const { mapActions: mapTagsViewActions } = createNamespacedHelpers('tagsView');

export default {
  name: "chart",
  mixins: [resize],
    data() {
    return {
      loading: true,
      rawData: [],
      chart: null,
      expectedValue: '',
      levelTwoTestContent: '',
      valueType: ''
    }
  },
  created() {
    this.levelTwoTestContent = this.$route.query.levelTwoTestContent || '';
    this.expectedValue = this.$route.query.expectedValue || '';
    this.getMetaAndData();
  },
  beforeDestroy() {
    this.disposeChart()
  },
  methods: {
    ...mapTagsViewActions(['delView']),
    goBack() {
      this.delView(this.$route).then(() => {
        this.$router.go(-1);
      });
    },
    disposeChart() {
      const dom = this.$refs.chartEl
      if (this.chart) {
        this.chart.dispose()
        this.chart = null
      } else if (dom) {
        const instance = echarts.getInstanceByDom(dom)
        instance && instance.dispose()
      }
    },
    getMetaAndData() {
      if (!this.levelTwoTestContent) {
        this.loading = false
        this.rawData = []
        this.disposeChart()
        this.$message.warning('缺少二级测试项名称')
        return
      }

      this.disposeChart()
      this.loading = true
      this.rawData = []

      getLevelTwoTest(this.$route.query.levelTwoTestId || '').then(response => {
        this.valueType = (response.data && response.data.valueType) || ''
        const params = {
          levelTwoTestContent: this.levelTwoTestContent,
          pageNum: 1,
          pageSize: 1000
        }
        return listTestData(params)
      })
        .then(response => {
          const rows = Array.isArray(response && response.rows) ? response.rows : []
          this.rawData = rows.map(item => ({
            ...item,
            measureValue: this.normalizeMeasuredValue(item.measureValue, this.valueType)
          }))
          this.$nextTick(() => this.initChart())
        })
        .catch(() => {
          this.$message.error('获取测试数据失败')
        })
        .finally(() => {
          this.loading = false
        })
    },
    normalizeMeasuredValue(value, valueType) {
      if (value === null || value === undefined || value === '') {
        return null
      }
      const type = (valueType || '').toLowerCase()
      if (type === 'int') {
        const n = parseInt(value, 10)
        return Number.isNaN(n) ? NaN : n
      }
      if (type === 'float') {
        const n = parseFloat(value)
        return Number.isNaN(n) ? NaN : n
      }
      return value
    },
    initChart() {
      if (!this.rawData || this.rawData.length === 0) return

      const dom = this.$refs.chartEl
      if (!dom) return

      // 防止对同一 DOM 重复 init
      const existing = echarts.getInstanceByDom(dom)
      this.chart = existing || echarts.init(dom)

      // 按时间顺序排序数据，相同时间按id排序
      const sortedData = [...this.rawData].sort((a, b) => {
        const timeA = new Date(a.gmtCreated).getTime()
        const timeB = new Date(b.gmtCreated).getTime()
        if (timeA !== timeB) return timeA - timeB
        return (a.id || 0) - (b.id || 0)
      })

      const formatDate = (dateStr) => {
        if (!dateStr) return ''
        const date = new Date(dateStr)
        const year = date.getFullYear()
        const month = String(date.getMonth() + 1).padStart(2, '0')
        const day = String(date.getDate()).padStart(2, '0')
        return `${year}-${month}-${day}`
      }

      const isUnqualified = (qualified) => qualified === 0

      const qualifiedLabel = (qualified) => {
        if (qualified === 1) return '合格'
        if (qualified === 0) return '不合格'
        return '未知'
      }

      const parseMeasuredValue = (v) => {
        // null/undefined/空字符串 -> 允许（按 0 展示），但非数字字符串 -> 阻止渲染
        if (v === null || v === undefined) return null
        if (typeof v === 'string') {
          const t = v.trim()
          if (!t) return null
          const n = parseFloat(t)
          return Number.isFinite(n) ? n : NaN
        }
        if (typeof v === 'number') return Number.isFinite(v) ? v : NaN
        return NaN
      }

      // 预期参考线：提取数字；若包含范围分隔符则画上下限，否则画单值
      const expectedValue = (this.expectedValue || '').trim()
      const nums = (expectedValue.match(/\d+(?:\.\d+)?/g) || []).map(Number).filter(Number.isFinite)
      const hasRange = /[-~～]/.test(expectedValue)

      let expectedLines = []
      if (hasRange && nums.length >= 2) {
        const lower = Math.min(nums[0], nums[1])
        const upper = Math.max(nums[0], nums[1])
        expectedLines = [
          { value: lower, label: '下限: ' + lower },
          { value: upper, label: '上限: ' + upper }
        ]
      } else if (nums.length >= 1) {
        expectedLines = [{ value: nums[0], label: '预期值: ' + nums[0] }]
      }

      const invalidMeasured = sortedData.some(item => {
        const parsed = parseMeasuredValue(item.measureValue)
        return parsed !== null && Number.isNaN(parsed)
      })
      if (invalidMeasured) {
        this.$message.warning('测量值存在非数字内容，暂不支持展示该数据')
        return
      }

      const xData = sortedData.map(item => formatDate(item.gmtCreated))
      const points = sortedData.map(item => {
        const measuredNum = parseMeasuredValue(item.measureValue)
        const value = measuredNum === null ? 0 : measuredNum
        const qualified = item.isQualified
        const unqualified = isUnqualified(qualified)
        return {
          value,
          itemStyle: { color: unqualified ? '#F56C6C' : '#409EFF' },
          record: {
            id: item.id || '',
            date: formatDate(item.gmtCreated),
            measuredValue: value,
            qualifiedLabel: qualifiedLabel(qualified),
            isUnqualified: unqualified
          }
        }
      })

      const expectedMarkLine = expectedLines.length > 0
        ? {
            symbol: ['none', 'none'],
            label: { show: true, position: 'end' },
            lineStyle: { color: '#67C23A', type: 'dashed', width: 2 },
            data: expectedLines.map((item, index) => ({
              yAxis: item.value,
              label: { formatter: item.label },
              lineStyle: {
                color: index === 0 ? '#F56C6C' : '#409EFF',
                type: 'dashed',
                width: 2
              }
            })),
            animation: false
          }
        : undefined

      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: (params) => {
            const data = params && params[0]
            const record = data && data.data && data.data.record ? data.data.record : null
            if (!record) return ''
            const contentStyle = record.isUnqualified ? 'color: #F56C6C; font-weight: bold;' : ''
            return `<div style="padding: 5px;">
              <div style="margin-bottom: 5px; ${contentStyle}">ID: ${record.id || ''}</div>
              <div style="margin-bottom: 5px; ${contentStyle}">日期: ${record.date || ''}</div>
              <div style="margin-bottom: 5px; ${contentStyle}">测量值: ${record.measuredValue}</div>
              <div style="margin-bottom: 5px; ${contentStyle}">状态: ${record.qualifiedLabel || '未知'}</div>
            </div>`
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '15%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: xData,
          axisLabel: { show: false },
          axisTick: { show: false },
          axisLine: { show: false }
        },
        yAxis: {
          type: 'value',
          name: '测量值'
        },
        dataZoom: [
          { type: 'inside', start: 0, end: 10, bottom: 12 },
          { start: 0, end: 10, bottom: 12 }
        ],
        series: [
          {
            data: points,
            type: 'line',
            smooth: false,
            ...(expectedMarkLine ? { markLine: expectedMarkLine } : {})
          }
        ]
      }

      this.chart.setOption(option)
    }
  }
}
</script>

<style scoped>
.box-card {
  margin-bottom: 30px;
  min-height: 500px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>
