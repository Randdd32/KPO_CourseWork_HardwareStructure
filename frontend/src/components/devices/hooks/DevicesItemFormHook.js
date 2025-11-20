import { useState } from 'react';
import toast from 'react-hot-toast';
import useDeviceItem from './DevicesItemHook';
import DevicesApiService from '../service/DevicesApiService';
import { toIsoString } from '../../utils/Formatter';

const useDeviceItemForm = (id, devicesChangeHandle) => {
  const {
    item,
    setItem,
    initialModel,
    initialLocation,
    initialEmployee
  } = useDeviceItem(id);

  const [validated, setValidated] = useState(false);
  const [dateError, setDateError] = useState(false);

  const resetValidity = () => {
    setValidated(false);
    setDateError(false);
  }

  const getDeviceObject = ({
    serialNumber,
    purchaseDate,
    warrantyExpiryDate,
    price,
    isWorking,
    furtherInformation,
    modelId,
    locationId,
    employeeId
  }) => ({
    serialNumber,
    purchaseDate: toIsoString(purchaseDate),
    warrantyExpiryDate: toIsoString(warrantyExpiryDate),
    price,
    isWorking,
    furtherInformation,
    modelId,
    locationId,
    employeeId
  });

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;
    setItem({
      ...item,
      [name]: type === 'checkbox' ? checked : value
    });
  };

  const handleModelChange = (newModel) => {
    setItem({
      ...item,
      modelId: newModel
    });
  };

  const handleLocationChange = (newLocation) => {
    setItem({
      ...item,
      locationId: newLocation
    });
  };

  const handleEmployeeChange = (newEmployee) => {
    setItem({
      ...item,
      employeeId: newEmployee
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();

    const body = getDeviceObject(item);

    let customValidity = true;

    if (item.purchaseDate && item.warrantyExpiryDate) {
      const purchaseDate = new Date(item.purchaseDate);
      const warrantyDate = new Date(item.warrantyExpiryDate);
      if (purchaseDate >= warrantyDate) {
        customValidity = false;
        setDateError(true);
      } else {
        setDateError(false);
      }
    } else {
      setDateError(false);
    }

    if (form.checkValidity() && customValidity) {
      if (!id) {
        await DevicesApiService.create(body);
      } else {
        await DevicesApiService.update(id, body);
      }
      devicesChangeHandle?.();
      toast.success('Устройство успешно сохранено', { id: 'DevicesTable' });
      return true;
    }

    setValidated(true);
    return false;
  };

  return {
    item,
    validated,
    dateError,
    handleSubmit,
    handleChange,
    handleModelChange,
    handleLocationChange,
    handleEmployeeChange,
    initialModel,
    initialLocation,
    initialEmployee,
    resetValidity
  };
};

export default useDeviceItemForm;