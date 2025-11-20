import PropTypes from 'prop-types';
import { Form } from 'react-bootstrap';

const TextArea = ({
  name, label, value, onChange, className, ...rest
}) => {
  return (
    <Form.Group className={className || ''} controlId={name}>
      <Form.Label>{label}</Form.Label>
      <Form.Control as="textarea" rows="3" name={name || ''} value={value || ''}
        onChange={onChange} {...rest} className="rounded-2" />
    </Form.Group>
  );
};

TextArea.propTypes = {
  name: PropTypes.string,
  label: PropTypes.string,
  value: PropTypes.string,
  onChange: PropTypes.func,
  className: PropTypes.string,
};

export default TextArea;