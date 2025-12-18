import { useEffect } from 'react';
import Manufacturers from '../components/manufacturers/table/Manufacturers';

const PageManufacturers = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Manufacturers />
  );
};

export default PageManufacturers;