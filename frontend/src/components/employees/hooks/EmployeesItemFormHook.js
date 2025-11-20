import { useState } from 'react';
import toast from 'react-hot-toast';
import useEmployeeItem from './EmployeesItemHook';
import EmployeesApiService from '../service/EmployeesApiService';

const useEmployeeItemForm = (id, employeesChangeHandle) => {
  const {
    item,
    setItem,
    initialDepartment,
    initialPosition
  } = useEmployeeItem(id);

  const [validated, setValidated] = useState(false);

  const resetValidity = () => {
    setValidated(false);
  };

  const getEmployeeObject = ({
    lastName,
    firstName,
    patronymic,
    departmentId,
    positionId
  }) => ({
    lastName,
    firstName,
    patronymic,
    departmentId,
    positionId
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setItem({
      ...item,
      [name]: value
    });
  };

  const handleDepartmentChange = (newDepartment) => {
    setItem({
      ...item,
      departmentId: newDepartment
    });
  };

  const handlePositionChange = (newPosition) => {
    setItem({
      ...item,
      positionId: newPosition
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();

    const body = getEmployeeObject(item);

    if (form.checkValidity()) {
      if (!id) {
        await EmployeesApiService.create(body);
      } else {
        await EmployeesApiService.update(id, body);
      }
      employeesChangeHandle?.();
      toast.success('Элемент успешно сохранён', { id: 'EmployeesTable' });
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
    handleDepartmentChange,
    handlePositionChange,
    initialDepartment,
    initialPosition,
    resetValidity
  };
};

export default useEmployeeItemForm;