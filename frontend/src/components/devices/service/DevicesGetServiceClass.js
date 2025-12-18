import { ApiClient } from '../../api/ApiClient';
import CrudApiService from '../../api/CrudApiService';

class DevicesGetServiceClass extends CrudApiService {
    constructor() {
        super('/device');
    }

    async getAllByFilters(params) {
      return ApiClient.get(`${this.url}`, { params });
    }
}

const DevicesGetService = new DevicesGetServiceClass();

export default DevicesGetService;