import CrudApiService from '../../api/CrudApiService';

export const DeviceModelsGetService = new CrudApiService('/device-model');

const DeviceModelsApiService = new CrudApiService('/admin/device-model');

export default DeviceModelsApiService;