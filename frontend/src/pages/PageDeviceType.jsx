import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import DeviceTypesForm from '../components/deviceTypes/form/DeviceTypesForm';

const PageDeviceType = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DeviceTypesForm id={id} />
  );
};

export default PageDeviceType;