<template>
  <div class="main">
    <div class="header">
      <h2>This is java2 final project</h2>
    </div>
    <div class="body">
      <section class="Left">
        <h2 style="text-align: center;font-size: 20px">Please Choose a Repo</h2>
        <el-table :data="reports" border style="margin-left: 20px; margin-top: 15px; height: 399px; border-radius: 15px"
                  :header-cell-style="{'background-color': '#1989fa', 'color': '#fff', 'font-size': '22px'}"
                  :row-style="{'background-color':'#ecf5ff', 'font-size': '20px'}">
          <el-table-column prop="name" label="Repositories" style="font-size: 15px"></el-table-column>
        </el-table>
        <div class="Buttons" style="margin-top: 50px; height: 400px">
          <el-button type="primary" @click="componentName='DevPage'" style="width: 200px">Developer Page</el-button>
          <el-button type="primary" @click="componentName='IssuePage'"
                     style="width: 200px; margin-top: 20px; margin-left: 0px">Issues Page
          </el-button>
          <el-button type="primary" @click="componentName='CommitPage'"
                     style="width: 200px; margin-top: 20px; margin-left: 0px">Commits Page
          </el-button>
        </div>
      </section>
      <section class="Right">
        <div class="Box" style="margin-left: 50px">
          <component :is="componentName"></component>
        </div>
        <router-view></router-view>
      </section>
    </div>
  </div>
</template>

<script>

import DevPage from './subpage/DevPage.vue'
import IssuePage from './subpage/IssuePage.vue'
import CommitPage from './subpage/CommitPage.vue'

export default {
  name: 'Main',
  mounted () {
    this.setDev()
    this.setIssue1()
    this.setIssue2()
    this.setCommit()
  },
  data () {
    return {
      componentName: 'DevPage',
      reports: [
        {name: 'hello'},
        {name: 'world'}
      ]
    }
  },
  components: {
    DevPage,
    IssuePage,
    CommitPage
  },
  methods: {
    handelSelect () {
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
