import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import LocationsApiService from '../service/LocationsApiService';

const useLocationDeleteModal = (locationsChangeHandle) => {
  const { isModalShow, showModal, hideModal } = useModal();
  const [currentId, setCurrentId] = useState(0);

  const showModalDialog = (id) => {
    showModal();
    setCurrentId(id);
  };

  const onClose = () => hideModal();

  const onDelete = async () => {
    await LocationsApiService.delete(currentId);
    locationsChangeHandle?.();
    toast.success('Элемент успешно удален', { id: 'LocationsTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose,
  };
};

export default useLocationDeleteModal;