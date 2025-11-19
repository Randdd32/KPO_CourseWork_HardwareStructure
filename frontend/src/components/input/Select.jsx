import PropTypes from 'prop-types';
import { Form } from 'react-bootstrap';

const Select = ({
  values, name, label, value, onChange, className, ...rest
}) => {
  return (
    <Form.Group className={className || ''} controlId={name}>
      <Form.Label className='form-label'>{label}</Form.Label>
      <Form.Select name={name || ''} value={value || ''} onChange={onChange} {...rest}>
        {
          values.map((option) =>
            <option key={option.id} value={option.id} disabled={option.disabled}>
              {option.name}
            </option>)
        }
      </Form.Select>
    </Form.Group>
  );
};

Select.propTypes = {
  values: PropTypes.array,
  name: PropTypes.string,
  label: PropTypes.string,
  value: PropTypes.string,
  onChange: PropTypes.func,
  className: PropTypes.string,
};

export default Select;