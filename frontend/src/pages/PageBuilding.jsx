import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import BuildingsForm from '../components/buildings/form/BuildingsForm';

const PageBuilding = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <BuildingsForm id={id} />
  );
};

export default PageBuilding;