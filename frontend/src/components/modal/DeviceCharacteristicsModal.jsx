import PropTypes from 'prop-types';
import { Button, Modal } from 'react-bootstrap';
import { createPortal } from 'react-dom';
import './deviceModals.css';

const DeviceCharacteristicsModal = ({ show, onClose, characteristics }) => {
  return createPortal(
    <Modal show={show} onHide={onClose} centered contentClassName="border-0 shadow-lg rounded-4">
      <Modal.Header closeButton>
        <Modal.Title>Характеристики модели устройства</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <ul className="device-model-characteristics-list">
          <li>
            <strong>Эффективность работы:</strong> <span className="characteristic-value">{characteristics.workEfficiency || 'Н/Д'}</span>
          </li>
          <li>
            <strong>Надежность:</strong> <span className="characteristic-value">{characteristics.reliability || 'Н/Д'}</span>
          </li>
          <li>
            <strong>Энергоэффективность:</strong> <span className="characteristic-value">{characteristics.energyEfficiency || 'Н/Д'}</span>
          </li>
          <li>
            <strong>Удобство использования:</strong> <span className="characteristic-value">{characteristics.userFriendliness || 'Н/Д'}</span>
          </li>
          <li>
            <strong>Долговечность:</strong> <span className="characteristic-value">{characteristics.durability || 'Н/Д'}</span>
          </li>
          <li>
            <strong>Эстетические качества:</strong> <span className="characteristic-value">{characteristics.aestheticQualities || 'Н/Д'}</span>
          </li>
        </ul>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>Закрыть</Button>
      </Modal.Footer>
    </Modal>,
    document.body
  );
};

DeviceCharacteristicsModal.propTypes = {
  show: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
  characteristics: PropTypes.shape({
    workEfficiency: PropTypes.number,
    reliability: PropTypes.number,
    energyEfficiency: PropTypes.number,
    userFriendliness: PropTypes.number,
    durability: PropTypes.number,
    aestheticQualities: PropTypes.number,
  }).isRequired,
};

export default DeviceCharacteristicsModal;