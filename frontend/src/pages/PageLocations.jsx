import { useEffect } from 'react';
import Locations from '../components/locations/table/Locations';

const PageLocations = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Locations />
  );
};

export default PageLocations;