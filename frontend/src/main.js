import { createApp } from 'vue';
import App from './App.vue';
import { createRouter, createWebHistory } from 'vue-router';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import LoginPage from './components/LoginPage.vue';
import RegisterPage from './components/RegisterPage.vue';
import axios from 'axios';
import store from './state/store';

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginPage }, 
  { path: '/register', component: RegisterPage }, 
];

const router = createRouter({
  history: createWebHistory(),
  routes
});
axios.defaults.baseURL = 'http://localhost:8080/api/';
const app = createApp(App);
app.use(router);
app.use(store);
app.use(ElementPlus);
app.mount('#app');
