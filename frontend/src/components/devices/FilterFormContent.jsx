import { Form, Button } from 'react-bootstrap';
import SearchableSelect from '../input/SelectForFilters';
import { ManufacturersGetService } from '../manufacturers/service/ManufacturersApiService';
import { BuildingsGetService } from '../buildings/service/BuildingsApiService';
import { LocationsGetService } from '../locations/service/LocationsApiService';
import { EmployeesGetService } from '../employees/service/EmployeesApiService';
import { DeviceTypesGetService } from '../deviceTypes/service/DeviceTypesApiService';
import { DepartmentsGetService } from '../departments/service/DepartmentsApiService';
import PropTypes from 'prop-types';

const FilterFormContent = ({
  manufacturerIds,
  buildingIds,
  locationIds,
  employeeIds,
  typeIds,
  departmentIds,
  isWorking,
  localPriceFrom,
  setLocalPriceFrom,
  localPriceTo,
  setLocalPriceTo,
  purchaseDateFrom,
  purchaseDateTo,
  warrantyDateFrom,
  warrantyDateTo,
  updateParams,
  handleResetFilters,
  parseDateTimeForInput,
  formatDateTimeForBackend,
}) => {
  return (
    <Form>
      <div className="filter-group mb-4">
        <h6 className="mb-3">Производители</h6>
        <SearchableSelect
          apiService={ManufacturersGetService}
          placeholder="Выберите производителей"
          displayField="name"
          valueField="id"
          buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
          isMulti
          isPagination={false}
          value={manufacturerIds}
          onChange={(values) => updateParams({ manufacturerIds: values })}
        />
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Здания</h6>
        <SearchableSelect
          apiService={BuildingsGetService}
          placeholder="Выберите здания"
          displayField="name"
          valueField="id"
          buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
          isMulti
          isPagination={false}
          value={buildingIds}
          onChange={(values) => updateParams({ buildingIds: values })}
        />
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Помещения</h6>
        <SearchableSelect
          apiService={LocationsGetService}
          placeholder="Выберите помещения"
          displayField="nameWithBuilding"
          valueField="id"
          buildQuery={(term) => `?name=${encodeURIComponent(term)}&page=0&size=40`}
          isMulti
          isPagination={true}
          value={locationIds}
          onChange={(values) => updateParams({ locationIds: values })}
        />
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Сотрудники</h6>
        <SearchableSelect
          apiService={EmployeesGetService}
          placeholder="Выберите сотрудников"
          displayField="fullName"
          valueField="id"
          buildQuery={(term) => `?fullName=${encodeURIComponent(term)}&page=0&size=40`}
          isMulti
          isPagination={true}
          value={employeeIds}
          onChange={(values) => updateParams({ employeeIds: values })}
        />
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Типы устройств</h6>
        <SearchableSelect
          apiService={DeviceTypesGetService}
          placeholder="Выберите типы устройств"
          displayField="name"
          valueField="id"
          buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
          isMulti
          isPagination={false}
          value={typeIds}
          onChange={(values) => updateParams({ typeIds: values })}
        />
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Отделы</h6>
        <SearchableSelect
          apiService={DepartmentsGetService}
          placeholder="Выберите отделы"
          displayField="name"
          valueField="id"
          buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
          isMulti
          isPagination={false}
          value={departmentIds}
          onChange={(values) => updateParams({ departmentIds: values })}
        />
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Статус работы</h6>
        <Form.Check
          type="radio"
          id="isWorkingTrue"
          label="Работает"
          name="isWorking"
          checked={isWorking === true}
          onChange={() => updateParams({ isWorking: 'true' })}
          className="mb-2"
        />
        <Form.Check
          type="radio"
          id="isWorkingFalse"
          label="Не работает"
          name="isWorking"
          checked={isWorking === false}
          onChange={() => updateParams({ isWorking: 'false' })}
          className="mb-2"
        />
        <Form.Check
          type="radio"
          id="isWorkingAll"
          label="Все"
          name="isWorking"
          checked={isWorking === null}
          onChange={() => updateParams({ isWorking: null })}
        />
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Ценовой диапазон</h6>
        <div className="d-flex gap-2 mb-2">
          <Form.Control
            className='rounded-4'
            type="number"
            placeholder="От"
            value={localPriceFrom}
            onChange={(e) => setLocalPriceFrom(e.target.value)}
            onBlur={(e) => updateParams({ priceFrom: e.target.value || null })}
          />
          <Form.Control
            className='rounded-4'
            type="number"
            placeholder="До"
            value={localPriceTo}
            onChange={(e) => setLocalPriceTo(e.target.value)}
            onBlur={(e) => updateParams({ priceTo: e.target.value || null })}
          />
        </div>
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Дата покупки</h6>
        <div className="mb-2">
          <label htmlFor="purchaseDateFrom" className="form-label mb-1">От:</label>
          <input
            type="datetime-local"
            id="purchaseDateFrom"
            className="form-control rounded-2 w-100"
            value={parseDateTimeForInput(purchaseDateFrom)}
            onChange={(e) => updateParams({ purchaseDateFrom: formatDateTimeForBackend(e.target.value) || null })}
            step="1"
          />
        </div>
        <div>
          <label htmlFor="purchaseDateTo" className="form-label mb-1">До:</label>
          <input
            type="datetime-local"
            id="purchaseDateTo"
            className="form-control rounded-2 w-100"
            value={parseDateTimeForInput(purchaseDateTo)}
            onChange={(e) => updateParams({ purchaseDateTo: formatDateTimeForBackend(e.target.value) || null })}
            step="1"
          />
        </div>
      </div>

      <div className="filter-group mb-4">
        <h6 className="mb-3">Дата окончания гарантии</h6>
        <div className="mb-2">
          <label htmlFor="warrantyDateFrom" className="form-label mb-1">От:</label>
          <input
            type="datetime-local"
            id="warrantyDateFrom"
            className="form-control rounded-2 w-100"
            value={parseDateTimeForInput(warrantyDateFrom)}
            onChange={(e) => updateParams({ warrantyDateFrom: formatDateTimeForBackend(e.target.value) || null })}
            step="1"
          />
        </div>
        <div>
          <label htmlFor="warrantyDateTo" className="form-label mb-1">До:</label>
          <input
            type="datetime-local"
            id="warrantyDateTo"
            className="form-control rounded-2 w-100"
            value={parseDateTimeForInput(warrantyDateTo)}
            onChange={(e) => updateParams({ warrantyDateTo: formatDateTimeForBackend(e.target.value) || null })}
            step="1"
          />
        </div>
      </div>

      <Button variant="outline-secondary" className="w-100" onClick={handleResetFilters}>
        Сбросить фильтры
      </Button>
    </Form>
  );
};

