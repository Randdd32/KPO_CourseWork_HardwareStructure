import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';
import TextArea from '../../input/TextArea.jsx';
import SearchableSelect from '../../input/SearchableSelect.jsx';
import StructureElementTypesApiService from '../../structureElementTypes/service/StructureElementTypesApiService.js';
import { ManufacturersGetService } from '../../manufacturers/service/ManufacturersApiService.js';

const StructureElementModelsItemForm = ({
  id,
  item,
  handleChange,
  validated,
  handleTypeChange,
  handleManufacturerChange,
  initialManufacturer,
  initialType
}) => {
  const NAME_PATTERN = /^.{1,50}$/;
  const NUMBER_PATTERN = /^(100|[1-9]?[0-9])$/;

  return (
    <>
      <Input name='id' label='ID' value={item.id}
        className='mb-4' type='text' readOnly disabled />
      <Input name='name' label='Название модели' value={item.name} onChange={handleChange}
        className='mb-4' type='text' pattern={NAME_PATTERN.source}
        isInvalid={validated && !NAME_PATTERN.test(item.name)}
        feedback="Название должно содержать от 1 до 50 символов" required />
      <TextArea name='description' label='Описание модели' value={item.description}
        onChange={handleChange} className='mb-4' />
      <div className="form-label">{id ? 'Тип элемента структуры' : 'Тип элемента структуры (обязателен к выбору)'}</div>
      <SearchableSelect
        apiService={StructureElementTypesApiService}
        placeholder="Выберите тип элемента"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
        isMulti={false}
        isPagination={false}
        className='mb-4'
        initialValue={initialType}
        onChange={handleTypeChange}
        required={true}
      />
      <div className="form-label">{id ? 'Производитель' : 'Производитель (обязателен к выбору)'}</div>
      <SearchableSelect
        apiService={ManufacturersGetService}
        placeholder="Выберите производителя"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
        isMulti={false}
        isPagination={false}
        className='mb-4'
        initialValue={initialManufacturer}
        onChange={handleManufacturerChange}
        required={true}
      />
      <Input name='workEfficiency' label='Эффективность работы' value={item.workEfficiency}
        onChange={handleChange} className='mb-4' type='text' pattern={NUMBER_PATTERN.source}
        isInvalid={validated && !NUMBER_PATTERN.test(item.workEfficiency)}
        feedback="Введите целое число от 0 до 100" required />
      <Input name='reliability' label='Надежность' value={item.reliability}
        onChange={handleChange} className='mb-4' type='text' pattern={NUMBER_PATTERN.source}
        isInvalid={validated && !NUMBER_PATTERN.test(item.reliability)}
        feedback="Введите целое число от 0 до 100" required />
      <Input name='energyEfficiency' label='Энергоэффективность' value={item.energyEfficiency}
        onChange={handleChange} className='mb-4' type='text' pattern={NUMBER_PATTERN.source}
        isInvalid={validated && !NUMBER_PATTERN.test(item.energyEfficiency)}
        feedback="Введите целое число от 0 до 100" required />
      <Input name='userFriendliness' label='Удобство использования' value={item.userFriendliness}
        onChange={handleChange} className='mb-4' type='text' pattern={NUMBER_PATTERN.source}
        isInvalid={validated && !NUMBER_PATTERN.test(item.userFriendliness)}
        feedback="Введите целое число от 0 до 100" required />
      <Input name='durability' label='Долговечность' value={item.durability}
        onChange={handleChange} className='mb-4' type='text' pattern={NUMBER_PATTERN.source}
        isInvalid={validated && !NUMBER_PATTERN.test(item.durability)}
        feedback="Введите целое число от 0 до 100" required />
      <Input name='aestheticQualities' label='Эстетические качества' value={item.aestheticQualities}
        onChange={handleChange} className='mb-4' type='text' pattern={NUMBER_PATTERN.source}
        isInvalid={validated && !NUMBER_PATTERN.test(item.aestheticQualities)}
        feedback="Введите целое число от 0 до 100" required />
    </>
  );
};

StructureElementModelsItemForm.propTypes = {
  id: PropTypes.string,
  item: PropTypes.object,
  handleChange: PropTypes.func,
  validated: PropTypes.bool,
  handleTypeChange: PropTypes.func,
  handleManufacturerChange: PropTypes.func,
  initialManufacturer: PropTypes.object,
  initialType: PropTypes.object
};

export default StructureElementModelsItemForm;