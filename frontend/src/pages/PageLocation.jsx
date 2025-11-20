import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import LocationsForm from '../components/locations/form/LocationsForm';

const PageLocation = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <LocationsForm id={id} />
  );
};

export default PageLocation;