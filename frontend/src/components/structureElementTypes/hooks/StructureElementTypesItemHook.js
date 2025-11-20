import { useEffect, useState } from 'react';
import StructureElementTypesApiService from '../service/StructureElementTypesApiService';

const useStructureElementTypeItem = (id) => {
  const emptyItem = {
    id: '',
    name: '',
  };

  const [item, setItem] = useState({ ...emptyItem });

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await StructureElementTypesApiService.get(itemId);
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

export default useStructureElementTypeItem;