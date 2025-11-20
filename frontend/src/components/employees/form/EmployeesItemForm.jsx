import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';
import SearchableSelect from '../../input/SearchableSelect.jsx';
import { DepartmentsGetService } from '../../departments/service/DepartmentsApiService.js';
import PositionsApiService from '../../positions/service/PositionsApiService.js';
import { NAME_PATTERN } from '../../utils/Constants.js';

const EmployeeItemForm = ({
  item,
  handleChange,
  validated,
  handleDepartmentChange,
  handlePositionChange,
  initialDepartment,
  initialPosition
}) => {
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
        name="lastName"
        label="Фамилия"
        value={item.lastName}
        onChange={handleChange}
        className="mb-4"
        type="text"
        pattern={NAME_PATTERN.source}
        isInvalid={validated && !NAME_PATTERN.test(item.lastName)}
        feedback="Фамилия должна содержать от 1 до 50 символов, только буквы и дефисы"
        required
      />
      <Input
        name="firstName"
        label="Имя"
        value={item.firstName}
        onChange={handleChange}
        className="mb-4"
        type="text"
        pattern={NAME_PATTERN.source}
        isInvalid={validated && !NAME_PATTERN.test(item.firstName)}
        feedback="Имя должно содержать от 1 до 50 символов, только буквы и дефисы"
        required
      />
      <Input
        name="patronymic"
        label="Отчество"
        value={item.patronymic}
        onChange={handleChange}
        className="mb-4"
        type="text"
        pattern={NAME_PATTERN.source}
        isInvalid={validated && item.patronymic && !NAME_PATTERN.test(item.patronymic)}
        feedback="Отчество должно содержать до 50 символов, только буквы и дефисы (либо должно быть не указано)"
      />
      <div className="form-label">Отдел</div>
      <SearchableSelect
        apiService={DepartmentsGetService}
        placeholder="Выберите отдел"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
        isMulti={false}
        isPagination={false}
        className="mb-4"
        initialValue={initialDepartment}
        onChange={handleDepartmentChange}
      />
      <div className="form-label">Должность</div>
      <SearchableSelect
        apiService={PositionsApiService}
        placeholder="Выберите должность"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}&page=0&size=40`}
        isMulti={false}
        isPagination={true}
        className="mb-4"
        initialValue={initialPosition}
        onChange={handlePositionChange}
      />
    </>
  );
};

EmployeeItemForm.propTypes = {
  id: PropTypes.string,
  item: PropTypes.object.isRequired,
  handleChange: PropTypes.func.isRequired,
  validated: PropTypes.bool,
  handleDepartmentChange: PropTypes.func.isRequired,
  handlePositionChange: PropTypes.func.isRequired,
  initialDepartment: PropTypes.object,
  initialPosition: PropTypes.object
};

export default EmployeeItemForm;