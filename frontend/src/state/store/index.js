import { createStore } from 'vuex';
import auth from '../auth';
import axiosPlugin from '@/plugins/axiosPlugin';

export default createStore({
  modules: {
    auth
  },
  plugins: [axiosPlugin]
});