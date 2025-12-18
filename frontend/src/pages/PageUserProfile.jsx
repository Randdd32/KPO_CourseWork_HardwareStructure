import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import UserProfile from '../components/users/UserProfile';

const PageUserProfile = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <UserProfile userId={id} />
  );
};

export default PageUserProfile;