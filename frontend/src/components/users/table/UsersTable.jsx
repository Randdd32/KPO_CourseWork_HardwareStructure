import PropTypes from 'prop-types';

const UsersTable = ({ children }) => {
  return (
    <div className="col-12 px-0">
      <div className="block table-responsive">
        <table className="table table-bordered align-middle">
          <thead>
            <tr>
              <th scope="col" className="text-center align-top">ID</th>
              <th scope="col" className="text-center align-top">Электронная почта</th>
              <th scope="col" className="text-center align-top">Номер телефона</th>
              <th scope="col" className="text-center align-top">Роль</th>
              <th scope="col" className="text-center align-top">Владелец аккаунта</th>
              <th scope="col" className='buttons-col'></th>
            </tr>
          </thead>
          <tbody>{children}</tbody>
        </table>
      </div>
    </div>
  );
};

UsersTable.propTypes = {
  children: PropTypes.node
};

export default UsersTable;