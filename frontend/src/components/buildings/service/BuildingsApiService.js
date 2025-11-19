import CrudApiService from '../../api/CrudApiService';

export const BuildingsGetService = new CrudApiService('/building');

const BuildingsApiService = new CrudApiService('/admin/building');

export default BuildingsApiService;