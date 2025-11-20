import { useEffect } from 'react';
import Departments from '../components/departments/table/Departments';

const PageDepartments = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Departments />
  );
};

export default PageDepartments;