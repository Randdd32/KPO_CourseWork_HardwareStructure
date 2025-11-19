import CrudApiService from '../../api/CrudApiService';

export const LocationsGetService = new CrudApiService('/location');

const LocationsApiService = new CrudApiService('/admin/location');

export default LocationsApiService;