<template>
  <el-container>
    <el-header style="display: flex; margin-left: 20px">
      <h2>Developers Count: {{ this.totalCount }}</h2>
    </el-header>
    <el-main style="margin-top: -40px">
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item v-for="(item, index) in developers" :name="index" :key="index" @click.native="fillDetail(item)">
          <template slot="title" style="text-align: center">
            <span style="margin-left:720px;font-weight:bold;font-size:20px;color:#2C8DF4;">{{ item.login }}</span>
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
                <el-table-column prop="ownerRepo" label="Repo" style="font-size: 15px"></el-table-column>
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
export default {
  name: 'DevPage',
  data () {
    return {
      activeName: '1',
      imageBaseUrl: 'https://avatars.githubusercontent.com/',
      imageUrl: 'https://avatars.githubusercontent.com/',
      totalCount: 10,
      developers: [
        {
          ownerRepo: 'project',
          login: 'acbgo',
          id: '12345',
          contributions: 50,
          type: 'good'
        },
        {
          ownerRepo: 'hello world',
          login: 'Martin-GD',
          id: '0987',
          contributions: 40,
          type: 'bad'
        }
      ],
      singleDev: []
    }
  },
  mounted () {
  },
  methods: {
    fillDetail (item) {
      this.singleDev = []
      this.singleDev.push(item)
      this.imageUrl = this.imageBaseUrl + item.login
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
</style>
