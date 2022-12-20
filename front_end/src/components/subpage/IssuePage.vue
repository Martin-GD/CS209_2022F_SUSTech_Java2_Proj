<template>
  <div class="container">
    <el-container>
      <el-header style="display: flex; margin-left: 200px; margin-top: -30px">
        <div class="Chart" id="chart" style="margin-top: 30px"></div>
        <div class="Table" style="margin-left: 40px; margin-top: 100px; width: 600px">
          <el-table
            :data="issuesManage"
            class="issuesManageTable"
            :header-cell-style="{'background-color': 'cornflowerblue', 'color': '#fff', 'font-size': '18px'}"
            style="width: 100%; border-radius: 15px;">
            <el-table-column label="average(day)" prop="average"></el-table-column>
            <el-table-column label="difference(day)" prop="difference"></el-table-column>
            <el-table-column label="variance(day^2)" prop="variance"></el-table-column>
          </el-table>
        </div>
        <div class="Search" style="margin-top: 130px; margin-left: 50px; width: 400px;">
          <el-input
            placeholder="请输入"
            class="Input"
            v-model="input"
          >
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </div>
      </el-header>
      <el-main style="margin-top: 100px">
        <h2 style="display: flex; margin-left: 20px">Issue Details</h2>
        <el-table
          :data="issues"
          class="IssueTable"
          row-key="id"
          :expand-row-keys="expands"
          @row-click="clickRowHandle"
          style="width: 1500px; border-radius: 15px; margin-top: 10px">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="TableExpand">
                <el-form-item label="Id: ">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
                <el-form-item label="Title: ">
                  <span>{{ props.row.title }}</span>
                </el-form-item>
                <el-form-item label="User: ">
                  <span>{{ props.row.user_login }}</span>
                </el-form-item>
                <el-form-item label="Number: ">
                  <span>{{ props.row.number }}</span>
                </el-form-item>
                <el-form-item label="Create Time: ">
                  <span>{{ props.row.created_at }}</span>
                </el-form-item>
                <el-form-item label="Last Update Time: ">
                  <span>{{ props.row.update_at }}</span>
                </el-form-item>
                <el-form-item label="State: ">
                  <span>{{ props.row.state }}</span>
                </el-form-item>
                <el-form-item label="Close Time: ">
                  <span>{{ props.row.closed_at }}</span>
                </el-form-item>
                <el-form-item label="Comment: ">
                  <span>{{ props.row.comment }}</span>
                </el-form-item>
                <el-form-item label="Body: ">
                  <span>{{ props.row.body }}</span>
                </el-form-item>
                <el-form-item label="Owner_Repo: ">
                  <span>{{ props.row.owner_repo }}</span>
                </el-form-item>
                <el-form-item label="Primary Key: ">
                  <span>{{ props.row.owner_repo }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="Id" prop="id" style="font-size: 15px"></el-table-column>
          <el-table-column label="Title" prop="title"></el-table-column>
          <el-table-column label="State" prop="state"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script>

import axios from 'axios'

export default {
  name: 'IssuePage',
  data () {
    return {
      activeName: '1',
      open: 0,
      close: 0,
      input: '',
      expands: [],
      issues: [
        {
          closed_at: '2022-12-8',
          comment: '2',
          created_at: '2022-11-5',
          description: '所以今天中午吃什么呢?',
          id: 1,
          number: 1500,
          owner_repo: 'hhh',
          pri_key: 'hhh2',
          state: 'closed',
          title: '中午吃饭了吗?',
          update_at: '2022-12-5',
          user_login: 'bbb'
        }
      ],
      issuesManage: [
        {
          average: 0,
          difference: 0,
          variance: 0
        }
      ]
    }
  },
  methods: {
    search () {
      this.issues = []
      let url = `http://localhost:8181/repo_Info/issue/Search_issue_by_keyword?owner_repo=${this.owner}_${this.repo}&keyword=${this.input}`
      axios.get(url).then(res => {
        console.log(res.data)
        if (res.data.length > 12) {
          for (let i = 0; i < 12; i++) {
            this.issues.push(res.data[i])
          }
        } else {
          this.issues = res.data
        }
      })
    },
    setChart (openTmp, closeTmp) {
      let chart = this.$echarts.init(document.getElementById('chart'))
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
            name: 'open and close',
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
                fontSize: 20,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {value: 0, name: 'open'},
              {value: 0, name: 'close'}
            ]
          }
        ]
      }
      if (openTmp !== 0 && closeTmp !== 0) {
        option.series[0].data[0].value = openTmp
        option.series[0].data[1].value = closeTmp
      } else {
        option.series[0].data = [
          {value: 0, name: 'No data'}
        ]
      }
      chart.setOption(option)
    },
    clickRowHandle (row, column, event) {
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id)
      } else {
        this.expands.push(row.id)
      }
    },
    readData () {
      let openTmp = 0
      let closeTmp = 0
      let url = `http://localhost:8181/repo_Info/issue/Get_issue_number_open?owner_repo=${this.owner}_${this.repo}`
      axios.get(url).then(res => {
        openTmp = res.data
        url = `http://localhost:8181/repo_Info/issue/Get_issue_number_closed?owner_repo=${this.owner}_${this.repo}`
        axios.get(url).then(res => {
          closeTmp = res.data
          this.setChart(openTmp, closeTmp)
        })
      })
      url = `http://localhost:8181/repo_Info/issue/Get_issue_time_statistic?owner_repo=${this.owner}_${this.repo}`
      axios.get(url).then(res => {
        this.issuesManage[0].average = (res.data[0] / (24 * 60)).toFixed(1)
        this.issuesManage[0].difference = (res.data[1] / (24 * 60)).toFixed(1)
        this.issuesManage[0].variance = (res.data[2] / (24 * 60) ^ 2).toFixed(1)
        // this.issuesManage[0].average = res.data[0]
        // this.issuesManage[0].difference = res.data[1]
        // this.issuesManage[0].variance = res.data[2]
      })
      url = `http://localhost:8181/repo_Info/issue/Get_issue?owner_repo=${this.owner}_${this.repo}`
      this.issues = []
      axios.get(url).then(res => {
        if (res.data.length > 12) {
          for (let i = 0; i < 12; i++) {
            this.issues.push(res.data[i])
          }
        } else {
          this.issues = res.data
        }
      })
    }
  }
}
</script>

<style scoped>

.Chart {
  height: 200px;
  width: 200px;
}

.IssueTable {
  font-size: 18px;
  font-weight: bold;
  color: #2C8DF4;
  /*color: cornflowerblue;*/
}

.TableExpand .el-form-item {
  margin-left: 60px;
  margin-bottom: 0;
  width: 50%;
}

.TableExpand .el-form-item .span {
  font-size: 50px;
}
</style>
