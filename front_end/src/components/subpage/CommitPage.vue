<template>
  <el-container>
    <el-header style="display: flex; margin-left: 100px; margin-top: 50px">
      <span style="font-size: 35px; font-weight: bold; background-color: #a0cfff; border-radius: 15px; width: 500px; height: 40px">Total Release Count: {{ this.totalCount }}</span>
    </el-header>
    <div style="display: flex; margin-top: 50px">
      <h2 style="margin-left: 150px">Release and commits</h2>
      <h2 style="margin-left: 180px">Commit Distribution</h2>
    </div>
    <el-main style="margin-top: -30px; display: flex;">
      <div class="Release" id="release"></div>
      <div class="Commit" id="commit"></div>
    </el-main>
  </el-container>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CommitPage',
  data () {
    return {
      totalCount: 5,
      releases: [
        {name: '1', value: 20},
        {name: '2', value: 25}
      ],
      commits: [
        {time: '2022-12-1', count: 10},
        {time: '2022-12-2', count: 15},
        {time: '2022-12-3', count: 15},
        {time: '2022-12-4', count: 20},
        {time: '2022-12-5', count: 10},
        {time: '2022-12-6', count: 5},
        {time: '2022-12-7', count: 13},
        {time: '2022-12-8', count: 12}
      ]
    }
  },
  methods: {
    setRelease () {
      let release = this.$echarts.init(document.getElementById('release'))
      let option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: 'Release and commit',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: []
          }
        ]
      }
      option.series[0].data = this.releases
      console.log(option.series[0].data)
      release.setOption(option)
    },
    setCommit () {
      let commit = this.$echarts.init(document.getElementById('commit'))
      let dataAxis = []
      let data = []
      let yMax = 30
      let dataShadow = []
      for (let i = 0; i < this.commits.length; i++) {
        dataAxis.push(this.commits[i].time)
        data.push(this.commits[i].count)
        dataShadow.push(yMax)
      }
      let option = {
        xAxis: {
          data: dataAxis,
          axisLabel: {
            inside: false,
            color: 'black',
            fontSize: '16'
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          z: 10
        },
        yAxis: {
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            color: 'black',
            fontSize: '14'
          }
        },
        dataZoom: [
          {
            type: 'inside'
          }
        ],
        series: [
          {
            type: 'bar',
            itemStyle: {
              color: 'rgba(0,0,0,0.05)'
            },
            barWidth: 30,
            barGap: '-100%',
            data: dataShadow,
            animation: false
          },
          {
            type: 'bar',
            barWidth: 30,
            showBackground: true,
            itemStyle: {
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: '#83bff6'},
                {offset: 0.5, color: '#188df0'},
                {offset: 1, color: '#188df0'}
              ])
            },
            emphasis: {
              itemStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: '#2378f7'},
                  {offset: 0.7, color: '#2378f7'},
                  {offset: 1, color: '#83bff6'}
                ])
              }
            },
            data: data
          }
        ]
      }
      // Enable data zoom when user click bar.
      const zoomSize = 6
      commit.on('click', function (params) {
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)])
        commit.dispatchAction({
          type: 'dataZoom',
          startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
          endValue:
            dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        })
      })
      commit.setOption(option)
    },
    readData () {
      let url = `http://localhost:8181/repo_Info/release/Get_release_number?owner_repo=${this.owner}_${this.repo}`
      this.totalCount = 0
      axios.get(url).then(res => {
        this.totalCount = res.data
      })
      url = `http://localhost:8181/repo_Info/release/Get_commit_number_in_release?owner_repo=${this.owner}_${this.repo}`
      this.releases = []
      axios.get(url).then(res => {
        if (res.data.length > 0) {
          for (let i = 0; i < res.data.length; i++) {
            let tmp = {name: '', value: 0}
            tmp.name = res.data[i].now_release
            tmp.value = res.data[i].cnt
            if (tmp.value > 0) {
              this.releases.push(tmp)
            }
          }
          console.log(this.releases)
          this.setRelease()
        } else {
          let tmp = {name: 'No data', value: 0}
          this.releases.push(tmp)
          this.setRelease()
        }
      })

      url = `http://localhost:8181/repo_Info/commit/Get_commit_time_distribution?owner_repo=${this.owner}_${this.repo}`
      this.commits = []
      axios.get(url).then(res => {
        let temp = {time: '0:00-8:00', count: res.data[0]}
        this.commits.push(temp)
        temp = {time: '8:00-16:00', count: res.data[1]}
        this.commits.push(temp)
        temp = {time: '16:00-24:00', count: res.data[2]}
        this.commits.push(temp)
        this.setCommit()
      })
    }
  }
}
</script>

<style scoped>

.Release {
  height: 500px;
  width: 500px;
}

.Commit {
  height: 500px;
  width: 1100px;
  margin-left: -40px;
}

</style>
