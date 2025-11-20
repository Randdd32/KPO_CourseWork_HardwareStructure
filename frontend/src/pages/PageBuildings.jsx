import { useEffect } from 'react';
import Buildings from '../components/buildings/table/Buildings';

const PageBuildings = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Buildings />
  );
};

export default PageBuildings;