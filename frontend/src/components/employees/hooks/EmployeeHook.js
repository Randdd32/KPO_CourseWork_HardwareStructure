import { useState } from 'react';
import { EmployeesGetService } from '../service/EmployeesApiService';

const useEmployees = () => {
  const [employeesRefresh, setEmployeesRefresh] = useState(false);
  const [employees, setEmployees] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const handleEmployeesChange = () => setEmployeesRefresh(!employeesRefresh);

  const getEmployees = async (page = 1, size = 8) => {
    const data = await EmployeesGetService.getAll(`?page=${page - 1}&size=${size}`);
    setEmployees(data.items || []);
    setTotalPages(data.totalPages || 1);
  };

  return {
    employees,
    totalPages,
    getEmployees,
    employeesRefresh,
    handleEmployeesChange
  };
};

export default useEmployees;