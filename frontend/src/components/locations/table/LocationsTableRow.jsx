import PropTypes from 'prop-types';
import { Button, ButtonGroup } from 'react-bootstrap';
import { FaEdit, FaTrash } from 'react-icons/fa';

const LocationsTableRow = ({ location, onDelete, onEditInPage }) => {
  return (
    <tr>
      <td className="text-center">{location.id}</td>
      <td>{location.name}</td>
      <td>{location.type}</td>
      <td>{location.buildingName}</td>
      <td>{location.departmentName}</td>
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

LocationsTableRow.propTypes = {
  location: PropTypes.object,
  onDelete: PropTypes.func,
  onEditInPage: PropTypes.func
};

export default LocationsTableRow;