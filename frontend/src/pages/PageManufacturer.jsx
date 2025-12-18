import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ManufacturersForm from '../components/manufacturers/form/ManufacturersForm';

const PageManufacturer = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <ManufacturersForm id={id} />
  );
};

export default PageManufacturer;