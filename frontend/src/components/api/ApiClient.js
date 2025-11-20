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

  toast.error(apiError?.message || error.message, { id: 'AxiosError' });

  return Promise.reject(apiError);
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

ApiClient.interceptors.response.use(
  responseHandler,
  (error) => responseErrorHandler(error)
);