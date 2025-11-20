import { Link, useNavigate } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useManufacturersDeleteModal from '../hooks/ManufacturersDeleteModalHook.js';
import useManufacturers from '../hooks/ManufacturerHook.js';
import ManufacturersTable from './ManufacturersTable.jsx';
import ManufacturersTableRow from './ManufacturersTableRow.jsx';

const Manufacturers = () => {
  const { manufacturers, handleManufacturersChange } = useManufacturers();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useManufacturersDeleteModal(handleManufacturersChange);

  const navigate = useNavigate();

  const showEditPage = (id) => {
    navigate(`/admin/manufacturer/${id}`);
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Производители
        </div>
      </div>
      <ManufacturersTable>
        {
          manufacturers.map((manufacturer) =>
            <ManufacturersTableRow
              key={manufacturer.id}
              manufacturer={manufacturer}
              onDelete={() => showDeleteModal(manufacturer.id)}
              onEditInPage={() => showEditPage(manufacturer.id)}
            />)
        }
      </ManufacturersTable>
      <div className="col-12 px-0 mb-2">
        <div className="block mb-4">
          <Link to="/admin/manufacturer" className="btn btn-success fw-semibold">Добавить производителя</Link>
        </div>
      </div>
      <ModalConfirm
        show={isDeleteModalShow}
        onConfirm={handleDeleteConfirm}
        onClose={handleDeleteCancel}
        title='Удаление'
        message='Удалить элемент?' />
    </div>
  );
};

export default Manufacturers;