FilterFormContent.propTypes = {
  manufacturerIds: PropTypes.arrayOf(PropTypes.number).isRequired,
  buildingIds: PropTypes.arrayOf(PropTypes.number).isRequired,
  locationIds: PropTypes.arrayOf(PropTypes.number).isRequired,
  employeeIds: PropTypes.arrayOf(PropTypes.number).isRequired,
  typeIds: PropTypes.arrayOf(PropTypes.number).isRequired,
  departmentIds: PropTypes.arrayOf(PropTypes.number).isRequired,
  isWorking: PropTypes.oneOf([true, false, null]),
  localPriceFrom: PropTypes.string.isRequired,
  setLocalPriceFrom: PropTypes.func.isRequired,
  localPriceTo: PropTypes.string.isRequired,
  setLocalPriceTo: PropTypes.func.isRequired,
  purchaseDateFrom: PropTypes.string.isRequired,
  purchaseDateTo: PropTypes.string.isRequired,
  warrantyDateFrom: PropTypes.string.isRequired,
  warrantyDateTo: PropTypes.string.isRequired,
  updateParams: PropTypes.func.isRequired,
  handleResetFilters: PropTypes.func.isRequired,
  parseDateTimeForInput: PropTypes.func.isRequired,
  formatDateTimeForBackend: PropTypes.func.isRequired
};

export default FilterFormContent;