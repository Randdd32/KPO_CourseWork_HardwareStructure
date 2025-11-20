import CrudApiService from '../../api/CrudApiService';

export const ManufacturersGetService = new CrudApiService('/manufacturer');

const ManufacturersApiService = new CrudApiService('/admin/manufacturer');

export default ManufacturersApiService;