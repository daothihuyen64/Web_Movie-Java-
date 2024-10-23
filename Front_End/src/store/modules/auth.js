import axios from 'axios'; 

const state = {
  token: localStorage.getItem('token') || '',
  userId: null,
  isAuthenticated: !!localStorage.getItem('token')
};

const getters = {
  token: state => state.token,
  isAuthenticated: state => state.isAuthenticated,
  userId: state => state.userId
};

const actions = {
  // commit là phương thức để gọi mutation (thay đổi trạng thái) trong store
  async login({ commit }, { username, password }) {
    try {
      const response = await axios.post('http://localhost:8080/login/signin', null, {
        params: { username, password }
      });

      // Kiểm tra nếu đăng nhập thành công
      if (response.data.success) {
        const { token, userId } = response.data.data;

        // Lưu token và userId vào localStorage
        localStorage.setItem('token', token);
        localStorage.setItem('userId', userId);

        commit('SET_TOKEN', token);
        commit('SET_USER_ID', userId);
      } else {
        // Nếu không thành công, ném ra lỗi với thông điệp từ phản hồi
        throw new Error(response.data.desc || 'Đăng nhập thất bại');
      }
    } catch (error) {
      throw new Error(error.response ? error.response.data.desc : 'Đăng nhập thất bại');
    }
  },
  logout({ commit }) {
    commit('CLEAR_AUTH');
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
  }
};

const mutations = {
  SET_TOKEN(state, token) {
    state.token = token;
    state.isAuthenticated = true;
  },
  SET_USER_ID(state, userId) {
    state.userId = userId;
  },
  CLEAR_AUTH(state) {
    state.token = '';
    state.userId = null;
    state.isAuthenticated = false;
  }
};

export default {
  state,
  getters,
  actions,
  mutations
};
