<template>
  <div class="Active" id="active"></div>
</template>

<script>
export default {
  name: 'ItemActive',
  data () {
    return {
      xData: [],
      yData: []
    }
  },
  methods: {
    setActive () {
      let active = this.$echarts.init(document.getElementById('active'))
      let option = {
        title: {
          text: 'Most Active Users',
          left: 'center'
        },
        grid: {
          top: '10%',
          left: '5%',
          right: '7%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: this.yData
        },
        series: [
          {
            data: this.xData,
            type: 'bar',
            itemStyle: {
              normal: {
                barBorderRadius: [0, 20, 20, 0],
                color: new this.$echarts.graphic.LinearGradient(
                  0, 0, 1, 0,
                  [
                    {offset: 1, color: '#a0cfff'},
                    {offset: 0.5, color: '#d9ecff'},
                    {offset: 0, color: '#ecf5ff'}
                  ]
                )
              }
            },
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      }
      active.setOption(option)
    }
  }
}
</script>

<style scoped>
.Active {
  height: 300px;
  width: 600px;
}
</style>
