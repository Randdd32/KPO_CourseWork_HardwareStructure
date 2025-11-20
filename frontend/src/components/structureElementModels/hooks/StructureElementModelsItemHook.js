import { useEffect, useState } from 'react';
import StructureElementModelsApiService from '../service/StructureElementModelsApiService';

const useStructureElementModelItem = (id) => {
  const emptyItem = {
    id: '',
    name: '',
    description: '',
    typeId: '',
    typeName: '',
    manufacturerId: '',
    manufacturerName: '',
    workEfficiency: '0',
    reliability: '0',
    energyEfficiency: '0',
    userFriendliness: '0',
    durability: '0',
    aestheticQualities: '0'
  };

  const [item, setItem] = useState({ ...emptyItem });
  const [initialManufacturer, setInitialManufacturer] = useState(null);
  const [initialType, setInitialType] = useState(null);

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await StructureElementModelsApiService.get(itemId);
      setItem(data);
      setInitialType({
        value: data.typeId,
        label: data.typeName
      });
      setInitialManufacturer({
        value: data.manufacturerId,
        label: data.manufacturerName
      });
    } else {
      setItem({ ...emptyItem });
      setInitialManufacturer(null);
      setInitialType(null);
    }
  };

  useEffect(() => {
    getItem(id);
  }, [id]);

  return {
    item,
    setItem,
    initialManufacturer,
    initialType
  };
};

export default useStructureElementModelItem;