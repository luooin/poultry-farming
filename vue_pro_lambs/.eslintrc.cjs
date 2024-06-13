/* eslint-env node */
module.exports = {
  root: true,
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended'
  ],
  parserOptions: {
    ecmaVersion: 'latest'
  },
  /* 忽略命名规则 */
  "rules":{
    "vue/multi-word-component-names":"off"
  }
}
