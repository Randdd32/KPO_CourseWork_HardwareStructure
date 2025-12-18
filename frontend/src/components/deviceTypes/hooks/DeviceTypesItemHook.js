import { useEffect, useState } from 'react';
import { DeviceTypesGetService } from '../service/DeviceTypesApiService';

const useDeviceTypeItem = (id) => {
  const emptyItem = {
    id: '',
    name: '',
  };

  const [item, setItem] = useState({ ...emptyItem });

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await DeviceTypesGetService.get(itemId);
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

export default useDeviceTypeItem;