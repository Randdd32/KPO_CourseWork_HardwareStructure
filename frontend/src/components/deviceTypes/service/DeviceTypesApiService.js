import CrudApiService from '../../api/CrudApiService';

export const DeviceTypesGetService = new CrudApiService('/device-type');

const DeviceTypesApiService = new CrudApiService('/admin/device-type');

export default DeviceTypesApiService;