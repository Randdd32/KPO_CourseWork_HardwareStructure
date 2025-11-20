import { useEffect, useState } from 'react';
import PositionsApiService from '../service/PositionsApiService';

const usePositionItem = (id) => {
  const emptyItem = {
    id: '',
    name: '',
    description: ''
  };

  const [item, setItem] = useState({ ...emptyItem });

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await PositionsApiService.get(itemId);
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

export default usePositionItem;