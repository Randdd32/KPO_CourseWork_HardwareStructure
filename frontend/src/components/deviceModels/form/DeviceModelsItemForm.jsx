import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';
import TextArea from '../../input/TextArea.jsx'
import SearchableSelect from '../../input/SearchableSelect.jsx';
import SelectWithCount from '../../input/SelectWithCount.jsx'
import { ManufacturersGetService } from '../../manufacturers/service/ManufacturersApiService.js';
import { DeviceTypesGetService } from '../../deviceTypes/service/DeviceTypesApiService.js';
import StructureElementModelsApiService from '../../structureElementModels/service/StructureElementModelsApiService.js'

const DeviceModelsItemForm = ({
  id,
  item,
  handleChange,
  validated,
  handleManufacturerChange,
  handleDeviceTypeChange,
  handleStructureElementsChange,
  initialManufacturer,
  initialDeviceType,
  initialStructureElements
}) => {
  const NAME_PATTERN = /^.{1,50}$/;

  return (
    <>
      <Input name='id' label='ID' value={item.id}
        className='mb-4' type='text' readOnly disabled />
      <Input name='name' label='Название модели' value={item.name} onChange={handleChange}
        className='mb-4' type='text' pattern={NAME_PATTERN.source}
        isInvalid={validated && !NAME_PATTERN.test(item.name)}
        feedback="Название модели должно содержать от 1 до 50 символов" required />
      <TextArea name='description' label='Описание модели' value={item.description}
        onChange={handleChange} className='mb-4' />
      <div className="form-label">{id ? 'Тип устройства' : 'Тип устройства (обязателен к выбору)'}</div>
      <SearchableSelect
        apiService={DeviceTypesGetService}
        placeholder="Выберите тип устройства"
        displayField="name"
        valueField="id"
        buildQuery={(term) => `?name=${encodeURIComponent(term)}`}
        isMulti={false}
        isPagination={false}
        className='mb-4'
        initialValue={initialDeviceType}
        onChange={handleDeviceTypeChange}
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
      <div className="form-label">Структура</div>
      <SelectWithCount
        apiService={StructureElementModelsApiService}
        placeholder="Выберите элементы структуры"
        initialValue={initialStructureElements}
        onChange={handleStructureElementsChange}
        className='mb-4'
      />
      <Input name='workEfficiency' label='Эффективность работы' value={item.workEfficiency}
        className='mb-4' type='text' readOnly disabled />
      <Input name='reliability' label='Надежность' value={item.reliability}
        className='mb-4' type='text' readOnly disabled />
      <Input name='energyEfficiency' label='Энергоэффективность' value={item.energyEfficiency}
        className='mb-4' type='text' readOnly disabled />
      <Input name='userFriendliness' label='Удобство использования' value={item.userFriendliness}
        className='mb-4' type='text' readOnly disabled />
      <Input name='durability' label='Долговечность' value={item.durability}
        className='mb-4' type='text' readOnly disabled />
      <Input name='aestheticQualities' label='Эстетические качества' value={item.aestheticQualities}
        className='mb-4' type='text' readOnly disabled />
    </>
  );
};

DeviceModelsItemForm.propTypes = {
  id: PropTypes.string,
  item: PropTypes.object,
  handleChange: PropTypes.func,
  validated: PropTypes.bool,
  handleManufacturerChange: PropTypes.func,
  handleDeviceTypeChange: PropTypes.func,
  handleStructureElementsChange: PropTypes.func,
  initialManufacturer: PropTypes.object,
  initialDeviceType: PropTypes.object,
  initialStructureElements: PropTypes.array
};

export default DeviceModelsItemForm;