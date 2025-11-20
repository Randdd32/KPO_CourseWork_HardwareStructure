import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';
import SearchableSelect from '../../input/SearchableSelect.jsx';
import Select from '../../input/Select.jsx';
import { BuildingsGetService } from '../../buildings/service/BuildingsApiService.js';
import { DepartmentsGetService } from '../../departments/service/DepartmentsApiService.js';
import { EmployeesGetService } from '../../employees/service/EmployeesApiService.js';
import { LocationTypeMapping } from '../../utils/Constants.js';

const LocationItemForm = ({
  id,
  item,
  handleChange,
  validated,
  handleBuildingChange,
  handleDepartmentChange,
  handleEmployeeIdsChange,
  initialBuilding,
  initialDepartment,
  initialEmployees
}) => {
  const locationTypeOptions = Object.entries(LocationTypeMapping).map(([key, value]) => ({
    id: key,
    name: value
  }));

  const NAME_PATTERN = /^.{1,50}$/;

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
        name="name"
        label="Название"
        value={item.name}
        onChange={handleChange}
        className="mb-4"
        type="text"
        pattern={NAME_PATTERN.source}
        isInvalid={validated && !NAME_PATTERN.test(item.name)}
        feedback="Название должно содержать от 1 до 50 символов"
        required
      />
      <Select
        name="type"
        label="Тип помещения"
        values={locationTypeOptions}
        value={item.type}
        onChange={handleChange}
        className="mb-4"
        required
        isInvalid={validated && !item.type}
        feedback="Выберите тип помещения"
      />
      <div className="form-label">{id ? 'Здание' : 'Здание (обязательно к выбору)'}</div>
      <SearchableSelect
        apiService={BuildingsGetService}
        placeholder="Выберите здание"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
        isMulti={false}
        isPagination={false}
        className="mb-4"
        initialValue={initialBuilding}
        onChange={handleBuildingChange}
        required={true}
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
      <div className="form-label">Сотрудники</div>
      <SearchableSelect
        apiService={EmployeesGetService}
        placeholder="Выберите сотрудников"
        displayField="fullName"
        valueField="id"
        buildQuery={(term) => `?fullName=${encodeURIComponent(term)}&page=0&size=40`}
        isMulti={true}
        isPagination={true}
        className="mb-4"
        initialValue={initialEmployees}
        onChange={handleEmployeeIdsChange}
      />
    </>
  );
};

LocationItemForm.propTypes = {
  id: PropTypes.string,
  item: PropTypes.object.isRequired,
  handleChange: PropTypes.func.isRequired,
  validated: PropTypes.bool,
  handleBuildingChange: PropTypes.func.isRequired,
  handleDepartmentChange: PropTypes.func.isRequired,
  handleEmployeeIdsChange: PropTypes.func.isRequired,
  initialBuilding: PropTypes.object,
  initialDepartment: PropTypes.object,
  initialEmployees: PropTypes.array
};

export default LocationItemForm;