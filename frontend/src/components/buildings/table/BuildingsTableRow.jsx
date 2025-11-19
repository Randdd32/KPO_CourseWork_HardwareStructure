import PropTypes from 'prop-types';
import { Button, ButtonGroup } from 'react-bootstrap';
import { FaEdit, FaTrash } from 'react-icons/fa';

const BuildingsTableRow = ({
  building, onDelete, onEditInPage,
}) => {
  return (
    <tr>
      <td className="text-center">{building.id}</td>
      <td>{building.name}</td>
      <td>{building.address}</td>
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

BuildingsTableRow.propTypes = {
  index: PropTypes.number,
  building: PropTypes.object,
  onDelete: PropTypes.func,
  onEditInPage: PropTypes.func,
};

export default BuildingsTableRow;