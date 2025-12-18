import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import DeviceModelsForm from '../components/deviceModels/form/DeviceModelsForm';

const PageDeviceModel = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DeviceModelsForm id={id} />
  );
};

export default PageDeviceModel;