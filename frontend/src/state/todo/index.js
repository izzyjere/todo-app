const state = {
  todos: [] 
};

const mutations = {
  SET_TODOS(state, todos) {
    state.todos = todos;
  }  
};

const actions = {
  async fetchTodos({ commit, rootState },searchQuery = "") {
    try {
      const userId = rootState.auth.user.id; 
      let endpoint = `/api/todo/${userId}?search=${searchQuery}`;      
      const response = await this.$axios.get(endpoint);
      commit('SET_TODOS', response.data ?? []);      
    } catch (error) {
      console.error('Error fetching todos:', error);
    }
  },
  async saveTodo({ dispatch }, todoRequest) {
    try {
      const response = await this.$axios.post('/api/todo', todoRequest);
      await dispatch('fetchTodos'); // Refresh todos after saving
      return response.data;
    } catch (error) {
      console.error('Error saving todo:', error);
      throw error;
    }
  },
  async deleteTodo({ dispatch }, id) {
    try {
      await this.$axios.delete(`/api/todo/${id}`);
      await dispatch('fetchTodos'); // Refresh todos after deleting
    } catch (error) {
      console.error('Error deleting todo:', error);
      throw error;
    }
  },
  async completeTodo({ dispatch }, id) {
    try {
      const response = await this.$axios.put(`/api/todo/${id}/complete`);
      await dispatch('fetchTodos'); // Refresh todos after completing
      return response.data;
    } catch (error) {
      console.error('Error completing todo:', error);
      throw error;
    }
  },
};

const getters = {
  todos: state => state.todos  
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
