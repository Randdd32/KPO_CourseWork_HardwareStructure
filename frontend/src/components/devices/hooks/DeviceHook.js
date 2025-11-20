import { useState } from 'react';
import { DevicesGetService } from '../service/DevicesApiService';

const useDevices = () => {
  const [devicesRefresh, setDevicesRefresh] = useState(false);
  const [devices, setDevices] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const handleDevicesChange = () => setDevicesRefresh(!devicesRefresh);

  const getDevices = async (page = 1, size = 8) => {
    const data = await DevicesGetService.getAll(`?page=${page - 1}&size=${size}&sortType=ID_ASC`);
    setDevices(data.items || []);
    setTotalPages(data.totalPages || 1);
  };

  return {
    devices,
    totalPages,
    getDevices,
    devicesRefresh,
    handleDevicesChange
  };
};

export default useDevices;