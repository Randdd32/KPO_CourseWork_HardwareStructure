import PropTypes from 'prop-types';
import Input from '../../input/Input.jsx';
import TextArea from '../../input/TextArea.jsx';

const PositionsItemForm = ({
  item,
  handleChange,
  validated
}) => {
  const NAME_PATTERN = /^.{1,50}$/;

  return (
    <>
      <Input
        name='id'
        label='ID'
        value={item.id}
        className='mb-4'
        type='text'
        readOnly
        disabled
      />
      <Input
        name='name'
        label='Название должности'
        value={item.name}
        onChange={handleChange}
        className='mb-4'
        type='text'
        pattern={NAME_PATTERN.source}
        isInvalid={validated && !NAME_PATTERN.test(item.name)}
        feedback="Название должно содержать от 1 до 50 символов"
        required
      />
      <TextArea
        name='description'
        label='Описание'
        value={item.description}
        onChange={handleChange}
        className='mb-4'
      />
    </>
  );
};

PositionsItemForm.propTypes = {
  item: PropTypes.object,
  handleChange: PropTypes.func,
  validated: PropTypes.bool
};

export default PositionsItemForm;