import PropTypes from 'prop-types';
import { Form } from 'react-bootstrap';

const Input = ({
  name, label, value, onChange, className, isInvalid, feedback, ...rest
}) => {
  return (
    <Form.Group className={className || ''} controlId={name}>
      {label && <Form.Label>{label}</Form.Label>}
      <Form.Control
        name={name || ''}
        value={value ?? ''}
        onChange={onChange}
        isInvalid={isInvalid}
        className="rounded-2"
        {...rest}
      />
      {feedback && (
        <Form.Control.Feedback type="invalid">
          {feedback}
        </Form.Control.Feedback>
      )}
    </Form.Group>
  );
};

Input.propTypes = {
  name: PropTypes.string,
  label: PropTypes.string,
  value: PropTypes.string,
  onChange: PropTypes.func,
  className: PropTypes.string,
  isInvalid: PropTypes.bool,
  feedback: PropTypes.string,
};

export default Input;