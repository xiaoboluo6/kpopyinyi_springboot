<template>
  <div class="dashboard-container">
    
    <img :src="require('@/assets/logo1.png')" alt="image" style="width: 650px; height: 200px;">
    <div class="dashboard-text" style="margin-left:180px;">欢迎您！管理员: {{ name }}</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
