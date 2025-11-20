import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';
import SearchableSelect from '../../input/SearchableSelect.jsx';
import PositionsApiService from '../../positions/service/PositionsApiService.js';

const DepartmentsItemForm = ({ item, handleChange, validated, handlePositionsChange, initialPositions }) => {
  const NAME_PATTERN = /^.{1,50}$/;

  return (
    <>
      <Input name='id' label='ID' value={item.id}
        className='mb-4' type='text' readOnly disabled />
      <Input name='name' label='Название отдела' value={item.name} onChange={handleChange}
        className='mb-4' type='text' pattern={NAME_PATTERN.source} isInvalid={validated && !NAME_PATTERN.test(item.name)}
        feedback="Название отдела должно содержать от 1 до 50 символов" required />
      <div className="form-label">Должности</div>
      <SearchableSelect
        apiService={PositionsApiService}
        placeholder="Выберите должности"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}&page=0&size=40`}
        isMulti={true}
        isPagination={true}
        className='mb-4'
        initialValue={initialPositions}
        onChange={handlePositionsChange}
      />
    </>
  );
};

DepartmentsItemForm.propTypes = {
  item: PropTypes.object,
  handleChange: PropTypes.func,
  validated: PropTypes.bool,
  handlePositionsChange: PropTypes.func,
  initialPositions: PropTypes.array
};

export default DepartmentsItemForm;