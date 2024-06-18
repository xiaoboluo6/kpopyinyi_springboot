<template>
  <div>
    <form id="uploadForm" enctype="multipart/form-data" class="upload-form">
      <div class="form-group">
        <label for="file1" class="file-label">原曲歌词文件lrc:</label>
        <input type="file" id="file1" name="file1" class="file-input">
      </div>
      <div class="form-group">
        <label for="file2" class="file-label">Roma文件的lrc:</label>
        <input type="file" id="file2" name="file2" class="file-input">
      </div>
      <div class="button-group">
        <button type="button" @click="getTransWithTime" class="btn btn-primary">获取音译文本 带时间戳</button>
        <button type="button" @click="getTransWithoutTime" class="btn btn-secondary">获取音译文本 不带时间戳</button>
      </div>
    </form>

    <div style="margin-left:auto;margin-right: auto;max-width: 1000px;">
      <section class="transliteration-body" style="white-space: pre-line;">
        <button @click="copyTransContent" v-show="transContent !== ''">一键复制</button>
        <div v-html="transContent"></div>
      </section>
    </div>

  </div>
</template>

<script>
import makeTransApi from '@/api/kpop/makeTrans'

export default {
  data() {
    return {
      formData: new FormData(), // 创建一个 FormData 对象，用于存储原曲和roma文件数据
      transContent: ""
    }
  },

  methods: {
    // 复制音译文本内容到剪贴板
    copyTransContent() {
      // 创建一个临时textarea元素
      const textarea = document.createElement('textarea');
      textarea.value = this.transContent; // 设置textarea的值为音译文本内容
      textarea.setAttribute('readonly', ''); // 设置textarea为只读
      textarea.style.position = 'absolute'; // 设置textarea为绝对定位
      textarea.style.left = '-9999px'; // 将textarea移出可见范围
      document.body.appendChild(textarea); // 将textarea添加到DOM中

      // 选中textarea中的文本
      textarea.select();
      textarea.setSelectionRange(0, textarea.value.length);

      // 执行复制命令
      document.execCommand('copy');

      // 移除textarea元素
      document.body.removeChild(textarea);

      // 提示用户复制成功
      alert('音译文本已复制到剪贴板！');
    },


    // 清空表单数据
    clearFormData() {
      this.formData = new FormData();
    },

    // 上传文件并调用后端接口获取带时间戳的音译文本
    getTransWithTime() {
      this.clearFormData(); // 清空表单数据
      const file1 = document.getElementById('file1').files[0];
      const file2 = document.getElementById('file2').files[0];

      // 检查文件是否为空
      if (!file1 || !file2) {
        alert("Please select both files.");
        return;
      }

      // 将文件添加到 FormData 对象中
      this.formData.append('file1', file1);
      this.formData.append('file2', file2);

      // 调用后端接口
      makeTransApi.getTransWithTime(this.formData)
        .then(response => {
          this.transContent = response.data.trans
        })
        .catch(error => {
          // 处理错误
          console.error(error);
        });
    },

    // 上传文件并调用后端接口获取不带时间戳的音译文本
    getTransWithoutTime() {
      const file1 = document.getElementById('file1').files[0];
      const file2 = document.getElementById('file2').files[0];

      // 检查文件是否为空
      if (!file1 || !file2) {
        alert("请选择原曲和Roma文件,不得为空");
        return;
      }

      // 将文件添加到 FormData 对象中
      this.formData.append('file1', file1);
      this.formData.append('file2', file2);

      // 调用后端接口
      makeTransApi.getTransWithoutTime(this.formData)
        .then(response => {
          this.transContent = response.data.trans
        })
        .catch(error => {
          // 处理错误
          console.error(error);
        });
    }
  }
}
</script>

<style scoped>
.upload-form {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
}

.file-label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.file-input {
  display: block;
  width: 100%;
  padding: 10px;
  font-size: 16px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-group {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.btn {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
  margin-right: 10px;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
  border: none;
}

.btn-secondary {
  background-color: #6c757d;
  color: #fff;
  border: none;
}



.transliteration-body {
  overflow: hidden;
  color: black; /* 将颜色改为黑色 */
  font-size: 16px;
  line-height: 30px;
  white-space: normal;
  word-break: break-all;
  margin-top: 20px;
  padding: 0 10px;
  text-align: justify;
}

</style>
