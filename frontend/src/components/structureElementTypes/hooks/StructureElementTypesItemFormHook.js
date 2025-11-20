import { useState } from 'react';
import toast from 'react-hot-toast';
import StructureElementTypesApiService from '../service/StructureElementTypesApiService';
import useStructureElementTypeItem from './StructureElementTypesItemHook';

const useStructureElementTypeItemForm = (id, structureElementTypesChangeHandle) => {
  const { item, setItem } = useStructureElementTypeItem(id);

  const [validated, setValidated] = useState(false);

  const resetValidity = () => {
    setValidated(false);
  };

  const getStructureElementTypeObject = ({ name }) => ({
    name
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
    const body = getStructureElementTypeObject(item);

    if (form.checkValidity()) {
      if (id === undefined) {
        await StructureElementTypesApiService.create(body);
      } else {
        await StructureElementTypesApiService.update(id, body);
      }
      if (structureElementTypesChangeHandle) {
        structureElementTypesChangeHandle();
      }
      toast.success('Элемент успешно сохранен', { id: 'StructureElementTypesTable' });
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

export default useStructureElementTypeItemForm;