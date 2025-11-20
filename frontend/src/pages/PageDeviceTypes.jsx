import { useEffect } from 'react';
import DeviceTypes from '../components/deviceTypes/table/DeviceTypes';

const PageDeviceTypes = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DeviceTypes />
  );
};

export default PageDeviceTypes;