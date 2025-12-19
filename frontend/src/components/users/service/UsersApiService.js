import CrudApiService from '../../api/CrudApiService';

export const UsersGetByIdService = new CrudApiService('/user');

const UsersApiService = new CrudApiService('/admin/user');

export default UsersApiService;