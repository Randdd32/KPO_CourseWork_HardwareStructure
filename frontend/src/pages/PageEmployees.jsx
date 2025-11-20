import { useEffect } from 'react';
import Employees from '../components/employees/table/Employees';

const PageEmployees = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Employees />
  );
};

export default PageEmployees;