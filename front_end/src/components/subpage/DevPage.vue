<template>
  <el-container>
    <el-header style="display: flex;">
<!--      <h2>Developers Count: {{ this.totalCount }}</h2>-->
<!--      <el-tag style="font-size: 38px; height: 40px; margin-left: 85px">Developers Count: {{ this.totalCount }}</el-tag>-->
      <span style="font-size: 35px; font-weight: bold; background-color: #a0cfff; border-radius: 15px; margin-left: 85px; width: 400px; height: 40px">Developers Count: {{ this.totalCount }}</span>
    </el-header>
    <div style="display: flex">
      <item-page style="background-color: #d9ecff;border: aliceblue 10px;border-radius: 20px; margin-left: 100px">
        <el-container>
          <el-header style="border-radius: 15px; background-color: #a0cfff; height: 40px;">
            <h2 style="text-align: center;font-size: 20px; margin-top: 7px">Owner Information</h2>
          </el-header>
          <el-main style="margin-top: 30px">
            <el-container>
              <el-aside width="150px">
                <el-avatar :size="150" :src="imageBaseUrl+owner" style="left: 10px"/>
              </el-aside>
              <el-main style="padding-left: 100px; margin-top: -20px">
                <h2 style="display: flex;">name: {{ owner }}</h2>
                <h2 style="display: flex; margin-top: 10px">repos: {{ repos }}</h2>
                <h2 style="display: flex; margin-top: 10px">follower: {{ followers }}</h2>
                <h2 style="display: flex; margin-top: 10px">following: {{ following }}</h2>
              </el-main>
            </el-container>
          </el-main>
        </el-container>
      </item-page>
      <item-page style="background-color: white;border: aliceblue 10px;border-radius: 20px; margin-left: 100px">
        <ItemActive ref="active"></ItemActive>
      </item-page>
    </div>
    <el-main style="margin-top: 40px">
      <el-collapse v-model="activeName" accordion style="width: 1380px; margin-left: 50px;">
        <el-collapse-item v-for="(item, index) in developers" :name="index" :key="index"
                          @click.native="fillDetail(item)">
          <template slot="title" style="text-align: center">
            <span style="margin-left:650px;font-weight:bold;font-size:20px;color:#2C8DF4;">{{ item.login }}</span>
          </template>
          <el-container>
            <el-aside class="Avatar">
              <img :src="imageUrl" style="width: 80px; border-radius: 50%; margin-top: 30px">
            </el-aside>
            <el-main class="Detail">
              <el-table :data="singleDev" border style="border-radius: 15px"
                        :header-cell-style="{'background-color': '#1989fa', 'color': '#fff', 'font-size': '15px'}"
                        :row-style="{'background-color':'#ecf5ff', 'font-size': '13x'}">
                <el-table-column prop="login" label="UserName" style="font-size: 15px"></el-table-column>
                <el-table-column prop="id" label="ID" style="font-size: 15px"></el-table-column>
                <el-table-column prop="contributions" label="Contributions" style="font-size: 15px"></el-table-column>
                <el-table-column prop="type" label="Type" style="font-size: 15px"></el-table-column>
              </el-table>
            </el-main>
          </el-container>
        </el-collapse-item>
      </el-collapse>
    </el-main>
  </el-container>
</template>

<script>

import axios from 'axios'

import ItemPage from './ItemPage.vue'
import ItemActive from './ItemActive.vue'

export default {
  name: 'DevPage',
  components: {ItemActive, ItemPage},
  data () {
    return {
      repo: '',
      owner: '',
      repos: 0,
      followers: 0,
      following: 0,
      activeName: '1',
      imageBaseUrl: 'https://avatars.githubusercontent.com/',
      imageUrl: '',
      totalCount: 10,
      developers: [
        {
          login: 'acbgo',
          id: '12345',
          contributions: 50,
          type: 'good'
        }
      ],
      singleDev: []
    }
  },
  methods: {
    fillDetail (item) {
      this.singleDev = []
      this.singleDev.push(item)
      this.imageUrl = this.imageBaseUrl + item.login
    },
    readData () {
      this.developers = []
      let url = `http://localhost:8181/repo_Info/developer/Get_developer_top5?owner_repo=${this.owner}_${this.repo}`
      axios.get(url).then(res => {
        for (let i = 0; i < res.data.length; i++) {
          let tmpData = res.data[i]
          let tmp = {
            login: '',
            id: '',
            contributions: 50,
            type: ''
          }
          tmp.login = tmpData.login
          tmp.id = tmpData.id
          tmp.contributions = tmpData.contributions
          tmp.type = tmpData.type
          this.developers.push(tmp)
        }
      })
      url = `http://localhost:8181/repo_Info/developer/Get_developer_number?owner_repo=${this.owner}_${this.repo}`
      axios.get(url).then(res => {
        this.totalCount = res.data
      })
      url = `http://localhost:8181/repo_Info/developer/Get_developer_top5?owner_repo=${this.owner}_${this.repo}`
      console.log(url)
      axios.get(url).then(res => {
        let active = this.$refs.active
        active.yData = []
        active.xData = []
        for (let i = 0; i < res.data.length; i++) {
          let tmpData = res.data[i]
          active.yData.push(tmpData.login)
          active.xData.push(tmpData.contributions)
        }
        active.setActive()
      })
      url = `http://localhost:8181/GetAllRepo`
      axios.get(url).then(res => {
        for (let i = 0; i < res.data.length; i++) {
          let temp = res.data[i]
          if (temp.repo === this.repo) {
            this.repos = temp.public_repos
            this.followers = temp.owner_followers
            this.following = temp.owner_following
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.Avatar {
  margin-left: -50px;
}

.Detail {
  margin-left: -70px;
}

/deep/ .el-collapse-item__arrow.is-active {
  transform: rotate(-90deg);;
}

/deep/ .el-collapse-item__arrow, .el-tabs__nav {
  transform: rotate(90deg);
}
</style>
