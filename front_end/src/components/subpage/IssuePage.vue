<template>
  <div class="container">
    <el-container>
      <el-header style="display: flex; margin-left: 15px">
        <h2>Total issues count: {{ open + close }}, with {{ open }} opening and {{ close }} closed</h2>
      </el-header>
      <el-main style="margin-top: -30px">
        <h2 style="display: flex; margin-left: 20px">Issue Details</h2>
        <el-table
          :data="issues"
          class="IssueTable"
          row-key="id"
          :expand-row-keys="expands"
          @row-click="clickRowHandle"
          style="width: 100%; border-radius: 15px; margin-top: 10px">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="TableExpand">
                <el-form-item label="Id: ">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
                <el-form-item label="Owner: ">
                  <span>{{ props.row.owner }}</span>
                </el-form-item>
                <el-form-item label="Title: ">
                  <span>{{ props.row.title }}</span>
                </el-form-item>
                <el-form-item label="User: ">
                  <span>{{ props.row.user }}</span>
                </el-form-item>
                <el-form-item label="State: ">
                  <span>{{ props.row.state }}</span>
                </el-form-item>
                <el-form-item label="Create Time: ">
                  <span>{{ props.row.createAt }}</span>
                </el-form-item>
                <el-form-item label="Last Update Time: ">
                  <span>{{ props.row.lastUpdateAt }}</span>
                </el-form-item>
                <el-form-item label="Close Time: ">
                  <span>{{ props.row.closedAt }}</span>
                </el-form-item>
                <el-form-item label="Comment: ">
                  <span>{{ props.row.comment }}</span>
                </el-form-item>
                <el-form-item label="Description: ">
                  <span>{{ props.row.description }}</span>
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
      open: 20,
      close: 7,
      expands: [],
      issues: [
        {
          owner: 'aaa',
          id: 1,
          title: '中午吃饭了吗?',
          user: 'bbb',
          state: 'close',
          createAt: '2022-11-5',
          lastUpdateAt: '2022-12-5',
          closedAt: '2022-12-8',
          comment: '今天中午吃饭了吗?',
          description: '所以今天中午吃什么呢?'
        },
        {
          owner: 'bbb',
          id: 2,
          title: '晚上吃饭了吗?',
          user: 'bbb',
          state: 'close',
          createAt: '2022-11-5',
          lastUpdateAt: '2022-12-5',
          closedAt: '2022-12-8',
          comment: '今天晚上吃饭了吗?',
          description: '所以今天晚上吃什么呢?'
        }
      ]
    }
  },
  mounted () {
    this.getIssue()
  },
  methods: {
    clickRowHandle (row, column, event) {
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id)
      } else {
        this.expands.push(row.id)
      }
    },
    getIssue () {
      // const _this = this
      axios.get('http://10.26.142.228:8181/repo_info/').then(res => {
        console.log(res.data)
        // _this.issues = res.data
      })
    }
  }
}
</script>

<style scoped>

.IssueTable {
  font-size: 18px;
  font-weight: bold;
  color: #2C8DF4;
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
