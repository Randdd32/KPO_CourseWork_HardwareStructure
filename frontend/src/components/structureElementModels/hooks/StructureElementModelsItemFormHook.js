import { useState } from 'react';
import toast from 'react-hot-toast';
import StructureElementModelsApiService from '../service/StructureElementModelsApiService';
import useStructureElementModelItem from './StructureElementModelsItemHook';

const useStructureElementModelItemForm = (id, structureElementModelsChangeHandle) => {
  const { item, setItem, initialManufacturer, initialType } = useStructureElementModelItem(id);
  const [validated, setValidated] = useState(false);

  const resetValidity = () => setValidated(false);

  const getStructureElementModelObject = ({
    name,
    description,
    typeId,
    manufacturerId,
    workEfficiency,
    reliability,
    energyEfficiency,
    userFriendliness,
    durability,
    aestheticQualities
  }) => ({
    name,
    description,
    typeId,
    manufacturerId,
    workEfficiency,
    reliability,
    energyEfficiency,
    userFriendliness,
    durability,
    aestheticQualities
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
  };

  const handleManufacturerChange = (newManufacturer) => {
    setItem({
      ...item,
      manufacturerId: newManufacturer
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    const body = getStructureElementModelObject(item);
    if (form.checkValidity()) {
      if (id === undefined) {
        await StructureElementModelsApiService.create(body);
      } else {
        await StructureElementModelsApiService.update(id, body);
      }
      if (structureElementModelsChangeHandle)
        structureElementModelsChangeHandle();
      toast.success('Элемент успешно сохранен', { id: 'StructureElementModelsTable' });
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
    initialManufacturer,
    initialType,
    resetValidity
  };
};

export default useStructureElementModelItemForm;