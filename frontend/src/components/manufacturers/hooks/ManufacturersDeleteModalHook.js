import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import ManufacturersApiService from '../service/ManufacturersApiService';

const useManufacturersDeleteModal = (manufacturersChangeHandle) => {
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
    await ManufacturersApiService.delete(currentId);
    manufacturersChangeHandle?.();
    toast.success('Элемент успешно удален', { id: 'ManufacturersTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose
  };
};

export default useManufacturersDeleteModal;