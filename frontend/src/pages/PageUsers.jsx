import { useEffect } from 'react';
import Users from '../components/users/table/Users';

const PageUsers = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <Users />
  );
};

export default PageUsers;