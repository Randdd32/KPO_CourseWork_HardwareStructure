import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import DevicesApiService from '../service/DevicesApiService';

const useDeviceDeleteModal = (devicesChangeHandle) => {
  const { isModalShow, showModal, hideModal } = useModal();
  const [currentId, setCurrentId] = useState(0);

  const showModalDialog = (id) => {
    showModal();
    setCurrentId(id);
  };

  const onClose = () => hideModal();

  const onDelete = async () => {
    await DevicesApiService.delete(currentId);
    devicesChangeHandle?.();
    toast.success('Устройство успешно удалено', { id: 'DevicesTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose
  };
};

export default useDeviceDeleteModal;