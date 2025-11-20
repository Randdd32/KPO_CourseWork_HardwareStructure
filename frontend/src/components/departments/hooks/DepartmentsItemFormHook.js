import { useState } from 'react';
import toast from 'react-hot-toast';
import DepartmentsApiService from '../service/DepartmentsApiService';
import useDepartmentsItem from './DepartmentsItemHook';

const useDepartmentsItemForm = (id, departmentsChangeHandle) => {
  const { item, setItem, initialPositions } = useDepartmentsItem(id);

  const [validated, setValidated] = useState(false);

  const resetValidity = () => {
    setValidated(false);
  };

  const getDepartmentObject = ({ name, positionIds }) => ({
    name,
    positionIds
  });

  const handleChange = (event) => {
    setItem({
      ...item,
      [event.target.name]: event.target.value
    });
  };

  const handlePositionIdsChange = (newPositionIds) => {
    setItem({
      ...item,
      positionIds: newPositionIds
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    const body = getDepartmentObject(item);
    if (form.checkValidity()) {
      if (id === undefined) {
        await DepartmentsApiService.create(body);
      } else {
        await DepartmentsApiService.update(id, body);
      }
      if (departmentsChangeHandle)
        departmentsChangeHandle();
      toast.success('Элемент успешно сохранен', { id: 'DepartmentsTable' });
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
    handlePositionIdsChange,
    initialPositions,
    resetValidity
  };
};

export default useDepartmentsItemForm;