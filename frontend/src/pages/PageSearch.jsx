import { useEffect } from 'react';
import DeviceList from '../components/devices/DeviceList';

const PageSearch = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DeviceList />
  );
};

export default PageSearch;