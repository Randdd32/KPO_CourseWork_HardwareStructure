import { useState } from 'react';
import { DeviceModelsGetService } from '../service/DeviceModelsApiService';

const useDeviceModels = () => {
  const [deviceModelsRefresh, setDeviceModelsRefresh] = useState(false);
  const [deviceModels, setDeviceModels] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const handleDeviceModelsChange = () => setDeviceModelsRefresh(!deviceModelsRefresh);

  const getDeviceModels = async (page = 1, size = 8) => {
    const data = await DeviceModelsGetService.getAll(`?page=${page - 1}&size=${size}`);
    setDeviceModels(data.items || []);
    setTotalPages(data.totalPages || 1);
  };

  return {
    deviceModels,
    totalPages,
    getDeviceModels,
    deviceModelsRefresh,
    handleDeviceModelsChange
  };
};

export default useDeviceModels;