import CrudApiService from '../../api/CrudApiService';

export const DevicesGetService = new CrudApiService('/device');

const DevicesApiService = new CrudApiService('/admin/device');

export default DevicesApiService;