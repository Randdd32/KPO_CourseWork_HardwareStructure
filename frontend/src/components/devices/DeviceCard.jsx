import PropTypes from 'prop-types';
import './deviceCard.css';
import { Link } from 'react-router-dom';
import { getDeviceImage } from './deviceImages';
import LoadingElement from '../utils/LoadingElement';

const DeviceCard = ({ device }) => {
  if (!device) {
    return (
      <LoadingElement />
    );
  }

  const deviceImage = getDeviceImage(device.typeName);

  const statusTextColorClass = device.isWorking ? 'text-success' : 'text-secondary';

  return (
    <Link to={`/device-details/${device.id}`} className="text-decoration-none text-dark device-card-link">
      <div className="card shadow-sm device-card-grid-item">
        <div className="row g-0">
          <div className="col-4 border-end d-flex justify-content-center align-items-center p-3 device-card-image-col">
            <img
              src={deviceImage}
              alt={device.typeName || 'Устройство'}
              className="img-fluid device-card-image-custom"
              onError={(e) => { e.target.onerror = null; e.target.src = getDeviceImage('default'); }}
            />
          </div>
          <div className="col-8 d-flex flex-column">
            <div className="card-body p-3 d-flex flex-column flex-grow-1">
              <h6 className="card-subtitle mb-1 text-dark fw-bold">{device.serialNumber}</h6>
              <p className="card-text mb-1 text-muted">
                {device.typeName}: {device.modelName}
              </p>
              <p className="card-text mb-1 fw-bold flex-grow-1">
                <small className={statusTextColorClass}>
                  {device.isWorking ? 'Работает' : 'Не работает'}
                </small>
              </p>
              <h5 className="card-title mt-auto fw-bold">{device.price} ₽</h5>
            </div>
          </div>
        </div>
      </div>
    </Link>
  );
};

DeviceCard.propTypes = {
  device: PropTypes.object.isRequired
};

export default DeviceCard;