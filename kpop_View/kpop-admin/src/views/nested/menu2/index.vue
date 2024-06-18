<template>
  <div style="padding:30px;">
    <el-alert :closable="false" title="menu 2" />
  </div>
</template>


<script>
export default {
  created() {
    // 打印 baseURL
    console.log('baseURL:', process.env.VUE_APP_BASE_API)
    console.log('baseURL:', typeof(process.env.VUE_APP_BASE_API))

    // 在这里可以执行其他初始化逻辑
  }
}
</script>
