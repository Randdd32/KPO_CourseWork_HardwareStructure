import PropTypes from 'prop-types';
import { Button, ButtonGroup } from 'react-bootstrap';
import { FaEdit, FaTrash } from 'react-icons/fa';
import formatDateTime from '../../utils/Formatter';

const DevicesTableRow = ({ device, onDelete, onEditInPage }) => {
  return (
    <tr>
      <td className="text-center">{device.id}</td>
      <td>{device.serialNumber}</td>
      <td>{formatDateTime(device.purchaseDate)}</td>
      <td>{formatDateTime(device.warrantyExpiryDate)}</td>
      <td>{device.price}</td>
      <td>{device.modelName}</td>
      <td>{device.locationName}</td>
      <td>{device.employeeInfo}</td>
      <td className="text-center">{device.isWorking ? 'Работает' : 'Сломано'}</td>
      <td>
        <ButtonGroup aria-label="Действия">
          <Button
            variant="success"
            onClick={onEditInPage}
            className="me-2 rounded-1"
            size="sm"
          >
            <FaEdit />
          </Button>
          <Button
            variant="danger"
            className='rounded-1'
            onClick={onDelete}
            size="sm"
          >
            <FaTrash />
          </Button>
        </ButtonGroup>
      </td>
    </tr>
  );
};

DevicesTableRow.propTypes = {
  device: PropTypes.object,
  onDelete: PropTypes.func,
  onEditInPage: PropTypes.func
};

export default DevicesTableRow;