import PropTypes from 'prop-types';

const BuildingsTable = ({ children }) => {
  return (
    <div className="col-12 px-0">
      <div className="block table-responsive">
        <table className="table table-bordered align-middle">
          <thead>
            <tr>
              <th scope="col" className="text-center align-top">ID</th>
              <th scope="col" className="text-center align-top">Название здания</th>
              <th scope="col" className="text-center align-top">Адрес</th>
              <th scope="col" className='buttons-col'></th>
            </tr>
          </thead>
          <tbody>
            {children}
          </tbody>
        </table>
      </div>
    </div>
  );
};

BuildingsTable.propTypes = {
  children: PropTypes.node,
};

export default BuildingsTable;