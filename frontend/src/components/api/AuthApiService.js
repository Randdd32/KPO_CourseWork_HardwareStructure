import { ApiClient } from './ApiClient';

class AuthApiService {
  async login(body) {
    return ApiClient.post(`/auth/login`, body);
  }

  async sendOtp(body) {
    return ApiClient.post('/auth/send-otp', body);
  }

  async invalidateOtp(body) {
    return ApiClient.post('/auth/invalidate-otp', body);
  }

  async verifyOtp(body) {
    return ApiClient.post(`/auth/verify-otp`, body);
  }

  async refreshToken() {
    return ApiClient.put(`/auth/refresh-token`);
  }

  async logout() {
    return ApiClient.post(`/auth/logout`);
  }

  async logoutAll(body) {
    return ApiClient.post(`/auth/logout-all`, body);
  }
}

export default AuthApiService;