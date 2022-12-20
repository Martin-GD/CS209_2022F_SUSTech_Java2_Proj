<template>
  <div class="main">
    <div class="header">
      <span style="font-size: 40px; font-weight: bold; border-radius: 15px;">This is java2 final project</span>
      <!--      <h2>This is java2 final project</h2>-->
    </div>
    <div class="body">
      <section class="Left">
        <h2 style="text-align: center;font-size: 20px">Please Choose a Repo</h2>
        <el-table :data="owner_repo" border
                  style="margin-left: 20px; margin-top: 15px; height: 399px; border-radius: 15px"
                  :header-cell-style="{'background-color': '#1989fa', 'color': '#fff', 'font-size': '22px'}"
                  :row-style="{'background-color':'#ecf5ff', 'font-size': '20px'}"
                  @row-click="handleRowClick"
                  ref="repos">
          <el-table-column prop="repo" label="Repositories" style="font-size: 15px"></el-table-column>
        </el-table>
        <div class="Buttons" style="margin-top: 20px; height: 160px">
          <el-button type="primary" @click="devSelect" style="width: 200px">Developer Page</el-button>
          <el-button type="primary" @click="issueSelect"
                     style="width: 200px; margin-top: 20px; margin-left: 0px">Issues Page
          </el-button>
          <el-button type="primary" @click="commitSelect"
                     style="width: 200px; margin-top: 20px; margin-left: 0px">Commits Page
          </el-button>
        </div>
        <div class="Crawler" style="height: 230px; width: 200px; margin-top: 40px; margin-left: 50px">
          <el-input placeholder="user name" v-model="UserName"></el-input>
          <el-input placeholder="repo name" v-model="RepoName" style="margin-top: 10px"></el-input>
          <el-button type="success" @click="crawl" style="width: 180px; margin-top: 20px;">Crawl</el-button>
        </div>
      </section>
      <section class="Right">
        <div class="Box" style="margin-left: 50px">
          <component :is="componentName" ref="subPage"></component>
        </div>
        <router-view></router-view>
      </section>
    </div>
  </div>
</template>

<script>

import axios from 'axios'

import DevPage from './subpage/DevPage.vue'
import IssuePage from './subpage/IssuePage.vue'
import CommitPage from './subpage/CommitPage.vue'

export default {
  name: 'Main',
  mounted () {
    this.readRepo()
  },
  data () {
    return {
      owner_repo: [
        {repo: 'clash_for_windows_pkg', owner: 'Fndroid'},
        {repo: 'hello', owner: 'world'}
      ],
      owner: 'Fndroid',
      repo: 'clash_for_windows_pkg',
      componentName: 'DevPage',
      UserName: '',
      RepoName: ''
    }
  },
  components: {
    DevPage,
    IssuePage,
    CommitPage
  },
  methods: {
    crawl () {
      this.$message({
        message: 'crawling, please wait for the success massage',
        type: 'success'
      })
      let url = `http://localhost:8181/Crawler_RepoOwner_Info?owner=${this.UserName}&repo=${this.RepoName}`
      console.log(url)
      axios.get(url).then(res => {
        this.$message({
          message: `repo ${res.data}`,
          type: 'success'
        })
      })
      url = `http://localhost:8181/repo_Info/commit/Crawler_Insert?owner=${this.UserName}&repo=${this.RepoName}`
      console.log(url)
      axios.get(url).then(res => {
        this.$message({
          message: `commit ${res.data}`,
          type: 'success'
        })
      })
      url = `http://localhost:8181/repo_Info/developer/Crawler_Insert?owner=${this.UserName}&repo=${this.RepoName}`
      console.log(url)
      axios.get(url).then(res => {
        this.$message({
          message: `developer ${res.data}`,
          type: 'success'
        })
      })
      url = `http://localhost:8181/repo_Info/issue/Crawler_Insert?owner=${this.UserName}&repo=${this.RepoName}`
      console.log(url)
      axios.get(url).then(res => {
        this.$message({
          message: `issue ${res.data}`,
          type: 'success'
        })
      })
      url = `http://localhost:8181/repo_Info/release/Crawler_Insert?owner=${this.UserName}&repo=${this.RepoName}`
      console.log(url)
      axios.get(url).then(res => {
        this.$message({
          message: `release ${res.data}`,
          type: 'success'
        })
      })
      this.UserName = ''
      this.RepoName = ''
    },
    devSelect () {
      this.componentName = 'DevPage'
      this.setSubPage()
    },
    issueSelect () {
      this.componentName = 'IssuePage'
      this.setSubPage()
    },
    commitSelect () {
      this.componentName = 'CommitPage'
      this.setSubPage()
    },
    handleRowClick (row) {
      this.repo = row.repo
      this.owner = row.owner
      DevPage.data().repo = this.repo
      DevPage.data().owner = this.owner
      IssuePage.data().repo = this.repo
      IssuePage.data().owner = this.owner
      CommitPage.data().repo = this.repo
      CommitPage.data().owner = this.owner
      this.setSubPage()
    },
    readRepo () {
      this.owner_repo = []
      this.owner = ''
      this.repo = ''
      axios.get('http://localhost:8181/GetAllRepo').then(res => {
        for (let i = 0; i < res.data.length; i++) {
          let tmp = {repo: '', owner: ''}
          tmp.repo = res.data[i].repo
          tmp.owner = res.data[i].owner
          this.owner_repo.push(tmp)
        }
        this.repo = this.owner_repo[0].repo
        this.owner = this.owner_repo[0].owner
        this.setSubPage()
      })
    },
    setSubPage () {
      this.$refs.subPage.repo = this.repo
      this.$refs.subPage.owner = this.owner
      this.$refs.subPage.readData()
    }
  }
}
</script>

<style scoped>
.main {
  background: url("../assets/background.jpg") center;
}

.header {
  padding-top: 18px;
  height: 35px;
  font-size: 20px;
}

.body {
  display: flex;
  padding-top: 50px;
}

.Left {
  width: 300px;
}

.Right {
  width: 1620px;
}

</style>
