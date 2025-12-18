import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import DeviceModelsApiService from '../service/DeviceModelsApiService';

const useDeviceModelDeleteModal = (deviceModelsChangeHandle) => {
  const { isModalShow, showModal, hideModal } = useModal();
  const [currentId, setCurrentId] = useState(0);

  const showModalDialog = (id) => {
    showModal();
    setCurrentId(id);
  };

  const onClose = () => hideModal();

  const onDelete = async () => {
    await DeviceModelsApiService.delete(currentId);
    deviceModelsChangeHandle?.();
    toast.success('Элемент успешно удален', { id: 'DeviceModelsTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose
  };
};

export default useDeviceModelDeleteModal;