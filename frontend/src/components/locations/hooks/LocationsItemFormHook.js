import { useState } from 'react';
import toast from 'react-hot-toast';
import useLocationItem from './LocationsItemHook';
import LocationsApiService from '../service/LocationsApiService';

const useLocationItemForm = (id, locationsChangeHandle) => {
  const {
    item,
    setItem,
    initialBuilding,
    initialDepartment,
    initialEmployees
  } = useLocationItem(id);

  const [validated, setValidated] = useState(false);

  const resetValidity = () => {
    setValidated(false);
  };

  const getLocationObject = ({
    name,
    type,
    buildingId,
    departmentId,
    employeeIds
  }) => ({
    name,
    type,
    buildingId,
    departmentId,
    employeeIds
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setItem({
      ...item,
      [name]: value,
    });
  };

  const handleBuildingChange = (newBuilding) => {
    setItem({
      ...item,
      buildingId: newBuilding,
    });
  };

  const handleDepartmentChange = (newDepartment) => {
    setItem({
      ...item,
      departmentId: newDepartment,
    });
  };

  const handleEmployeeIdsChange = (newEmployeeIds) => {
    setItem({
      ...item,
      employeeIds: newEmployeeIds
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    const body = getLocationObject(item);
    if (form.checkValidity()) {
      if (!id) {
        await LocationsApiService.create(body);
      } else {
        await LocationsApiService.update(id, body);
      }
      locationsChangeHandle?.();
      toast.success('Элемент успешно сохранен', { id: 'LocationsTable' });
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
    handleBuildingChange,
    handleDepartmentChange,
    handleEmployeeIdsChange,
    initialBuilding,
    initialDepartment,
    initialEmployees,
    resetValidity
  };
};

export default useLocationItemForm;