import { Link, useNavigate } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useBuildingsDeleteModal from '../hooks/BuildingsDeleteModalHook.js';
import useBuildings from '../hooks/BuildingHook.js';
import BuildingsTable from './BuildingsTable.jsx';
import BuildingsTableRow from './BuildingsTableRow.jsx';

const Buildings = () => {
  const { buildings, handleBuildingsChange } = useBuildings();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useBuildingsDeleteModal(handleBuildingsChange);

  const navigate = useNavigate();

  const showEditPage = (id) => {
    navigate(`/admin/building/${id}`);
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Здания
        </div>
      </div>
      <BuildingsTable>
        {
          buildings.map((building) =>
            <BuildingsTableRow
              key={building.id}
              building={building}
              onDelete={() => showDeleteModal(building.id)}
              onEditInPage={() => showEditPage(building.id)}
            />)
        }
      </BuildingsTable>
      <div className="col-12 px-0 mb-2">
        <div className="block mb-4">
          <Link to="/admin/building" className="btn btn-success fw-semibold">Добавить здание</Link>
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

export default Buildings;
