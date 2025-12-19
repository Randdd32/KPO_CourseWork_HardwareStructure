import PropTypes from 'prop-types';
import { Button, Modal, Tooltip, OverlayTrigger } from 'react-bootstrap';
import { createPortal } from 'react-dom';
import './deviceModals.css';

const DeviceStructureModal = ({ show, onClose, structureElements }) => {
  return createPortal(
    <Modal show={show} onHide={onClose} size="lg" centered contentClassName="border-0 shadow-lg rounded-4">
      <Modal.Header closeButton>
        <Modal.Title>Структура устройства</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {structureElements && structureElements.length > 0 ? (
          <div className="table-responsive">
            <table className="table table-bordered table-hover mb-0">
              <thead>
                <tr>
                  <th className='text-center align-top'>Название</th>
                  <th className='text-center align-top'>Производитель</th>
                  <th className='text-center align-top'>Описание</th>
                  <th className='text-center align-top'>Тип</th>
                  <th className='text-center align-top'>Количество</th>
                </tr>
              </thead>
              <tbody>
                {structureElements.map((element) => (
                  <tr key={element.structureElement.id}>
                    <td>{element.structureElement.name || 'Н/Д'}</td>
                    <td>{element.structureElement.manufacturerName || 'Н/Д'}</td>
                    <td>
                      <OverlayTrigger
                        placement="top"
                        overlay={<Tooltip>{element.structureElement.description || 'Описание отсутствует'}</Tooltip>}
                      >
                        <span>
                          {element.structureElement.description ?
                            (element.structureElement.description.length > 50 ?
                              `${element.structureElement.description.substring(0, 47)}...` :
                              element.structureElement.description)
                            : 'Н/Д'}
                        </span>
                      </OverlayTrigger>
                    </td>
                    <td>{element.structureElement.typeName || 'Н/Д'}</td>
                    <td className='text-center align-middle'>{element.count || 'Н/Д'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <p>Информация о структуре устройства отсутствует.</p>
        )}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>Закрыть</Button>
      </Modal.Footer>
    </Modal>,
    document.body
  );
};

DeviceStructureModal.propTypes = {
  show: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
  structureElements: PropTypes.arrayOf(PropTypes.shape({
    structureElement: PropTypes.shape({
      name: PropTypes.string,
      manufacturerName: PropTypes.string,
      description: PropTypes.string,
      typeName: PropTypes.string,
    }),
    count: PropTypes.number,
  })),
};

export default DeviceStructureModal;