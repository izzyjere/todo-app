import axios from 'axios';

const state = {
  token: null,
  isAuthenticated: false,
  user: null,
  errorMessage: null
};

const mutations = {
  SET_TOKEN(state, token) {
    state.token = token;
    state.isAuthenticated = !!token;
  },
  SET_USER(state, user) {
    state.user = user;
  },
  SET_ERROR_MESSAGE(state, errorMessage) {
    state.errorMessage = errorMessage;
  }
};

const actions = {
  async login({ commit }, credentials) {
    try {
      commit('SET_ERROR_MESSAGE', null);
      const response = await axios.post('auth/login', credentials);
      if (response.data.succeeded) {
        const { token, expiresIn } = response.data.data.tokenData;
        const user = response.data.data.userData;
        commit('SET_TOKEN', token);
        commit('SET_USER', user);
        commit('SET_ERROR_MESSAGE', null);
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(user));
        setTimeout(() => {
          commit('SET_TOKEN', null);
          commit('SET_USER', null);
          commit('SET_ERROR_MESSAGE', null);
          localStorage.removeItem('token');
          localStorage.removeItem('user');
        }, expiresIn * 1000);
      } else {
        commit('SET_ERROR_MESSAGE', response.message);
        throw new Error("Invalid credentials.");
      }

    } catch (error) {
      console.error('Login failed:', error);
      commit('SET_ERROR_MESSAGE', error.message);
      throw error;
    }
  },
  logout({ commit }) {
    commit('SET_TOKEN', null);
    commit('SET_USER', null);
    commit('SET_ERROR_MESSAGE', null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
};

const getters = {
  isAuthenticated: state => state.isAuthenticated,
  user: state => state.user,
  errorMessage: state => state.errorMessage
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
