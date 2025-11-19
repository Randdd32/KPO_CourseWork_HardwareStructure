import PropTypes from 'prop-types';
import { Button, Modal } from 'react-bootstrap';
import { createPortal } from 'react-dom';

const ModalConfirm = ({
  show, title, message, onConfirm, onClose,
}) => {
  return createPortal(
    <Modal show={show} backdrop='static' centered onHide={() => onClose()}>
      <Modal.Header className='pt-2 pb-2 ps-3 pe-3' closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>

      <Modal.Body>
        {message}
      </Modal.Body>

      <Modal.Footer className='m-0 pt-2 pb-2 ps-3 pe-3 row justify-content-center'>
        <Button variant='dark' className='col-5 m-0 me-2 fw-semibold'
          onClick={() => onConfirm()}>
          Да
        </Button>
        <Button variant='secondary' className='col-5 m-0 ms-2 fw-semibold'
          onClick={() => onClose()}>
          Нет
        </Button>
      </Modal.Footer>
    </Modal>,
    document.body,
  );
};

ModalConfirm.propTypes = {
  show: PropTypes.bool,
  title: PropTypes.string,
  message: PropTypes.string,
  onConfirm: PropTypes.func,
  onClose: PropTypes.func,
};

export default ModalConfirm;