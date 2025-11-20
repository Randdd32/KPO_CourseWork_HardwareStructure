import { useEffect } from 'react';
import Devices from '../components/devices/table/Devices';

const PageDevices = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Devices />
  );
};

export default PageDevices;