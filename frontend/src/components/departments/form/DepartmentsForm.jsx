import PropTypes from 'prop-types';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import useDepartmentsItemForm from '../hooks/DepartmentsItemFormHook.js';
import DepartmentsItemForm from './DepartmentsItemForm.jsx';

const DepartmentsForm = ({ id }) => {
  const navigate = useNavigate();

  const {
    item,
    validated,
    handleSubmit,
    handleChange,
    handlePositionIdsChange,
    initialPositions
  } = useDepartmentsItemForm(id);

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
        <div className='d-flex justify-content-center fs-2 fw-bold admin-title mt-2'>Отдел</div>
        <DepartmentsItemForm
          item={item}
          handleChange={handleChange}
          validated={validated}
          handlePositionsChange={handlePositionIdsChange}
          initialPositions={initialPositions}
        />
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

DepartmentsForm.propTypes = {
  id: PropTypes.string,
};

export default DepartmentsForm;