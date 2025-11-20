import { useEffect, useState } from 'react';
import { DeviceTypesGetService } from '../service/DeviceTypesApiService';

const useDeviceTypes = () => {
  const [deviceTypesRefresh, setDeviceTypesRefresh] = useState(false);
  const [deviceTypes, setDeviceTypes] = useState([]);
  const handleDeviceTypesChange = () => setDeviceTypesRefresh(!deviceTypesRefresh);

  const getDeviceTypes = async () => {
    const data = await DeviceTypesGetService.getAll();
    setDeviceTypes(data ?? []);
  };

  useEffect(() => {
    getDeviceTypes();
  }, [deviceTypesRefresh]);

  return {
    deviceTypes,
    setDeviceTypes,
    handleDeviceTypesChange
  };
};

export default useDeviceTypes;