import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import EmployeesForm from '../components/employees/form/EmployeesForm';

const PageEmployee = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <EmployeesForm id={id} />
  );
};

export default PageEmployee;