import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import UsersForm from '../components/users/form/UsersForm';

const PageUser = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <UsersForm id={id} />
  );
};

export default PageUser;