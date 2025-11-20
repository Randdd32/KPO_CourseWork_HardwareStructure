import { Link, useNavigate } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useDeviceTypesDeleteModal from '../hooks/DeviceTypesDeleteModalHook.js';
import useDeviceTypes from '../hooks/DeviceTypeHook.js';
import DeviceTypesTable from './DeviceTypesTable.jsx';
import DeviceTypesTableRow from './DeviceTypesTableRow.jsx';

const DeviceTypes = () => {
  const { deviceTypes, handleDeviceTypesChange } = useDeviceTypes();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useDeviceTypesDeleteModal(handleDeviceTypesChange);

  const navigate = useNavigate();

  const showEditPage = (id) => {
    navigate(`/admin/device-type/${id}`);
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Типы устройств
        </div>
      </div>
      <DeviceTypesTable>
        {
          deviceTypes.map((deviceType, index) =>
            <DeviceTypesTableRow
              key={deviceType.id}
              index={index}
              deviceType={deviceType}
              onDelete={() => showDeleteModal(deviceType.id)}
              onEditInPage={() => showEditPage(deviceType.id)}
            />)
        }
      </DeviceTypesTable>
      <div className="col-12 px-0 mb-2">
        <div className="block mb-4">
          <Link to="/admin/device-type" className="btn btn-dark fw-semibold">Добавить тип устройства</Link>
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

export default DeviceTypes;