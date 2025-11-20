import { useEffect, useState } from 'react';
import { DepartmentsGetService } from '../service/DepartmentsApiService';

const useDepartments = () => {
  const [departmentsRefresh, setDepartmentsRefresh] = useState(false);
  const [departments, setDepartments] = useState([]);
  const handleDepartmentsChange = () => setDepartmentsRefresh(!departmentsRefresh);

  const getDepartments = async () => {
    const data = await DepartmentsGetService.getAll();
    setDepartments(data ?? []);
  };

  useEffect(() => {
    getDepartments();
  }, [departmentsRefresh]);

  return {
    departments,
    setDepartments,
    handleDepartmentsChange
  };
};

export default useDepartments;