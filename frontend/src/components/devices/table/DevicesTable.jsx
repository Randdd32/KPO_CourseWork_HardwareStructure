import PropTypes from 'prop-types';

const DevicesTable = ({ children }) => {
  return (
    <div className="col-12 px-0">
      <div className="block table-responsive">
        <table className="table table-bordered align-middle">
          <thead>
            <tr>
              <th scope="col" className="text-center align-top">ID</th>
              <th scope="col" className="text-center align-top">Серийный номер</th>
              <th scope="col" className="text-center align-top">Дата покупки</th>
              <th scope="col" className="text-center align-top">Истечение гарантии</th>
              <th scope="col" className="text-center align-top">Цена</th>
              <th scope="col" className="text-center align-top">Модель</th>
              <th scope="col" className="text-center align-top">Помещение</th>
              <th scope="col" className="text-center align-top">Ответственный сотрудник</th>
              <th scope="col" className="text-center align-top">Состояние</th>
              <th scope="col" className='buttons-col'></th>
            </tr>
          </thead>
          <tbody>{children}</tbody>
        </table>
      </div>
    </div>
  );
};

DevicesTable.propTypes = {
  children: PropTypes.node,
};

export default DevicesTable;