import { useState } from 'react';
import { LocationsGetService } from '../service/LocationsApiService';
import { LocationTypeMapping } from '../../utils/Constants';

const useLocations = () => {
  const [locationsRefresh, setLocationsRefresh] = useState(false);
  const [locations, setLocations] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const handleLocationsChange = () => setLocationsRefresh(!locationsRefresh);

  const getLocations = async (page = 1, size = 8) => {
    const data = await LocationsGetService.getAll(`?page=${page - 1}&size=${size}`);
    const mappedLocations = (data.items || []).map(location => ({
      ...location,
      type: LocationTypeMapping[location.type] || location.type
    }));
    setLocations(mappedLocations);
    setTotalPages(data.totalPages || 1);
  };

  return {
    locations,
    totalPages,
    getLocations,
    locationsRefresh,
    handleLocationsChange
  };
};

export default useLocations;