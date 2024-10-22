import { createStore } from 'vuex';

export default createStore({
  state: {
    user: null, // Trạng thái lưu thông tin user
    token: null
  },
  mutations: {
    // Mutation để cập nhật thông tin user sau khi đăng nhập thành công
    setUser(state, user) {
      state.user = user;
    },
    setToken(state, token) {
        state.token = token;
    },
    // Mutation để đăng xuất
    logout(state) {
      state.user = null;
      state.token = null;
    }
  },
  //   actions: {
//   // Action login nhận một đối tượng user
//     async login({ commit }, user) {
//         try {
//         // Gửi yêu cầu đăng nhập đến server với toàn bộ đối tượng user
//         const response = await axios.post('/api/login', user);
//         const { user: userData, token } = response.data;

//         // Lưu thông tin người dùng và token vào state
//         commit('setUser', userData);
//         commit('setToken', token);

//         // Lưu token vào localStorage để duy trì đăng nhập giữa các lần tải lại trang
//         localStorage.setItem('token', token);
//         } catch (error) {
//         throw new Error('Thông tin đăng nhập không chính xác.');
//         }
//     }
//  },
  actions: {
    // Action để đăng nhập
    login({ commit }, user) {
      // Sau khi xử lý API thành công, thực hiện commit để cập nhật user
      commit('setUser', user);
    },
    // Action để đăng xuất
    logout({ commit }) {
      commit('logout');
    }
  },
  getters: {
    // Getter để lấy thông tin user
    user(state) {
      return state.user;
    },
    // Getter để kiểm tra user đã đăng nhập hay chưa
    isAuthenticated(state) {
      return !!state.user;
    }
  }
});
