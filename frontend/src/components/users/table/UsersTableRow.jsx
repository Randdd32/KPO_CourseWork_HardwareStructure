import PropTypes from 'prop-types';
import { Button } from 'react-bootstrap';

const UsersTableRow = ({ user, onDelete, onEditInPage }) => {
  const isTargetSuperAdmin = user.role === 'Супер-администратор';

  return (
    <tr>
      <td className="text-center">{user.id}</td>
      <td>{user.email}</td>
      <td>{user.phoneNumber}</td>
      <td>{user.role}</td>
      <td>{user.employeeFullName}</td>
      <td>
        {isTargetSuperAdmin ? null : (
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
        )}
      </td>
    </tr>
  );
};

UsersTableRow.propTypes = {
  user: PropTypes.object,
  onDelete: PropTypes.func,
  onEditInPage: PropTypes.func
};

export default UsersTableRow;