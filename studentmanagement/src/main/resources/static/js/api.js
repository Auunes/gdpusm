/**
 * 通用API请求处理函数
 * @param {Object} options - 请求配置项
 * @param {string} options.url - 请求URL
 * @param {Object} [options.data={}] - 请求参数
 * @param {string} [options.method='post'] - 请求方法
 * @param {Function} [options.success] - 成功回调函数
 * @param {Function} [options.error] - 错误回调函数
 * @param {boolean} [options.isBlob=false] - 是否返回Blob类型数据
 */
function request(options) {
  // 获取token
  const token = localStorage.getItem('token');
  
  // 默认配置
  const defaultOptions = {
    method: 'post',
    baseURL: '/api',
    headers: {
      'Content-Type': 'application/json',
      'auth': token || ''
    },
    responseType: options.isBlob ? 'blob' : 'json'
  };
  
  // 合并配置
  const config = {
    ...defaultOptions,
    url: options.url,
    method: options.method || 'post',
    data: options.data || {}
  };
  
  // 发送请求
  axios(config).then(response => {
    // Blob类型直接返回
    if (options.isBlob) {
      if (options.success) {
        options.success(response.data);
      }
      return;
    }
    
    const res = response.data;
    
    // 成功
    if (res.error === 0) {
      if (options.success) {
        options.success(res.body);
      }
      return;
    }
    
    // 需要登录
    if (res.error === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('userInfo');
      // 跳转到登录页
      window.location.href = '/login';
      return;
    }
    
    // 系统异常
    if (res.error === 500) {
      console.error('系统异常', res.message);
      if (options.error) {
        options.error(res.message);
      } else {
        // 可以替换为您的提示组件
        alert('系统异常：' + res.message);
      }
      return;
    }
    
    // 业务异常
    if (options.error) {
      options.error(res.message);
    } else {
      // 可以替换为您的提示组件
      alert(res.message);
    }
  }).catch(err => {
    console.error('请求异常', err);
    if (options.error) {
      options.error('网络异常，请稍后再试');
    } else {
      // 可以替换为您的提示组件
      alert('网络异常，请稍后再试');
    }
  });
}

/**
 * 导出API工具
 */
const api = {
  /**
   * 发送POST请求
   * @param {string} url - 请求URL
   * @param {Object} data - 请求参数
   * @param {Function} success - 成功回调函数
   * @param {Function} error - 错误回调函数
   */
  post(url, data, success, error) {
    request({
      url,
      data,
      success,
      error
    });
  },
  
  /**
   * 发送GET请求
   * @param {string} url - 请求URL
   * @param {Object} data - 请求参数
   * @param {Function} success - 成功回调函数
   * @param {Function} error - 错误回调函数
   */
  get(url, data, success, error) {
    request({
      url,
      data,
      method: 'get',
      success,
      error
    });
  },
  
  /**
   * 下载文件
   * @param {string} url - 请求URL
   * @param {Object} data - 请求参数
   * @param {string} fileName - 文件名
   * @param {Function} error - 错误回调函数
   */
  download(url, data, fileName, error) {
    request({
      url,
      data,
      isBlob: true,
      success(blob) {
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = fileName;
        link.click();
        URL.revokeObjectURL(link.href);
      },
      error
    });
  }
}; 