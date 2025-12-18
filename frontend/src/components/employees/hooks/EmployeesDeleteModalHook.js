import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import EmployeesApiService from '../service/EmployeesApiService';

const useEmployeeDeleteModal = (employeesChangeHandle) => {
  const { isModalShow, showModal, hideModal } = useModal();
  const [currentId, setCurrentId] = useState(0);

  const showModalDialog = (id) => {
    showModal();
    setCurrentId(id);
  };

  const onClose = () => hideModal();

  const onDelete = async () => {
    await EmployeesApiService.delete(currentId);
    employeesChangeHandle?.();
    toast.success('Элемент успешно удалён', { id: 'EmployeesTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose
  };
};

export default useEmployeeDeleteModal;