import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import PositionsForm from '../components/positions/form/PositionsForm';

const PagePosition = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <PositionsForm id={id} />
  );
};

export default PagePosition;