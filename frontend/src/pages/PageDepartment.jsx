import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import DepartmentsForm from '../components/departments/form/DepartmentsForm';

const PageDepartment = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <DepartmentsForm id={id} />
  );
};

export default PageDepartment;