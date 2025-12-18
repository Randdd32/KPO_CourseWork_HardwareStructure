import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import StructureElementModelsApiService from '../service/StructureElementModelsApiService';

const useStructureElementModelDeleteModal = (structureElementModelsChangeHandle) => {
  const { isModalShow, showModal, hideModal } = useModal();
  const [currentId, setCurrentId] = useState(0);

  const showModalDialog = (id) => {
    showModal();
    setCurrentId(id);
  };

  const onClose = () => hideModal();

  const onDelete = async () => {
    await StructureElementModelsApiService.delete(currentId);
    structureElementModelsChangeHandle?.();
    toast.success('Элемент успешно удален', { id: 'StructureElementModelsTable' });
    onClose();
  };

  return {
    isDeleteModalShow: isModalShow,
    showDeleteModal: showModalDialog,
    handleDeleteConfirm: onDelete,
    handleDeleteCancel: onClose
  };
};

export default useStructureElementModelDeleteModal;