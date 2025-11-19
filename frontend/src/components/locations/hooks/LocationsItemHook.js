import { useEffect, useState } from 'react';
import { LocationsGetService } from '../service/LocationsApiService';

const useLocationItem = (id) => {
  const emptyItem = {
    id: '',
    name: '',
    type: 'UNKNOWN',
    buildingId: '',
    buildingName: '',
    departmentId: '',
    departmentName: '',
    employees: [],
    employeeIds: []
  };

  const [item, setItem] = useState({ ...emptyItem });
  const [initialBuilding, setInitialBuilding] = useState(null);
  const [initialDepartment, setInitialDepartment] = useState(null);
  const [initialEmployees, setInitialEmployees] = useState([]);

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await LocationsGetService.get(itemId);
      setItem(data);
      setInitialBuilding({ value: data.buildingId, label: data.buildingName });
      setInitialDepartment({ value: data.departmentId || 0, label: data.departmentName || 'N/A' });
      setInitialEmployees(
        data.employees?.map(employee => ({
          value: employee?.id || 0,
          label: employee?.fullName || 'N/A'
        })) || []
      );
    } else {
      setItem({ ...emptyItem });
    }
  };

  useEffect(() => {
    getItem(id);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  return {
    item,
    setItem,
    initialBuilding,
    initialDepartment,
    initialEmployees
  };
};

export default useLocationItem;