import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { Alert } from 'react-bootstrap';
import { UsersGetByIdService } from './service/UsersApiService';
import LoadingElement from '../utils/LoadingElement';
import { UserRoleMapping } from '../utils/Constants'
import './profile.css';

const UserProfile = ({ userId }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    UsersGetByIdService.get(userId)
      .then((res) => {
        setUser(res);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [userId]);

  if (loading)
    return <LoadingElement />;
  if (error)
    return <div className="text-center mt-3"><Alert variant="danger">{error}</Alert></div>;

  return (
    <div className="profile-container mt-2">
      <div className='d-flex justify-content-center fs-4 fw-bold admin-title mt-1 mb-2'>
        Ваши данные
      </div>
      <dl className="profile-list mt-1 mb-2">
        <dt>Электронная почта</dt>
        <dd>{user.email}</dd>

        <dt>Номер телефона</dt>
        <dd>{user.phoneNumber}</dd>

        <dt>Ваша роль</dt>
        <dd>{UserRoleMapping[user.role] || user.role}</dd>

        <dt>ФИО</dt>
        <dd>{user.employeeFullName}</dd>
      </dl>
    </div>
  );
};

UserProfile.propTypes = {
  userId: PropTypes.number,
};

export default UserProfile;