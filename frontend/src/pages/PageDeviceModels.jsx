import { useEffect } from 'react';
import DeviceModels from '../components/deviceModels/table/DeviceModels';

const PageDeviceModels = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DeviceModels />
  );
};

export default PageDeviceModels;