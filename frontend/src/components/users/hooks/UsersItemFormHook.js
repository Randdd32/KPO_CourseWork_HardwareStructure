import { useState } from 'react';
import toast from 'react-hot-toast';
import useUserItem from './UsersItemHook';
import UsersApiService from '../service/UsersApiService';
import AuthApiService from '../../api/AuthApiService';

const useUserItemForm = (id, usersChangeHandle) => {
  const {
    item,
    setItem,
    initialEmployee,
    initialEmail
  } = useUserItem(id);

  const [validated, setValidated] = useState(false);

  const authApiService = new AuthApiService();

  const resetValidity = () => {
    setValidated(false);
  };

  const getUserObject = ({
    email,
    password,
    phoneNumber,
    role,
    employeeId
  }) => ({
    email,
    password,
    phoneNumber,
    role,
    employeeId
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setItem({
      ...item,
      [name]: value,
    });
  };

  const handleEmployeeChange = (newEmployeeId) => {
    setItem({
      ...item,
      employeeId: newEmployeeId
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    const body = getUserObject(item);
    if (form.checkValidity()) {
      if (!id) {
        await UsersApiService.create(body);
      } else {
        await authApiService.logoutAll({ email: initialEmail });
        await UsersApiService.update(id, body);
      }
      usersChangeHandle?.();
      toast.success('Пользователь успешно сохранен', { id: 'UsersTable' });
      return true;
    }
    setValidated(true);
    return false;
  };

  return {
    item,
    validated,
    handleSubmit,
    handleChange,
    handleEmployeeChange,
    initialEmployee,
    resetValidity
  };
};

export default useUserItemForm;