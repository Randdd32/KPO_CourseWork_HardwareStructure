import { useContext } from 'react';
import {
  Link, NavLink, useNavigate
} from 'react-router-dom';
import Logo from '../../assets/icons/logo.png';
import User from '../../assets/icons/user.png';
import { Navbar, Nav, Container, Form, FormControl, Button, NavDropdown } from 'react-bootstrap';
import { observer } from "mobx-react-lite";
import SearchContext from './SearchContext.jsx';
import StoreContext from '../users/StoreContext.jsx';
import './navigation.css';
import EntitiesDropdown from './EntitiesDropdown.jsx';

const Navigation = observer(({ routes }) => {
  const indexPageLink = routes.filter((route) => route.index === false).shift();
  const navigate = useNavigate();
  const { store } = useContext(StoreContext);
  const { searchValue, updateSearchValue } = useContext(SearchContext);

  const handleLogout = async () => {
    await store.logout();
    navigate('/', { replace: false });
    window.location.reload();
  };

  return (
    <header className="w-100 position-sticky top-0 z-3">
      <Navbar expand="lg" className="px-2 navbar">
        <Container fluid="md" className="d-flex flex-wrap align-items-center">
          <Navbar.Brand as={Link} to={indexPageLink?.path ?? '/'} className="order-0">
            <img src={Logo} alt="Logo" height="40" className="me-3" />
            <span className='me-4 text-white'>
              Учет аппаратных средств
            </span>
          </Navbar.Brand>

          <Navbar.Toggle aria-controls="navbarNav" className="order-2 order-lg-2 ms-3" />

          {store.isAuth && (
            <Nav className="ms-3 d-none d-lg-flex order-lg-3">
              <img
                src={User}
                alt="User"
                width="32"
                height="32"
                className="me-2"
              />
              <NavDropdown title={store.user.email} align="end">
                <NavDropdown.Item as={Link} to={`/user/${store.user.id}`}>
                  Профиль
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                  Выйти из аккаунта
                </NavDropdown.Item>
              </NavDropdown>
            </Nav>
          )}

          <div className="search-wrapper d-flex flex-grow-1 order-1 order-lg-1 my-2 my-lg-0">
            <Form className="d-flex w-100">
              <FormControl
                type="search"
                placeholder="Поиск устройств..."
                className="me-2 flex-grow-1"
                aria-label="Search"
                value={searchValue}
                onChange={(e) => updateSearchValue(e.target.value)}
                maxLength={100}
              />
              <Button variant="outline-light" type="submit">Поиск</Button>
            </Form>
          </div>

          <Navbar.Collapse id="navbarNav" className="order-3 w-100">
            <Nav className="me-auto mt-2 mb-lg-0">
              <NavLink to="/" className="nav-link ps-0">Главная</NavLink>
              {store.isAdmin && (
                <EntitiesDropdown />
              )}
              {store.isAuth && (
                <div className="d-lg-none">
                  <NavLink to={`/user/${store.user.id}`} className="nav-link">Профиль</NavLink>
                  <Nav.Link onClick={handleLogout}>Выйти из аккаунта</Nav.Link>
                </div>
              )}
            </Nav>
          </Navbar.Collapse>

        </Container>
      </Navbar>
    </header >
  );
});

export default Navigation;