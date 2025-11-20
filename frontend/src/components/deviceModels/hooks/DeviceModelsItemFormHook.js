import { useState } from 'react';
import toast from 'react-hot-toast';
import DeviceModelsApiService from '../service/DeviceModelsApiService';
import useDeviceModelItem from './DeviceModelsItemHook';

const useDeviceModelItemForm = (id, deviceModelsChangeHandle) => {
  const { item, setItem, initialManufacturer, initialDeviceType, initialStructureElements } = useDeviceModelItem(id);
  const [validated, setValidated] = useState(false);

  const resetValidity = () => setValidated(false);

  const getDeviceModelObject = ({
    name,
    description,
    typeId,
    manufacturerId,
    structureElementsIds
  }) => ({
    name,
    description,
    typeId,
    manufacturerId,
    structureElementsIds
  });

  const handleChange = (event) => {
    setItem({
      ...item,
      [event.target.name]: event.target.value
    });
  };

  const handleTypeChange = (newType) => {
    setItem({
      ...item,
      typeId: newType
    });
  }

  const handleManufacturerChange = (newManufacturer) => {
    setItem({
      ...item,
      manufacturerId: newManufacturer
    });
  }

  const handleStructureElementsChange = (newElements) => {
    setItem({
      ...item,
      structureElementsIds: newElements
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    const body = getDeviceModelObject(item);
    if (form.checkValidity()) {
      if (id === undefined) {
        await DeviceModelsApiService.create(body);
      } else {
        await DeviceModelsApiService.update(id, body);
      }
      if (deviceModelsChangeHandle)
        deviceModelsChangeHandle();
      toast.success('Элемент успешно сохранен', { id: 'DeviceModelsTable' });
      return true;
    }
    setValidated(true);
    return false;
  };

  return {
    item,
    validated,
    handleSubmit,
    handleChange,
    handleTypeChange,
    handleManufacturerChange,
    handleStructureElementsChange,
    initialManufacturer, 
    initialDeviceType,
    initialStructureElements,
    resetValidity
  };
};

export default useDeviceModelItemForm;