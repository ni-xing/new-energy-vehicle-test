<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>{{ $route.query.levelTwoTestContent || '' }} - 预期值: {{ $route.query.expectedValue || '' }}</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="goBack">返回</el-button>
      </div>

      <div v-if="rawData.length > 0">
        <!-- 图表 -->
        <div id="chart" style="width: 100%; height: 400px;"></div>
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
import { getChildTableData } from "@/api/test/levelTwoTest";
import { mapGetters, mapActions, createNamespacedHelpers } from 'vuex';
import * as echarts from 'echarts';

const { mapActions: mapTagsViewActions } = createNamespacedHelpers('tagsView');

export default {
  name: "chart",
    data() {
    return {
      childTableName: null,
      loading: true,
      rawData: [],
      chart: null,
      isCharValue: false  // 测试值是否为字符类型
    }
  },
  created() {
    this.childTableName = this.$route.query.childTableName || null;
    this.getData();
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
  computed: {
    ...mapGetters(['visitedViews'])
  },
  methods: {
    ...mapTagsViewActions(['delView']),
    goBack() {
      this.delView(this.$route).then(() => {
        this.$router.go(-1);
      });
    },
    getData() {
      if (!this.childTableName) {
        this.loading = false;
        this.$message.warning('缺少子表名称');
        return;
      }

      this.loading = true;
      this.getChildTableData(this.childTableName);
    },
    getChildTableData(childTableName) {
      if (!childTableName) {
        this.loading = false;
        this.$message.warning('子表名称为空');
        return;
      }

      const params = {
        pageNum: 1,
        pageSize: 1000
      };

      getChildTableData(childTableName, params).then(response => {
        this.rawData = response.rows || [];
        this.loading = false;
        this.$nextTick(() => {
          this.initChart();
        });
      }).catch(() => {
        this.loading = false;
        this.$message.error('获取子表数据失败');
      });
    },
    initChart() {
      if (this.rawData.length === 0) {
        return;
      }

      this.chart = echarts.init(document.getElementById('chart'));

      // 按时间顺序排序数据，相同时间按id排序
      const sortedData = [...this.rawData].sort((a, b) => {
        const timeA = new Date(a.gmt_created).getTime();
        const timeB = new Date(b.gmt_created).getTime();
        if (timeA !== timeB) {
          return timeA - timeB;
        }
        // 时间相同时，按id比较
        return (a.id || 0) - (b.id || 0);
      });

      // 格式化时间为 yyyy-MM-dd
      const formatDate = (dateStr) => {
        if (!dateStr) return '';
        const date = new Date(dateStr);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
      };

      const xData = sortedData.map(item => formatDate(item.gmt_created));
      const yData = sortedData.map(item => item.measured_value !== undefined ? item.measured_value : 0);
      const qualifiedData = sortedData.map(item => item.qualified === true ? '合格' : (item.qualified === false ? '不合格' : '未填写'));

      // 从预期值中提取数值（支持范围格式，如 "10-20"、"12V-16V" 或 "10~15"）
      const expectedValue = this.$route.query.expectedValue || '';
      console.log('预期值:', expectedValue);
      let expectedLines = [];
      if (expectedValue) {
        // 尝试匹配范围格式（用 - 或 ~ 分隔，支持带单位如 12V-16V）
        // 匹配：数字（可带小数点，可带单位字母） + 分隔符 + 数字（可带小数点，可带单位字母）
        const rangeMatch = expectedValue.match(/^([\d.]+\s*[a-zA-Z]*)\s*[-~]\s*([\d.]+\s*[a-zA-Z]*)$/);
        console.log('范围匹配:', rangeMatch);
        if (rangeMatch) {
          // 范围格式：上下限都有
          const lowerNum = parseFloat(rangeMatch[1]);
          const upperNum = parseFloat(rangeMatch[2]);
          const lowerLabel = rangeMatch[1].trim();
          const upperLabel = rangeMatch[2].trim();
          if (!isNaN(lowerNum) && !isNaN(upperNum)) {
            expectedLines = [
              { value: lowerNum, label: '下限: ' + lowerLabel },
              { value: upperNum, label: '上限: ' + upperLabel }
            ];
          }
        } else {
          // 单值格式（保留单位）
          const num = parseFloat(expectedValue);
          console.log('单值解析:', expectedValue, num);
          if (!isNaN(num)) {
            expectedLines = [{ value: num, label: '预期值: ' + expectedValue }];
          }
        }
      }
      console.log('预期参考线:', expectedLines);

      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: function (params) {
            const data = params[0];
            const index = data.dataIndex;
            const item = sortedData[index];
            const qualified = item.qualified;
            const isUnqualified = qualified === false || qualified === 'false' || qualified === 0;

            // 不合格时全部标红
            const contentStyle = isUnqualified ? 'color: #F56C6C; font-weight: bold;' : '';

            return `<div style="padding: 5px;">
              <div style="margin-bottom: 5px; ${contentStyle}">ID: ${item.id || ''}</div>
              <div style="margin-bottom: 5px; ${contentStyle}">日期: ${xData[index]}</div>
              <div style="margin-bottom: 5px; ${contentStyle}">测量值: ${yData[index]}</div>
              <div style="margin-bottom: 5px; ${contentStyle}">状态: ${qualifiedData[index]}</div>
            </div>`;
          }
        },
        // 新增 grid 配置，调整底部边距
        grid: {
          left: '3%',
          right: '4%',
          bottom: '15%', // 增大底部边距，为 dataZoom 留出空间
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: xData,
          name: '日期',
          nameLocation: 'middle',
          nameGap: 30,
          axisLabel: {
            show: true,
            interval: 0
          }
        },
        yAxis: {
          type: 'value',
          name: '测量值'
        },
        dataZoom: [
          {
            type: 'inside',
            start: 0,
            end: 10,
            bottom: 12
          },
          {
            start: 0,
            end: 10,
            bottom: 12
          }
        ],
        series: [
          {
            data: yData.map((value, index) => {
              const qualified = sortedData[index].qualified;
              const isUnqualified = qualified === false || qualified === 'false' || qualified === 0;
              return {
                value: value,
                itemStyle: {
                  color: isUnqualified ? '#F56C6C' : '#409EFF'
                }
              };
            }),
            type: 'line',
            smooth: false
          }
        ]
      };

      this.chart.setOption(option);

      // 添加预期值参考线（支持单值和范围）
      if (expectedLines.length > 0) {
        console.log('添加预期参考线:', expectedLines);
        this.chart.setOption({
          series: [{
            markLine: {
              symbol: ['none', 'none'],
              label: {
                show: true,
                position: 'end'
              },
              // 默認樣式（單值時使用）
              lineStyle: {
                color: '#67C23A',
                type: 'dashed',
                width: 2
              },
              data: expectedLines.map((item, index) => ({
                yAxis: item.value,
                label: { formatter: item.label },
                // 每條線單獨設置顏色：第一條下限紅色，第二條上限藍色
                lineStyle: {
                  color: index === 0 ? '#F56C6C' : '#409EFF',
                  type: 'dashed',
                  width: 2
                }
              })),
              animation: false
            }
          }]
        });
      } else {
        console.log('未添加预期参考线，expectedLines 为空');
      }
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
