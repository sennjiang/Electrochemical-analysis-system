<template>
  <!--设置宽高-->
  <div style="width: 80%; height: 400px">
    <!--echarts图-->
    <div :class="echart" :style="{height:500,width:400}"/>
    <!-- 其他内容可以放 -->
  </div>

</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme

export default {
  props:{
    detailV:{
      type:Array,
      required: true
    },
    detailA:{
      type:Array,
      required: true
    },
    curveAmount:{
      type:Number,
      required: true
    }
  },
  data() {
    return {
      resultVisible: false,
      result: null,
      xy: null,
      yx:null,
      // echart对象
      chart: null,
      // x轴标点 改x轴动态更改此变量
      xdata: ['-0.6', '-0.4', '0', '0.1', '0.2', '0.4', '0.6'],
      // y轴的真正数据 如果空数组就不显示图线
      y1data: [100, 20, 60, 100, 70, 60, 70],
      y2data: [],
      // 点击点以后会将坐标值赋给x， y
      x: undefined,
      y: undefined,
      times: 0
    }
  },
  watch: {
    xdata: {
      deep: true,
      handler() {
        this.setOptions()
      }
    },
    ydata: {
      deep: true,
      handler() {
        this.setOptions()
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose();
    this.chart = null
  },
  created() {
    this.xdata = this.detailV;
    if(this.curveAmount == 1) this.y1data = this.detailA;
    else if(this.curveAmount == 2) this.y2data = this.detailA;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions()
    },
    setOptions() {
      this.chart.setOption({
        backgroundColor: '#394056',
        title: {
          top: 20,
          textStyle: {
            fontWeight: 'normal',
            fontSize: 16,
            color: '#F1F1F3'
          },
          left: '1%'
        },
        toolbox: {
          show: true,
          // 启用功能，目前支持feature，工具箱自定义功能回调处理
          feature: {
            // 辅助线标志
            mark: { show: true },
            // dataZoom，框选区域缩放，自动与存在的dataZoom控件同步，分别是启用，缩放后退
            dataZoom: {
              show: true,
              title: {
                dataZoom: '区域缩放',
                dataZoomReset: '区域缩放后退'
              }
            },
            // 数据视图，打开数据视图，可设置更多属性,readOnly 默认数据视图为只读(即值为true)，可指定readOnly为false打开编辑功能
            dataView: { show: true, readOnly: false },
            // magicType，动态类型切换，支持直角系下的折线图、柱状图、堆积、平铺转换
            magicType: { show: false, type: ['line', 'bar'] },
            // restore，还原，复位原始图表
            restore: { show: true },
            // saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'
            saveAsImage: { show: true }
          }
        },
        xAxis: {
          data: this.xdata,
          boundaryGap: false,
          type: 'category',
          axisLine: {
            lineStyle: {
              color: '#1ca5bb'
            }
          }
        },
        grid: {
          top: 100,
          left: '2%',
          right: '2%',
          bottom: '2%',
          containLabel: true
        },
        // 提示框，鼠标悬浮交互时的信息提示
        tooltip: {
          // 触发类型，默认（'item'）数据触发，可选为：'item' | 'axis'
          trigger: 'axis',
          axisPointer: {
            lineStyle: {
              color: '#57617B'
            }
          },
          textStyle: {
            color: '#FFFFFF'
          }
        },
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: '#1ca5bb'
            }
          },
          axisLabel: {
            margin: 10,
            textStyle: {
              fontSize: 14
            }
          },
          splitLine: {
            lineStyle: {
              color: '#57617B'
            }
          }
        }],
        // 图例，每个图表最多仅有一个图例
        //legend: {
        //  // 显示策略，可选为：true（显示） | false（隐藏），默认值为true
        //  show: false,
        //  // legend的data: 用于设置图例，data内的字符串数组需要与sereis数组内每一个series的name值对应
        //  data: ['1', '2']
        //},
        series : [{
          name: '1',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 5,
          showSymbol: false,
          lineStyle: {
            normal: {
              width: 1
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(137, 189, 27, 0.3)'
              }, {
                offset: 0.8,
                color: 'rgba(137, 189, 27, 0)'
              }], false),
              shadowColor: 'rgba(0, 0, 0, 0.1)',
              shadowBlur: 10
            }
          },
          itemStyle: {
            normal: {
              color: 'rgb(137,189,27)',
              borderColor: 'rgba(137,189,2,0.27)',
              borderWidth: 12

            }
          },
          data: this.y1data
        }, {
          name: '2',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 5,
          showSymbol: false,
          lineStyle: {
            normal: {
              width: 1
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(0, 136, 212, 0.3)'
              }, {
                offset: 0.8,
                color: 'rgba(0, 136, 212, 0)'
              }], false),
              shadowColor: 'rgba(0, 0, 0, 0.1)',
              shadowBlur: 10
            }
          },
          itemStyle: {
            normal: {
              color: 'rgb(0,136,212)',
              borderColor: 'rgba(0,136,212,0.2)',
              borderWidth: 12
            }
          },
          data: this.y2data
        }]
      })

      // 点击事件
      this.chart.on('click',(params) => {
        this.times ++
        this.y = params.data
        this.x = this.xdata[params.dataIndex]
        if ( this.times == 1) {
          this.xy = [this.y,this.x]
        }else if( this.times == 2){
          this.yx = [this.y,this.x]
          console.log(this.xy[0])
          console.log(this.yx[0])
          this.result = (this.xy[0] + this.yx[0] ) / 2,
          this.resultVisible = true
          this.$message({
              message: this.result,
              type: 'success'
          })
          this.times = 0;
        }
      });

    }
  }
}
</script>
