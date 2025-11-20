import { useEffect } from 'react';
import { Alert, Button, Container } from 'react-bootstrap';
import { useNavigate, useRouteError } from 'react-router-dom';
import OOPS from '../assets/icons/oops.png'

const ErrorPage = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  const navigate = useNavigate();
  const error = useRouteError();

  return (
    <Container fluid="md" className="d-flex flex-column justify-content-center align-items-center min-vh-100 p-3">
      <div className="w-100" style={{ maxWidth: '600px' }}>
        <img src={OOPS} className="mb-3 mx-auto d-block" alt="OOPS" width="256" height="256" />
        <Alert variant="danger" className="mb-3">
          {error?.message ?? 'Страница не найдена'}
        </Alert>
        <Button
          variant="secondary"
          style={{ maxWidth: '200px' }}
          onClick={() => navigate(-1)}
        >
          Назад
        </Button>
      </div>
    </Container>
  );
};

export default ErrorPage;