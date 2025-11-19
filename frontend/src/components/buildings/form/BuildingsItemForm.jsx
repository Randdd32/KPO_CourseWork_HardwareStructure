import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';

const BuildingsItemForm = ({ item, handleChange, validated }) => {
  const NAME_PATTERN = /^.{1,30}$/;
  const ADDRESS_PATTERN = /^.{1,255}$/;

  return (
    <>
      <Input name='id' label='ID' value={item.id}
        className='mb-4' type='text' readOnly disabled />
      <Input name='name' label='Название здания' value={item.name} onChange={handleChange}
        className='mb-4' type='text' pattern={NAME_PATTERN.source} isInvalid={validated && !NAME_PATTERN.test(item.name)}
        feedback="Название здания должно содержать от 1 до 30 символов" required />
      <Input name='address' label='Адрес' value={item.address} onChange={handleChange}
        className='mb-4' type='text' pattern={ADDRESS_PATTERN.source} isInvalid={validated && !ADDRESS_PATTERN.test(item.address)}
        feedback="Адрес должен быть длиной от 1 до 255 символов" required />
    </>
  );
};

BuildingsItemForm.propTypes = {
  item: PropTypes.object,
  handleChange: PropTypes.func,
  validated: PropTypes.bool
};

export default BuildingsItemForm;