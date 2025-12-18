import { useContext } from 'react';
import Input from '../../input/Input.jsx';
import Select from '../../input/Select.jsx';
import { EmployeesGetService } from '../../employees/service/EmployeesApiService.js';
import SearchableSelect from '../../input/SearchableSelect.jsx'
import { PASSWORD_PATTERN_STRING, PHONE_PATTERN_STRING, PASSWORD_MESSAGE } from '../../utils/Constants.js'
import { observer } from "mobx-react-lite";
import StoreContext from '../StoreContext.jsx';

const UserItemForm = observer(({
  id,
  item,
  handleChange,
  validated,
  handleEmployeeChange,
  initialEmployee
}) => {
  const { store } = useContext(StoreContext);

  const getRoleOptions = (currentUserRole) => {
    switch (currentUserRole) {
      case 'ADMIN':
        return [
          { id: 'USER', name: 'Пользователь' },
          { id: 'ADMIN', name: 'Администратор', disabled: true },
          { id: 'SUPER_ADMIN', name: 'Супер администратор', disabled: true },
        ];
      case 'SUPER_ADMIN':
        return [
          { id: 'USER', name: 'Пользователь' },
          { id: 'ADMIN', name: 'Администратор' },
          { id: 'SUPER_ADMIN', name: 'Супер администратор', disabled: true },
        ];
      default:
        return [
          { id: 'USER', name: 'Пользователь' },
          { id: 'ADMIN', name: 'Администратор' },
          { id: 'SUPER_ADMIN', name: 'Супер администратор' },
        ];
    }
  };

  const ROLE_OPTIONS = getRoleOptions(store?.user?.role);
  const PASSWORD_PATTERN = new RegExp(PASSWORD_PATTERN_STRING);
  const PHONE_PATTERN = new RegExp(PHONE_PATTERN_STRING);

  return (
    <>
      <Input
        name="id"
        label="ID"
        value={item.id}
        className="mb-4"
        type="text"
        readOnly
        disabled
      />
      <Input
        name="email"
        label="Электронная почта"
        value={item.email}
        onChange={handleChange}
        className="mb-4"
        type="email"
        feedback="Введите корректный email"
        required
      />
      <Input
        name="password"
        label={id ? "Пароль (оставьте поле пустым, если не хотите изменять)" : "Пароль"}
        value={item.password ?? ''}
        onChange={handleChange}
        className="mb-4"
        type="password"
        pattern={PASSWORD_PATTERN_STRING}
        isInvalid={validated && (
          (!id && !item.password) ||
          (item.password && !PASSWORD_PATTERN.test(item.password))
        )}
        feedback={PASSWORD_MESSAGE}
        required={!id}
      />
      <Input
        name="phoneNumber"
        label="Номер телефона"
        value={item.phoneNumber}
        onChange={handleChange}
        className="mb-4"
        type="text"
        pattern={PHONE_PATTERN_STRING}
        isInvalid={validated && !PHONE_PATTERN.test(item.phoneNumber)}
        feedback="Неккоректный формат номера телефона для стран СНГ"
        required
      />
      <Select
        name="role"
        label="Роль"
        values={ROLE_OPTIONS}
        value={item.role}
        onChange={handleChange}
        className="mb-4"
        required
        isInvalid={validated && !item.role}
        feedback="Выберите роль пользователя"
      />
      <div className="form-label">{id ? 'Владелец аккаунта (сотрудник)' : 'Владелец аккаунта (сотрудник, обязателен к выбору)'}</div>
      <SearchableSelect
        apiService={EmployeesGetService}
        placeholder="Выберите сотрудника"
        displayField="fullName"
        valueField="id"
        buildQuery={(term) => `?fullName=${encodeURIComponent(term)}&withoutAccount=true&page=0&size=40`}
        isMulti={false}
        isPagination={true}
        className="mb-4"
        initialValue={initialEmployee}
        onChange={handleEmployeeChange}
        required={true}
      />
    </>
  );
});

export default UserItemForm;