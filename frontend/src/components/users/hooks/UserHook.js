import { useState } from 'react';
import UsersApiService from '../service/UsersApiService';
import { UserRoleMapping } from '../../utils/Constants';

const useUsers = () => {
  const [usersRefresh, setUsersRefresh] = useState(false);
  const [users, setUsers] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const handleUsersChange = () => setUsersRefresh(!usersRefresh);

  const getUsers = async (page = 1, size = 8) => {
    const data = await UsersApiService.getAll(`?page=${page - 1}&size=${size}`);
    const mappedUsers = (data.items || []).map(user => ({
      ...user,
      role: UserRoleMapping[user.role] || user.role
    }));
    setUsers(mappedUsers);
    setTotalPages(data.totalPages || 1);
  };

  return {
    users,
    totalPages,
    getUsers,
    usersRefresh,
    handleUsersChange
  };
};

export default useUsers;