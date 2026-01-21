import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../view/LoginView.vue'
const routes = [
  {
    path: '/',
    redirect: '/login' // 默认重定向到登录页
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
