import { useEffect, useState } from 'react';
import { DepartmentsGetService } from '../service/DepartmentsApiService';

const useDepartmentsItem = (id) => {
  const emptyItem = {
    id: '',
    name: '',
    positions: [],
    positionIds: []
  };

  const [item, setItem] = useState({ ...emptyItem });

  const [initialPositions, setInitialPositions] = useState([]);

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await DepartmentsGetService.get(itemId);
      setItem(data);
      setInitialPositions(
        data.positions?.map(position => ({
          value: position?.id || 0,
          label: position?.name || 'N/A'
        })) || []
      );
    } else {
      setItem({ ...emptyItem });
      setInitialPositions([]);
    }
  };

  useEffect(() => {
    getItem(id);
  }, [id]);

  return {
    item,
    setItem,
    initialPositions
  };
};

export default useDepartmentsItem;