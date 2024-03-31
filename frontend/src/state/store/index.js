import { createStore } from 'vuex';
import auth from '../auth';
import todo from '../todo';
import axios from 'axios';

const axiosInstance = axios.create();

axiosInstance.interceptors.request.use(
  (config) => {
    const token = auth.state.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      config.baseURL = "http://localhost:8080/";
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
const axiosPlugin = (store) => {
  store.$axios = axiosInstance;
};
export default createStore({
  modules: {
    auth,
    todo
  },
  plugins: [axiosPlugin]
});