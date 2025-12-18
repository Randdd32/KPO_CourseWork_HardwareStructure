import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import DeviceDetails from '../components/devices/DeviceDetails'

const PageDeviceDetails = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DeviceDetails id={id} />
  );
};

export default PageDeviceDetails;