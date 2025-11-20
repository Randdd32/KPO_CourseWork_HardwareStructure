import CrudApiService from '../../api/CrudApiService';

export const DepartmentsGetService = new CrudApiService('/department');

const DepartmentsApiService = new CrudApiService('/admin/department');

export default DepartmentsApiService;