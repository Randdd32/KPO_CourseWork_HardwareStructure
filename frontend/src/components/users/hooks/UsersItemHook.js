import { useEffect, useState } from 'react';
import { UsersGetByIdService } from '../service/UsersApiService';

const useUserItem = (id) => {
  const emptyItem = {
    id: '',
    email: '',
    password: '',
    phoneNumber: '',
    role: 'USER',
    employeeId: '',
    employeeFullName: ''
  };

  const [item, setItem] = useState({ ...emptyItem });
  const [initialEmployee, setInitialEmployee] = useState(null);
  const [initialEmail, setInitialEmail] = useState(null);

  const getItem = async (itemId = undefined) => {
    if (itemId && itemId > 0) {
      const data = await UsersGetByIdService.get(itemId);
      setItem(data);
      setInitialEmployee({ value: data.employeeId, label: data.employeeFullName });
      setInitialEmail(data.email);
    } else {
      setItem({ ...emptyItem });
      setInitialEmployee(null);
      setInitialEmail(null);
    }
  };

  useEffect(() => {
    getItem(id);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  return {
    item,
    setItem,
    initialEmployee,
    initialEmail
  };
};

export default useUserItem;