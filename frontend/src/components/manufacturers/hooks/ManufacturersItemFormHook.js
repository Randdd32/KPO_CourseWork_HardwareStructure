import { useState } from 'react';
import toast from 'react-hot-toast';
import ManufacturersApiService from '../service/ManufacturersApiService';
import useManufacturerItem from './ManufacturersItemHook';

const useManufacturerItemForm = (id, manufacturersChangeHandle) => {
  const { item, setItem } = useManufacturerItem(id);

  const [validated, setValidated] = useState(false);

  const resetValidity = () => {
    setValidated(false);
  };

  const getManufacturerObject = ({ name }) => ({
    name
  });

  const handleChange = (event) => {
    setItem({
      ...item,
      [event.target.name]: event.target.value
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    const body = getManufacturerObject(item);

    if (form.checkValidity()) {
      if (id === undefined) {
        await ManufacturersApiService.create(body);
      } else {
        await ManufacturersApiService.update(id, body);
      }
      if (manufacturersChangeHandle) {
        manufacturersChangeHandle();
      }
      toast.success('Элемент успешно сохранен', { id: 'ManufacturersTable' });
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
    resetValidity
  };
};

export default useManufacturerItemForm;