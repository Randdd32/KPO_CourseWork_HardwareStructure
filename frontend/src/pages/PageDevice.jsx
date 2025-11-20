import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import DevicesForm from '../components/devices/form/DevicesForm';

const PageDevice = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DevicesForm id={id} />
  );
};

export default PageDevice;