import axios from 'axios';
import {makeAutoObservable} from "mobx";
import AuthApiService from '../components/api/AuthApiService'
import { jwtDecode } from 'jwt-decode';
import toast from 'react-hot-toast';
import { API_URL } from '../components/api/ApiClient';

export default class Store {
    user = {};
    userEmail = '';
    isOtpSent = false;
    isAuth = false;
    isLoading = false;

    constructor() {
        makeAutoObservable(this);
        this.authApiService = new AuthApiService();
    }

    setAuth(bool) {
        this.isAuth = bool;
    }

    setUser(user) {
        this.user = user;
    }

    setLoading(bool) {
        this.isLoading = bool;
    }

    setOtpSent(bool) {
        this.isOtpSent = bool;
    }

    setUserEmail(email) {
        this.userEmail = email;
    }

    get isAdmin() {
        return this.user?.role === 'ADMIN' || this.user?.role === 'SUPER_ADMIN';
    }

    get isSuperAdmin() {
        return this.user?.role === 'SUPER_ADMIN';
    }

    get isRegularAdmin() {
        return this.user?.role === 'ADMIN';
    }

    get isRegularUser() {
        return this.user?.role === 'USER';
    } 
    
    resetAuthState() {
        this.setUser({});
        this.setUserEmail('');
        this.setOtpSent(false);
        this.setAuth(false);
        localStorage.removeItem('token');
    }

    async login(email, password) {
        try {
            await this.authApiService.login({ email, password });
            this.setUserEmail(email);
            this.setOtpSent(true);
        } catch (e) {
            if (e?.status === 401 && (e?.error == 'BAD_CREDENTIALS' || e?.error == 'USER_NOT_FOUND')) {
                toast.error('Были введены неверные данные!  Пожалуйста проверьте свою электронную почту или пароль');
            } else {
                toast.error('Произошла ошибка: ' + e?.error + ' - ' + e?.message);
            }
            this.setOtpSent(false);
            this.setUserEmail('');
        }
    }

    async sendOtp(email) {
        try {
            await this.authApiService.sendOtp({ email });
            toast.success('Проверьте вашу электронную почту');
        } catch (e) {
            toast.error('Ошибка при отправке OTP: ' + e?.error + ' - ' + e?.message);
        }
    }

    async invalidateOtp(email) {
        try {
            await this.authApiService.invalidateOtp({ email });
        } catch (e) {
            toast.error('Ошибка при сбросе OTP: ' + e?.error + ' - ' + e?.message);
        }
    }

    async verifyOtp(email, otp) {
        try {
            const otpInt = parseInt(otp, 10);
            if (isNaN(otpInt)) {
                toast.error('Некорректный одноразовый пароль');
                return;
            }

            const data = await this.authApiService.verifyOtp({ email, oneTimePassword: otpInt });

            if (!data.accessToken) {
                throw new Error('No access token received!');
            }

            localStorage.setItem('token', data.accessToken);
            const user = this.decodeUserFromToken(data.accessToken);

            this.setUser(user);
            this.setAuth(true);
            this.setOtpSent(false);
            this.setUserEmail('');
        } catch (e) {
            if (e?.status === 401 && e?.error === 'INVALID_OTP') {
              toast.error('Неверный одноразовый пароль. Попробуйте ещё раз');
            } else {
                toast.error('Произошла ошибка: ' + e?.error + ' - ' + e?.message);
            }
        }
    }

    async logout() {
    try {
        await this.authApiService.logout();
        localStorage.removeItem('token');
        this.setAuth(false);
        this.setUser({});
    } catch (e) {
        toast.error('Произошла ошибка: ' + e?.error + ' - ' + e?.message);
        console.error(e);
    }
}

    async checkAuth() {
      this.setLoading(true);
      try {
        const data = await axios.put(
            `${API_URL}/auth/refresh-token`,
            null,
            {
                withCredentials: true,
                headers: {
                Accept: 'application/json'
                }
            }
        );

        localStorage.setItem('token', data.data.accessToken);
        const user = this.decodeUserFromToken(data.data.accessToken);

        this.setUser(user);
        this.setAuth(true);
      } catch (e) {
        console.error(e);
        this.setAuth(false);
        this.setUser({});
      } finally {
        this.setLoading(false);
      }
    }

    decodeUserFromToken(token) {
        try {
            const decoded = jwtDecode(token);
            return {
                id: decoded.user_id,
                email: decoded.sub,
                role: decoded.role
            };
        } catch (e) {
            toast.error('Произошла ошибка: ' + e?.error + ' - ' + e?.message);
            return {};
        }
    }
}