import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useDevicesDeleteModal from '../hooks/DevicesDeleteModalHook.js';
import useDevices from '../hooks/DeviceHook.js';
import DevicesTable from './DevicesTable.jsx';
import DevicesTableRow from './DevicesTableRow.jsx';
import PaginationComponent from '../../pagination/Pagination.jsx';
import { useEffect } from 'react';
import { PAGE_SIZE } from '../../utils/Constants.js';

const Devices = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const pageParam = parseInt(searchParams.get('page')) || 1;

  const {
    devices,
    totalPages,
    getDevices,
    devicesRefresh,
    handleDevicesChange
  } = useDevices();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useDevicesDeleteModal(handleDevicesChange);

  useEffect(() => {
    getDevices(pageParam, PAGE_SIZE);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pageParam, devicesRefresh]);

  const showEditPage = (id) => {
    navigate(`/admin/device/${id}`);
  };

  const handlePageChange = (page) => {
    setSearchParams(prev => {
      const newParams = new URLSearchParams(prev);
      newParams.set('page', page);
      return newParams;
    });
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Устройства
        </div>
      </div>
      <DevicesTable>
        {devices.map((device) => (
          <DevicesTableRow
            key={device.id}
            device={device}
            onDelete={() => showDeleteModal(device.id)}
            onEditInPage={() => showEditPage(device.id)}
          />
        ))}
      </DevicesTable>
      <PaginationComponent
        totalPages={totalPages}
        currentPage={pageParam}
        onPageChange={handlePageChange}
      />
      <div className="col-12 px-0 mt-3 mb-2">
        <div className="block mb-4">
          <Link to="/admin/device" className="btn btn-success fw-semibold">Добавить устройство</Link>
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

export default Devices;