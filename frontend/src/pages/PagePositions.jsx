import { useEffect } from 'react';
import Positions from '../components/positions/table/Positions';

const PagePositions = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Positions />
  );
};

export default PagePositions;