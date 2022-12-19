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
                <el-form-item label="Description: ">
                  <span>{{ props.row.description }}</span>
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
      ]
    }
  },
  methods: {
    clickRowHandle (row, column, event) {
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id)
      } else {
        this.expands.push(row.id)
      }
    },
    readData () {
      let url = `http://localhost:8181/repo_Info/issue/Get_issue_number_open?owner_repo=${this.owner}_${this.repo}`
      axios.get(url).then(res => {
        this.open = res.data
      })
      url = `http://localhost:8181/repo_Info/issue/Get_issue_number_closed?owner_repo=${this.owner}_${this.repo}`
      axios.get(url).then(res => {
        this.close = res.data
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
