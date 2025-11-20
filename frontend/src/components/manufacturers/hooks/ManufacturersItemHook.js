import { useEffect, useState } from 'react';
import { ManufacturersGetService } from '../service/ManufacturersApiService';

const useManufacturerItem = (id) => {
  const emptyItem = {
    id: '',
    name: ''
  };

  const [item, setItem] = useState({ ...emptyItem });

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await ManufacturersGetService.get(itemId);
      setItem(data);
    } else {
      setItem({ ...emptyItem });
    }
  };

  useEffect(() => {
    getItem(id);
  }, [id]);

  return {
    item,
    setItem
  };
};

export default useManufacturerItem;