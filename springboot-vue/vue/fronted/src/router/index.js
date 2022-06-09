import Vue from 'vue'
import Router from "vue-router";
import HelloWorld from "../components/HelloWorld";

Vue.use(Router)

const routes = [
  {
    path: '/',
    name: 'HelloWorld',
    redirect: 'HelloWorld',
  },
  {
    path: '/list',
    name: 'BoardList',
    component: () => import('../components/views/board/ListView'),
  },
  {
    path: '/write',
    name: 'BoardWrite',
    component: () => import('../components/views/board/WriteView.vue'),
  },
  {
    path: '/detail',
    name: 'BoardDetail',
    component: () => import('../components/views/board/DetailView.vue'),
  },
  {
    path: '/edit',
    name: 'BoardEdit',
    component: () => import('../components/views/board/EditView.vue'),
  },
]

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

export default router
