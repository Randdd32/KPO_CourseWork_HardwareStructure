import { useEffect, useContext } from 'react';
import { Alert, Button, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import StoreContext from '../users/StoreContext.jsx';
import { observer } from "mobx-react-lite";
import OOPS from '../../assets/icons/oops.png'

const ProtectedAdminRoute = observer(({ children }) => {
  const { store } = useContext(StoreContext);

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  const navigate = useNavigate();

  if (!store.isAdmin && !store.isSuperAdmin) {
    return (
      <Container fluid="md" className="d-flex flex-column justify-content-center align-items-center min-vh-100 p-3">
        <div className="w-100" style={{ maxWidth: '600px' }}>
          <img src={OOPS} className="mb-3 mx-auto d-block" alt="OOPS" width="256" height="256" />
          <Alert variant="danger" className="mb-3">
            Доступ к данной странице не разрешен.
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
  }

  return children;
});

export default ProtectedAdminRoute;