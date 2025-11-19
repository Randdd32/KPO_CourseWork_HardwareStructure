import { useEffect, useState } from 'react';
import { BuildingsGetService } from '../service/BuildingsApiService';

const useBuildingsItem = (id) => {
  const emptyItem = {
    id: '',
    name: '',
    address: ''
  }

  const [item, setItem] = useState({ ...emptyItem });

  const getItem = async (itemId = undefined) => {
        if (itemId && itemId > 0) {
            const data = await BuildingsGetService.get(itemId);
            setItem(data);
        } else {
            setItem({ ...emptyItem });
        }
    };

    useEffect(() => {
        getItem(id);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [id]);

    return {
        item,
        setItem
    };
}

export default useBuildingsItem;