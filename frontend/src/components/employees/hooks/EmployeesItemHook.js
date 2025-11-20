import { useEffect, useState } from 'react';
import { EmployeesGetService } from '../service/EmployeesApiService';

const useEmployeeItem = (id) => {
  const emptyItem = {
    id: '',
    lastName: '',
    firstName: '',
    patronymic: '',
    departmentId: '',
    departmentName: '',
    positionId: '',
    positionName: '',
    fullName: ''
  };

  const [item, setItem] = useState({ ...emptyItem });
  const [initialDepartment, setInitialDepartment] = useState(null);
  const [initialPosition, setInitialPosition] = useState(null);

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await EmployeesGetService.get(itemId);
      setItem(data);
      setInitialDepartment({ value: data.departmentId || 0, label: data.departmentName || 'N/A' });
      setInitialPosition({ value: data.positionId || 0, label: data.positionName || 'N/A' });
    } else {
      setItem({ ...emptyItem });
      setInitialDepartment(null);
      setInitialPosition(null);
    }
  };

  useEffect(() => {
    getItem(id);
  }, [id]);

  return {
    item,
    setItem,
    initialDepartment,
    initialPosition
  };
};

export default useEmployeeItem;