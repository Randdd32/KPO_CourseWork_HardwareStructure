import PropTypes from 'prop-types';
import { Button, ButtonGroup } from 'react-bootstrap';
import { FaEdit, FaTrash } from 'react-icons/fa';

const DeviceModelsTableRow = ({
  deviceModel, onDelete, onEditInPage,
}) => {
  return (
    <tr>
      <td className="text-center">{deviceModel.id}</td>
      <td>{deviceModel.name}</td>
      <td>{deviceModel.typeName}</td>
      <td>{deviceModel.manufacturerName}</td>
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

DeviceModelsTableRow.propTypes = {
  deviceModel: PropTypes.object,
  onDelete: PropTypes.func,
  onEditInPage: PropTypes.func
};

export default DeviceModelsTableRow;