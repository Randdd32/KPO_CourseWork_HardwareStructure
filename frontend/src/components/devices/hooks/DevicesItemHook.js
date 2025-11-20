import { useEffect, useState } from 'react';
import { DevicesGetService } from '../service/DevicesApiService';

const useDeviceItem = (id) => {
  const emptyItem = {
    id: '',
    serialNumber: '',
    purchaseDate: '',
    warrantyExpiryDate: '',
    price: '0',
    isWorking: true,
    furtherInformation: '',
    modelId: '',
    modelName: '',
    locationId: '',
    locationName: '',
    employeeId: '',
    employeeInfo: '',
    departmentName: '',
    typeName: '',
    manufacturerName: '',
    buildingName: ''
  };

  const [item, setItem] = useState({ ...emptyItem });
  const [initialModel, setInitialModel] = useState(null);
  const [initialLocation, setInitialLocation] = useState(null);
  const [initialEmployee, setInitialEmployee] = useState(null);

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await DevicesGetService.get(itemId);
      setItem(data);
      setInitialModel({ value: data.modelId, label: data.modelName });
      setInitialLocation({ value: data.locationId || 0, label: data.locationName || 'N/A' });
      setInitialEmployee({ value: data.employeeId || 0, label: data.employeeFullName || 'N/A' });
    } else {
      setItem({ ...emptyItem });
      setInitialModel(null);
      setInitialLocation(null);
      setInitialEmployee(null);
    }
  };

  useEffect(() => {
    getItem(id);
  }, [id]);

  return {
    item,
    setItem,
    initialModel,
    initialLocation,
    initialEmployee
  };
};

export default useDeviceItem;