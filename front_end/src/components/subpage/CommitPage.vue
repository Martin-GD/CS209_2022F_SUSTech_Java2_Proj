<template>
  <el-container>
    <el-header style="display: flex; margin-left: 15px">
      <h2>Release Count: {{ this.totalCount }}</h2>
    </el-header>
    <el-main style="margin-top: -30px">

    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'CommitPage',
  data () {
    return {
      totalCount: 5,
      releases: [
        {
          Id: 1,
          commitCount: 20
        },
        {
          Id: 2,
          commitCount: 25
        },
        {
          Id: 3,
          commitCount: 30
        },
        {
          Id: 4,
          commitCount: 15
        },
        {
          Id: 5,
          commitCount: 10
        }
      ],
      commits: [
        {
          time: '2022-12-1',
          count: 10
        },
        {
          time: '2022-12-2',
          count: 15
        },
        {
          time: '2022-12-3',
          count: 15
        },
        {
          time: '2022-12-4',
          count: 20
        },
        {
          time: '2022-12-5',
          count: 10
        },
        {
          time: '2022-12-6',
          count: 5
        },
        {
          time: '2022-12-7',
          count: 13
        },
        {
          time: '2022-12-8',
          count: 12
        }
      ]
    }
  },
  mounted () {
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
