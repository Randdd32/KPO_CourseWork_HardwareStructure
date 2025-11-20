import { useState } from 'react';
import toast from 'react-hot-toast';
import DeviceTypesApiService from '../service/DeviceTypesApiService';
import useDeviceTypeItem from './DeviceTypesItemHook';

const useDeviceTypeItemForm = (id, deviceTypesChangeHandle) => {
  const { item, setItem } = useDeviceTypeItem(id);

  const [validated, setValidated] = useState(false);

  const resetValidity = () => {
    setValidated(false);
  };

  const getDeviceTypeObject = ({ name }) => ({
    name,
  });

  const handleChange = (event) => {
    setItem({
      ...item,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    const body = getDeviceTypeObject(item);

    if (form.checkValidity()) {
      if (id === undefined) {
        await DeviceTypesApiService.create(body);
      } else {
        await DeviceTypesApiService.update(id, body);
      }
      if (deviceTypesChangeHandle) {
        deviceTypesChangeHandle();
      }
      toast.success('Элемент успешно сохранен', { id: 'DeviceTypesTable' });
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
    resetValidity,
  };
};

export default useDeviceTypeItemForm;