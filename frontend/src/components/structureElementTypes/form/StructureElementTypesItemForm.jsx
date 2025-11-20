import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';

const StructureElementTypesItemForm = ({ item, handleChange, validated }) => {
  const NAME_PATTERN = /^.{1,50}$/;

  return (
    <>
      <Input name='id' label='ID' value={item.id}
        className='mb-4' type='text' readOnly disabled />
      <Input name='name' label='Название типа структурного элемента' value={item.name} onChange={handleChange}
        className='mb-4' type='text' pattern={NAME_PATTERN.source} isInvalid={validated && !NAME_PATTERN.test(item.name)}
        feedback="Название типа элемента структуры должно содержать от 1 до 50 символов" required />
    </>
  );
};

StructureElementTypesItemForm.propTypes = {
  item: PropTypes.object,
  handleChange: PropTypes.func,
  validated: PropTypes.bool
};

export default StructureElementTypesItemForm;