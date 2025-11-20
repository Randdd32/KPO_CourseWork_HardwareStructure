import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useDeviceModelsDeleteModal from '../hooks/DeviceModelsDeleteModalHook.js';
import useDeviceModels from '../hooks/DeviceModelHook.js';
import DeviceModelsTable from './DeviceModelsTable.jsx';
import DeviceModelsTableRow from './DeviceModelsTableRow.jsx';
import PaginationComponent from '../../pagination/Pagination.jsx';
import { useEffect } from 'react';
import { PAGE_SIZE } from '../../utils/Constants.js';

const DeviceModels = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const pageParam = parseInt(searchParams.get('page')) || 1;

  const {
    deviceModels,
    totalPages,
    getDeviceModels,
    deviceModelsRefresh,
    handleDeviceModelsChange
  } = useDeviceModels();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useDeviceModelsDeleteModal(handleDeviceModelsChange);

  useEffect(() => {
    getDeviceModels(pageParam, PAGE_SIZE);
  }, [pageParam, deviceModelsRefresh]);

  const showEditPage = (id) => {
    navigate(`/admin/device-model/${id}`);
  };

  const handlePageChange = (page) => {
    setSearchParams(prevSearchParams => {
      const newSearchParams = new URLSearchParams(prevSearchParams);
      newSearchParams.set('page', page);
      return newSearchParams;
    });
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Модели устройств
        </div>
      </div>
      <DeviceModelsTable>
        {
          deviceModels.map((model) =>
            <DeviceModelsTableRow
              key={model.id}
              deviceModel={model}
              onDelete={() => showDeleteModal(model.id)}
              onEditInPage={() => showEditPage(model.id)}
            />
          )
        }
      </DeviceModelsTable>
      <PaginationComponent
        totalPages={totalPages}
        currentPage={pageParam}
        onPageChange={handlePageChange}
      />
      <div className="col-12 px-0 mt-3 mb-2">
        <div className="block mb-4">
          <Link to="/admin/device-model" className="btn btn-success fw-semibold">Добавить модель</Link>
        </div>
      </div>
      <ModalConfirm
        show={isDeleteModalShow}
        onConfirm={handleDeleteConfirm}
        onClose={handleDeleteCancel}
        title="Удаление"
        message="Удалить элемент?"
      />
    </div>
  );
};

export default DeviceModels;