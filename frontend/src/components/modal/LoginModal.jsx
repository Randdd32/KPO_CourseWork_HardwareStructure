import { useState, useContext } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import { createPortal } from 'react-dom';
import { observer } from "mobx-react-lite";
import StoreContext from '../users/StoreContext';
import { PASSWORD_PATTERN_STRING, PASSWORD_MESSAGE } from "../utils/Constants"

const LoginModal = observer(({ show }) => {
  const [validated, setValidated] = useState(false);
  const [formData, setFormData] = useState({ email: '', password: '' });
  const { store } = useContext(StoreContext);

  const PASSWORD_PATTERN = new RegExp(PASSWORD_PATTERN_STRING);

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();

    if (form.checkValidity()) {
      await store.login(formData.email, formData.password);
      return;
    }
    setValidated(true);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  return createPortal(
    <Modal
      show={show}
      backdrop="static"
      keyboard={false}
      centered
      contentClassName="border-0 shadow-lg rounded-4"
    >
      <Modal.Body className="p-4">
        <div className="text-center mb-4">
          <h1 className="h3">Вход в систему</h1>
          <p className="text-muted mb-0">Войдите, чтобы получить доступ к своей учетной записи</p>
        </div>

        <Form noValidate validated={validated} onSubmit={handleSubmit}>
          <Form.Group controlId="email" className="mb-3">
            <Form.Label className="text-muted">Электронная почта</Form.Label>
            <Form.Control
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              className="rounded-3"
            />
            <Form.Control.Feedback type="invalid">
              Электронная почта должна быть введена в корректном формате
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId="password" className="mb-4">
            <Form.Label className="text-muted">Пароль</Form.Label>
            <Form.Control
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              pattern={PASSWORD_PATTERN_STRING}
              required
              className="rounded-3"
              isInvalid={
                validated && !PASSWORD_PATTERN.test(formData.password)
              }
            />
            <Form.Control.Feedback type="invalid">
              {PASSWORD_MESSAGE}
            </Form.Control.Feedback>
          </Form.Group>

          <div className="d-grid">
            <Button variant="dark" size="lg" type="submit" className="rounded-3">
              Войти в систему
            </Button>
          </div>
        </Form>
      </Modal.Body>
    </Modal>,
    document.body
  );
});

export default LoginModal;