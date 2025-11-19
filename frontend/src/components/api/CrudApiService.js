import { ApiClient } from './ApiClient';

class CrudApiService {
    constructor(url) {
        this.url = url;
    }

    async getAll(expand) {
        return ApiClient.get(`${this.url}${expand || ''}`);
    }

    async getByIds(expand) {
        return ApiClient.get(`${this.url}/ids${expand || ''}`)
    }

    async get(id, expand) {
        return ApiClient.get(`${this.url}/${id}${expand || ''}`);
    }

    async create(body) {
        return ApiClient.post(this.url, body);
    }

    async update(id, body) {
        return ApiClient.put(`${this.url}/${id}`, body);
    }

    async delete(id) {
        return ApiClient.delete(`${this.url}/${id}`);
    }
}

export default CrudApiService;