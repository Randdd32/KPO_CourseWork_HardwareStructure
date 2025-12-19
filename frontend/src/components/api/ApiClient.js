import axios from 'axios';
import toast from 'react-hot-toast';
import Qs from 'qs';

export const API_URL = '/api/v1';

export class HttpError extends Error {
  constructor(message = '') {
    super(message);
    this.name = 'HttpError';
    Object.setPrototypeOf(this, new.target.prototype);
    toast.error(message, { id: 'HttpError' });
  }
}

function responseHandler(response) {
  const { status, data } = response;

  if ([200, 201, 202, 206].includes(status)) {
    if (data === undefined) {
      throw new HttpError('API Error. No data!');
    }
    return data;
  }

  if (status === 204) {
    return;
  }

  throw new HttpError(`API Error! Invalid status code ${status}!`);
}

function responseErrorHandler(error) {
  if (error === null) {
    throw new Error('Unrecoverable error!! Error is null!');
  }
  const apiError = error.response?.data || {};
  apiError.status = error.response?.status;
  if (apiError?.status !== 401 && apiError?.status !== 403) {
    toast.error(apiError?.message || error.message, { id: 'AxiosError' });
  }
  return Promise.reject(apiError);
}

function addAuthToken(config) {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}

async function responseAuthErrorInterceptor(error) {
  const originalRequest = error.config;
  if (
    error.response &&
    (error.response.status === 401 || error.response.status === 403) &&
    !originalRequest._isRetry
  ) {
    const errorCode = error.response.data?.error;

    if (
      errorCode === 'BAD_CREDENTIALS' ||
      errorCode === 'USER_NOT_FOUND' ||
      errorCode === 'INVALID_OTP'
    ) {
      return Promise.reject(error);
    }

    if (originalRequest.url.endsWith('/refresh-token') || originalRequest.url.endsWith('/refresh')) {
      return Promise.reject(error);
    }

    originalRequest._isRetry = true;
    const response = await axios.put(
      `${API_URL}/auth/refresh-token`,
      {},
      {
        withCredentials: true,
        headers: {
          Accept: 'application/json'
        }
      }
    );
    localStorage.setItem('token', response.data.accessToken);

    if (originalRequest._clientType === 'FileApiClient') {
      return FileApiClient.request(originalRequest);
    } else {
      return ApiClient.request(originalRequest);
    }
  }
  return null;
}

export const ApiClient = axios.create({
  baseURL: API_URL,
  withCredentials: true,
  timeout: 30000,
  headers: {
    Accept: 'application/json'
  },
  paramsSerializer: {
    serialize: (params) => {
      return Qs.stringify(params, { arrayFormat: 'repeat' });
    }
  }
});

export const FileApiClient = axios.create({
  baseURL: API_URL,
  timeout: 30000,
  responseType: 'blob',
  headers: {
    Accept: 'application/octet-stream'
  }
});

FileApiClient.interceptors.request.use((config) => {
  config._clientType = 'FileApiClient';
  return addAuthToken(config);
});

ApiClient.interceptors.request.use((config) => {
  config._clientType = 'ApiClient';
  return addAuthToken(config);
});

ApiClient.interceptors.response.use(
  responseHandler,
  async (error) => {
    try {
      const retryResponse = await responseAuthErrorInterceptor(error);
      if (retryResponse) {
        return retryResponse;
      }
    } catch (refreshError) {
      return responseErrorHandler(refreshError);
    }
    return responseErrorHandler(error);
  }
);

FileApiClient.interceptors.response.use(
  response => response,
  async (error) => {
    try {
      const retryResponse = await responseAuthErrorInterceptor(error);
      if (retryResponse) {
        return retryResponse;
      }
    } catch (refreshError) {
      return responseErrorHandler(refreshError);
    }
    return responseErrorHandler(error);
  }
);