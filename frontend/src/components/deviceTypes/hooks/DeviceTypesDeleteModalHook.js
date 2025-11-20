import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import DeviceTypesApiService from '../service/DeviceTypesApiService';

const useDeviceTypesDeleteModal = (deviceTypesChangeHandle) => {
  const { isModalShow, showModal, hideModal } = useModal();
  const [currentId, setCurrentId] = useState(0);

  const showModalDialog = (id) => {
    showModal();
    setCurrentId(id);
  };

  const onClose = () => {
    hideModal();
  };

  const onDelete = async () => {
    await DeviceTypesApiService.delete(currentId);
    deviceTypesChangeHandle?.();
    toast.success('Элемент успешно удален', { id: 'DeviceTypesTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose,
  };
};

export default useDeviceTypesDeleteModal;