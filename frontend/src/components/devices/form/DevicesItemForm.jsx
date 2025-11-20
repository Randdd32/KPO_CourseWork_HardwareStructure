import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';
import TextArea from '../../input/TextArea.jsx';
import SearchableSelect from '../../input/SearchableSelect.jsx';
import DatePicker, { registerLocale, setDefaultLocale } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ru from 'date-fns/locale/ru';

import { LocationsGetService } from '../../locations/service/LocationsApiService.js';
import { EmployeesGetService } from '../../employees/service/EmployeesApiService.js';
import { DeviceModelsGetService } from '../../deviceModels/service/DeviceModelsApiService.js';

registerLocale('ru', ru);
setDefaultLocale('ru');

const DevicesItemForm = ({
  id,
  item,
  handleChange,
  validated,
  dateError,
  handleModelChange,
  handleLocationChange,
  handleEmployeeChange,
  initialModel,
  initialLocation,
  initialEmployee
}) => {
  const SERIAL_PATTERN = /^.{1,15}$/;

  const handleDateChange = (date, name) => {
    handleChange({ target: { name, value: date?.toISOString() } });
  };

  return (
    <>
      <Input name='id' label='ID' value={item.id}
        className='mb-4' type='text' readOnly disabled />
      <Input name='serialNumber' label='Серийный номер' value={item.serialNumber} onChange={handleChange}
        className='mb-4' type='text' pattern={SERIAL_PATTERN.source}
        isInvalid={validated && !SERIAL_PATTERN.test(item.serialNumber)}
        feedback="Серийный номер должен содержать от 1 до 15 символов" required />
      {validated && dateError && (
        <div className="invalid-feedback d-block mb-3 fs-6">Дата окончания гарантии должна быть позже даты покупки!</div>
      )}
      <div className='mb-4'>
        <div className='form-label'>Дата покупки</div>
        <DatePicker
          selected={item.purchaseDate ? new Date(item.purchaseDate) : null}
          onChange={(date) => handleDateChange(date, 'purchaseDate')}
          showTimeSelect
          showTimeSelectSeconds
          timeFormat="HH:mm:ss"
          timeIntervals={15}
          timeCaption="Время"
          dateFormat="yyyy-MM-dd HH:mm:ss"
          className={`form-control rounded-2 ${validated && !item.purchaseDate ? 'is-invalid' : ''}`}
          locale="ru"
          placeholderText="Выберите дату"
          required
        />
        {validated && !item.purchaseDate && <div className="invalid-feedback">Дата покупки обязательна</div>}
      </div>
      <div className='mb-4'>
        <div className='form-label'>Дата окончания гарантии</div>
        <DatePicker
          selected={item.warrantyExpiryDate ? new Date(item.warrantyExpiryDate) : null}
          onChange={(date) => handleDateChange(date, 'warrantyExpiryDate')}
          showTimeSelect
          showTimeSelectSeconds
          timeFormat="HH:mm:ss"
          timeIntervals={15}
          timeCaption="Время"
          dateFormat="yyyy-MM-dd HH:mm:ss"
          className={`form-control rounded-2 ${validated && !item.warrantyExpiryDate ? 'is-invalid' : ''}`}
          locale="ru"
          placeholderText="Выберите дату"
          required
        />
        {validated && !item.warrantyExpiryDate && <div className="invalid-feedback">Дата окончания гарантии обязательна</div>}
      </div>
      <Input name='price' label='Цена' value={item.price} onChange={handleChange}
        className='mb-4' type='number' min="0" step="0.01"
        isInvalid={validated && (item.price === '' || Number(item.price) < 0)}
        feedback="Цена должна быть неотрицательным числом" required />
      <div className="form-check mb-4">
        <input className="form-check-input" type="checkbox" id="isWorking" name="isWorking"
          checked={item.isWorking} onChange={e => handleChange({ target: { name: 'isWorking', value: e.target.checked } })}
        />
        <label className="form-check-label" htmlFor="isWorking">Работает</label>
      </div>
      <TextArea name='furtherInformation' label='Дополнительная информация' value={item.furtherInformation}
        onChange={handleChange} className='mb-4' />
      <div className="form-label">{id ? 'Модель устройства' : 'Модель устройства (обязательна к выбору)'}</div>
      <SearchableSelect
        apiService={DeviceModelsGetService}
        placeholder="Выберите модель устройства"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}&page=0&size=40`}
        isMulti={false}
        isPagination={true}
        className='mb-4'
        initialValue={initialModel}
        onChange={handleModelChange}
        required={true}
      />
      <div className="form-label">Помещение</div>
      <SearchableSelect
        apiService={LocationsGetService}
        placeholder="Выберите помещение"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}&page=0&size=40`}
        isMulti={false}
        isPagination={true}
        className='mb-4'
        initialValue={initialLocation}
        onChange={handleLocationChange}
      />
      <div className="form-label">Ответственный сотрудник</div>
      <SearchableSelect
        apiService={EmployeesGetService}
        placeholder="Выберите сотрудника"
        displayField="fullName"
        valueField="id"
        buildQuery={(term) => `?fullName=${encodeURIComponent(term)}&page=0&size=40`}
        isMulti={false}
        isPagination={true}
        className='mb-4'
        initialValue={initialEmployee}
        onChange={handleEmployeeChange}
      />
    </>
  );
};

DevicesItemForm.propTypes = {
  id: PropTypes.string,
  item: PropTypes.object.isRequired,
  handleChange: PropTypes.func.isRequired,
  validated: PropTypes.bool,
  dateError: PropTypes.bool,
  handleModelChange: PropTypes.func.isRequired,
  handleLocationChange: PropTypes.func.isRequired,
  handleEmployeeChange: PropTypes.func.isRequired,
  initialModel: PropTypes.object,
  initialLocation: PropTypes.object,
  initialEmployee: PropTypes.object,
};

export default DevicesItemForm;