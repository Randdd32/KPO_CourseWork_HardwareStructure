import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import useDeviceItem from './hooks/DevicesItemHook';
import { DeviceModelsGetService } from '../deviceModels/service/DeviceModelsApiService';
import { getDeviceImage } from './deviceImages';
import LoadingElement from '../utils/LoadingElement';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faTimesCircle, faInfoCircle, faListAlt } from '@fortawesome/free-solid-svg-icons';
import { Button } from 'react-bootstrap';
import formatDateTime from '../utils/Formatter';
import DeviceCharacteristicsModal from '../modal/DeviceCharacteristicsModal';
import DeviceStructureModal from '../modal/DeviceStructureModal';
import './deviceDetails.css';

const DeviceDetailsPage = ({ id }) => {
  const { item } = useDeviceItem(Number(id));
  const [deviceModelItem, setDeviceModelItem] = useState(null);

  const [showCharacteristicsModal, setShowCharacteristicsModal] = useState(false);
  const [showStructureModal, setShowStructureModal] = useState(false);

  useEffect(() => {
    const fetchModelItem = async () => {
      if (item?.modelId) {
        const data = await DeviceModelsGetService.get(item.modelId);
        setDeviceModelItem(data);
      }
    };
    fetchModelItem();
  }, [item?.modelId]);

  if (!item || !item.id) {
    return (
      <LoadingElement />
    );
  }

  const deviceImage = getDeviceImage(item.typeName);

  const statusClass = item.isWorking ? 'text-success' : 'text-danger';
  const statusIcon = item.isWorking ? faCheckCircle : faTimesCircle;
  const statusText = item.isWorking ? 'Работает' : 'Не работает';

  return (
    <>
      <div className="row gy-3 device-detail-main-row mt-3">
        <div className="col-md-6 col-lg-3 mt-0 px-0 device-image-column">
          <div className="block d-flex justify-content-center justify-content-md-start">
            <img
              src={deviceImage}
              className="img-fluid device-detail-image"
              alt={item.typeName || 'Изображение устройства'}
              onError={(e) => { e.target.onerror = null; e.target.src = getDeviceImage('default'); }}
            />
          </div>
        </div>
        <div className="col-md-6 col-lg-5 mt-3 mt-md-0 px-0 device-characteristics-column">
          <div className="block d-flex flex-column align-items-center align-items-md-start ms-md-4">
            <p className="mb-2 mb-3 device-serial-number">
              {item.serialNumber || 'Точных данных о серийном номере нет'}
            </p>
            <dl className="row mt-2 gx-5 gy-2 text-center text-sm-start device-characteristics-list">

              <dt className="col-sm-6 mt-0">Дата покупки</dt>
              <dd className="col-sm-6 mt-0 pe-0">{formatDateTime(item.purchaseDate) || 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Истечение гарантии</dt>
              <dd className="col-sm-6 pe-0">{formatDateTime(item.warrantyExpiryDate) || 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Цена</dt>
              <dd className="col-sm-6 pe-0">{item.price !== null ? `${item.price} ₽` : 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Ответственный сотрудник</dt>
              <dd className="col-sm-6 pe-0">{item.employeeFullName || 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Тип</dt>
              <dd className="col-sm-6 pe-0">{item.typeName || 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Производитель</dt>
              <dd className="col-sm-6 pe-0">{item.manufacturerName || 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Модель</dt>
              <dd className="col-sm-6 pe-0">{item.modelName || 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Помещение</dt>
              <dd className="col-sm-6 pe-0">{item.locationName || 'Точных данных нет'}</dd>

              <dt className="col-sm-6">Здание</dt>
              <dd className="col-sm-6 pe-0">{item.buildingName || 'Точных данных нет'}</dd>
            </dl>
            <div className="d-flex flex-column flex-md-row gap-2 mt-2 w-100 justify-content-center justify-content-md-start">
              <Button
                variant="outline-primary"
                onClick={() => setShowCharacteristicsModal(true)}
                disabled={!deviceModelItem || !deviceModelItem.id}
                className="device-action-button mt-0 me-md-3"
              >
                <FontAwesomeIcon icon={faInfoCircle} className='me-2' />
                Характеристики модели
              </Button>
              <Button
                variant="outline-info"
                onClick={() => setShowStructureModal(true)}
                disabled={!deviceModelItem || !deviceModelItem.id}
                className="device-action-button mt-2 mt-md-0"
              >
                <FontAwesomeIcon icon={faListAlt} className='me-2' />
                Структура устройства
              </Button>
            </div>
          </div>
        </div>

        <div className="col-lg-4 mt-0 px-0 d-none d-lg-block device-status-column">
          <div className="block d-flex flex-column align-items-center align-items-lg-end">
            <div className="border border-secondary-subtle rounded-1 p-3 device-status-container">
              <div className="d-flex align-items-center mb-3">
                <FontAwesomeIcon icon={statusIcon} className={`me-3 ${statusClass}`} size="2x" />
                <p className={`mb-0 fw-bold ${statusClass}`}>
                  {statusText}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {item.furtherInformation && item.furtherInformation.trim() !== '' && (
        <div className="row mt-4 mb-1 mb-md-3 device-description-row">
          <div className="col px-0">
            <div className="block">
              <p className="device-description-h mb-2">
                Дополнительная информация
              </p>
              <p className="device-description-text">
                {item.furtherInformation}
              </p>
            </div>
          </div>
        </div>
      )}

      <div className="col px-0 d-lg-none device-status-mobile-column mb-3">
        <div className="block d-flex flex-column align-items-center border border-secondary-subtle rounded-1 p-2">
          <div className="d-flex align-items-center mb-3">
            <FontAwesomeIcon icon={statusIcon} className={`me-3 ${statusClass}`} size="2x" />
            <p className={`mb-0 fw-bold ${statusClass}`}>
              {statusText}
            </p>
          </div>
        </div>
      </div>

      {deviceModelItem && (
        <DeviceCharacteristicsModal
          show={showCharacteristicsModal}
          onClose={() => setShowCharacteristicsModal(false)}
          characteristics={deviceModelItem}
        />
      )}

      {deviceModelItem && (
        <DeviceStructureModal
          show={showStructureModal}
          onClose={() => setShowStructureModal(false)}
          structureElements={deviceModelItem.structureElements}
        />
      )}
    </>
  );
};

DeviceDetailsPage.propTypes = {
  id: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.number
  ]).isRequired
};

export default DeviceDetailsPage;