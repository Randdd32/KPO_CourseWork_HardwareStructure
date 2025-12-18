import { useEffect, useState } from 'react';
import { ManufacturersGetService } from '../service/ManufacturersApiService';

const useManufacturers = () => {
  const [manufacturersRefresh, setManufacturersRefresh] = useState(false);
  const [manufacturers, setManufacturers] = useState([]);
  const handleManufacturersChange = () => setManufacturersRefresh(!manufacturersRefresh);

  const getManufacturers = async () => {
    const data = await ManufacturersGetService.getAll();
    setManufacturers(data ?? []);
  };

  useEffect(() => {
    getManufacturers();
  }, [manufacturersRefresh]);

  return {
    manufacturers,
    setManufacturers,
    handleManufacturersChange
  };
};

export default useManufacturers;