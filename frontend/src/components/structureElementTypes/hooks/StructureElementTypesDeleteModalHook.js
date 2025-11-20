import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import StructureElementTypesApiService from '../service/StructureElementTypesApiService';

const useStructureElementTypesDeleteModal = (structureElementTypesChangeHandle) => {
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
    await StructureElementTypesApiService.delete(currentId);
    structureElementTypesChangeHandle?.();
    toast.success('Элемент успешно удален', { id: 'StructureElementTypesTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose,
  };
};

export default useStructureElementTypesDeleteModal;