import PropTypes from 'prop-types';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import useStructureElementTypeItemForm from '../hooks/StructureElementTypesItemFormHook.js';
import StructureElementTypesItemForm from './StructureElementTypesItemForm.jsx';

const StructureElementTypesForm = ({ id }) => {
  const navigate = useNavigate();

  const {
    item,
    validated,
    handleSubmit,
    handleChange,
  } = useStructureElementTypeItemForm(id);

  const onBack = () => {
    navigate(-1);
  };

  const onSubmit = async (event) => {
    if (await handleSubmit(event)) {
      onBack();
    }
  };

  return (
    <div className="row justify-content-center">
      <Form className='col-lg-11 col-xl-10' noValidate validated={validated} onSubmit={onSubmit}>
        <div className='d-flex justify-content-center fs-2 fw-bold admin-title mt-2'>Тип элемента структуры</div>
        <StructureElementTypesItemForm item={item} handleChange={handleChange} validated={validated} />
        <Form.Group className="d-flex flex-column flex-md-row justify-content-start mb-3">
          <Button className="edit-btn add-btn w-100 w-md-25 mb-2 mb-md-0 me-md-3 fw-semibold" type="submit" variant="success">
            Сохранить
          </Button>
          <Button className="edit-btn w-100 w-md-25 mt-1 mt-md-0 ms-md-2 fw-semibold" variant="secondary" onClick={() => onBack()}>
            Отмена
          </Button>
        </Form.Group>
      </Form>
    </div>
  );
};

StructureElementTypesForm.propTypes = {
  id: PropTypes.string
};

export default StructureElementTypesForm;