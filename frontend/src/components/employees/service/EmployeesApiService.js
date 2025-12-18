import CrudApiService from '../../api/CrudApiService';

export const EmployeesGetService = new CrudApiService('/employee');

const EmployeesApiService = new CrudApiService('/admin/employee');

export default EmployeesApiService;