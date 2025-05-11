const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8082,
    proxy: {
      '/api': {
        target: 'http://192.168.1.112:8080',
        ws: true
      }
    } 
  },
  lintOnSave: false
})
