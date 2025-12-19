import { useContext, useEffect } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import useUserItemForm from '../hooks/UsersItemFormHook.js';
import UserItemForm from './UsersItemForm.jsx';
import { observer } from "mobx-react-lite";
import StoreContext from '../StoreContext.jsx';

const UsersForm = observer(({ id }) => {
  const { store } = useContext(StoreContext);
  const navigate = useNavigate();

  const {
    item,
    validated,
    handleSubmit,
    handleChange,
    handleEmployeeChange,
    initialEmployee
  } = useUserItemForm(id);

  const onBack = () => {
    navigate(-1);
  };

  const onSubmit = async (event) => {
    if (await handleSubmit(event)) {
      onBack();
    }
  };

  useEffect(() => {
    const isEditingAdmin = item?.role === 'ADMIN' || item?.role === 'SUPER_ADMIN';
    const isNotSuperAdmin = !store.isSuperAdmin;

    if (isEditingAdmin && isNotSuperAdmin) {
      navigate(-1);
    }
  }, [item, store, navigate]);

  return (
    <div className="row justify-content-center">
      <Form className="col-lg-11 col-xl-10" noValidate validated={validated} onSubmit={onSubmit}>
        <div className="d-flex justify-content-center fs-2 fw-bold admin-title mt-2">Пользователь</div>
        <UserItemForm
          id={id}
          item={item}
          handleChange={handleChange}
          validated={validated}
          handleEmployeeChange={handleEmployeeChange}
          initialEmployee={initialEmployee}
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
});

export default UsersForm;