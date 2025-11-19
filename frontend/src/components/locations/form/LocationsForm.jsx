import PropTypes from 'prop-types';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import useLocationItemForm from '../hooks/LocationsItemFormHook.js';
import LocationItemForm from './LocationsItemForm.jsx';

const LocationsForm = ({ id }) => {
  const navigate = useNavigate();

  const {
    item,
    validated,
    handleSubmit,
    handleChange,
    handleBuildingChange,
    handleDepartmentChange,
    handleEmployeeIdsChange,
    initialBuilding,
    initialDepartment,
    initialEmployees
  } = useLocationItemForm(id);

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
      <Form className="col-lg-11 col-xl-10" noValidate validated={validated} onSubmit={onSubmit}>
        <div className="d-flex justify-content-center fs-2 fw-bold admin-title mt-2">Помещение</div>
        <LocationItemForm
          id={id}
          item={item}
          handleChange={handleChange}
          validated={validated}
          handleBuildingChange={handleBuildingChange}
          handleDepartmentChange={handleDepartmentChange}
          handleEmployeeIdsChange={handleEmployeeIdsChange}
          initialBuilding={initialBuilding}
          initialDepartment={initialDepartment}
          initialEmployees={initialEmployees}
        />
        <Form.Group className="d-flex flex-column flex-md-row justify-content-start mb-3">
          <Button className="edit-btn add-btn w-100 w-md-25 mb-2 mb-md-0 me-md-3 fw-semibold" type="submit" variant="success">
            Сохранить
          </Button>
          <Button className="edit-btn w-100 w-md-25 mt-1 mt-md-0 ms-md-2 fw-semibold" variant="secondary" onClick={onBack}>
            Отмена
          </Button>
        </Form.Group>
      </Form>
    </div>
  );
};

LocationsForm.propTypes = {
  id: PropTypes.string,
};

export default LocationsForm;