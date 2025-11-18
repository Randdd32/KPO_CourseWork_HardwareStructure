import { Link, NavLink } from 'react-router-dom';
import VK from '../../assets/icons/vk.png';
import Telegram from '../../assets/icons/telegram.png';
import Odnoklassniki from '../../assets/icons/odnoklassniki.png';
import { Container, Row, Col, Nav } from 'react-bootstrap';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './footer.css';

const Footer = () => {
  const year = new Date().getFullYear();

  return (
    <footer className="footer pt-4 pb-4">
      <Container fluid="md">
        <Row>
          <Col md={3} className="mb-3 mb-md-0 text-start">
            <h5>О компании</h5>
            <p>Здесь должно быть описание компании. Здесь должно быть описание компании.</p>
          </Col>

          <Col md={3} className="mb-3 mb-md-0 text-start">
            <h5>Важные ссылки</h5>
            <Nav className="flex-column">
              <NavLink to="/" className="text-white nav-link-custom">
                <i className="bi bi-house me-2"></i> Главная страница
              </NavLink>
              <NavLink to="/support" className="text-white nav-link-custom">
                <i className="bi bi-envelope me-2"></i> Помощь
              </NavLink>
            </Nav>
          </Col>

          <Col md={3} className="mb-3 mb-md-0 text-start">
            <h5>Контакты</h5>
            <ul className="list-unstyled mb-0">
              <li><i className="bi bi-geo-alt-fill me-2"></i> "Адрес компании"</li>
              <li><i className="bi bi-telephone-fill me-2"></i> "Номер техподдержки"</li>
              <li><i className="bi bi-envelope-fill me-2"></i> "Электронная почта"</li>
            </ul>
          </Col>

          <Col md={3} className="mb-0 text-start">
            <h5>Социальные сети</h5>
            <ul className="list-inline mb-3">
              <li className="list-inline-item">
                <Link to="/" className="text-white">
                  <img src={VK} alt="VK" width="28" height="28" />
                </Link>
              </li>
              <li className="list-inline-item">
                <Link to="/" className="text-white">
                  <img src={Odnoklassniki} alt="Odnoklassniki" width="28" height="28" />
                </Link>
              </li>
              <li className="list-inline-item">
                <Link to="/" className="text-white">
                  <img src={Telegram} alt="Telegram" width="28" height="28" />
                </Link>
              </li>
            </ul>
          </Col>
        </Row>

        <hr className="bg-light" />

        <Row>
          <Col md={12} className="text-center">
            <p className="mb-0">&copy; {year} "Название компании". Все права защищены.</p>
          </Col>
        </Row>
      </Container>
    </footer>
  );
};

export default Footer;