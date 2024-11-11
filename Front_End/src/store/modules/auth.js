import axios from 'axios'; 

const state = {
  nickname: null,
  token: localStorage.getItem('token') || '',
  userId: null,
  isAuthenticated: !!localStorage.getItem('token')
};

const getters = {
  nickname : state => state.nickname,
  token: state => state.token,
  isAuthenticated: state => state.isAuthenticated,
  userId: state => state.userId
};

const actions = {
  async login({ commit }, { username, password }) {
    try {
      const response = await axios.post('http://localhost:8080/login/signin', {
        username,
        password,
      });
      if (response.data.success) {
        const {nickname, token, userId } = response.data.data;

        // Lưu token và userId vào localStorage
        localStorage.setItem('token', token);
        localStorage.setItem('userId', userId);
        localStorage.setItem('nickname', nickname);

        commit('SET_TOKEN', token);
        commit('SET_USER_ID', userId);
        commit('SET_nickname', nickname);
      } else {
        // Nếu không thành công, ném ra lỗi với thông điệp từ phản hồi
        throw new Error(response.data.desc || 'Đăng nhập thất bại');
      }
    } catch (error) {
      throw new Error(error.response.data.desc || 'Đăng nhập thất bại');
    }
  },
  logout({ commit }) {
    commit('CLEAR_AUTH');
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('nickname');
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
  SET_nickname(state, nickname) {
    state.nickname = nickname;
  },
  CLEAR_AUTH(state) {
    state.nickname  = null;
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
