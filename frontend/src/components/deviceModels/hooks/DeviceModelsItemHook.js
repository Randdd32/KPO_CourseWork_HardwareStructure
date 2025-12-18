import { useEffect, useState } from 'react';
import { DeviceModelsGetService } from '../service/DeviceModelsApiService';

const useDeviceModelItem = (id) => {
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
    aestheticQualities: '0',
    structureElementsIds: [],
    structureElements: [],
  };

  const [item, setItem] = useState({ ...emptyItem });
  const [initialStructureElements, setInitialStructureElements] = useState([]);
  const [initialManufacturer, setInitialManufacturer] = useState(null);
  const [initialDeviceType, setInitialDeviceType] = useState(null);

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await DeviceModelsGetService.get(itemId);
      setItem(data);
      setInitialStructureElements(
        data.structureElements?.map((se) => ({
          structureElementId: se?.structureElement?.id || 0,
          count: se?.count || 1,
          structureElement: { name: se?.structureElement?.name || 'N/A' }
        })) || []
      );
      setInitialDeviceType({
          value: data.typeId,
          label: data.typeName
        }
      )
      setInitialManufacturer({
          value: data.manufacturerId,
          label: data.manufacturerName
        }
      )
    } else {
      setItem({ ...emptyItem });
      setInitialStructureElements([]);
      setInitialManufacturer(null);
      setInitialDeviceType(null);
    }
  };

  useEffect(() => {
    getItem(id);
  }, [id]);

  return {
    item,
    setItem,
    initialManufacturer,
    initialDeviceType,
    initialStructureElements
  };
};

export default useDeviceModelItem;