import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import DepartmentsApiService from '../service/DepartmentsApiService';

const useDepartmentsDeleteModal = (departmentsChangeHandle) => {
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
    await DepartmentsApiService.delete(currentId);
    departmentsChangeHandle?.();
    toast.success('Элемент успешно удален', { id: 'DepartmentsTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose,
  };
};

export default useDepartmentsDeleteModal;