import { useState } from 'react';
import toast from 'react-hot-toast';
import useModal from '../../modal/hooks/ModalHook';
import BuildingsApiService from '../service/BuildingsApiService';

const useBuildingsDeleteModal = (buildingsChangeHandle) => {
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
        await BuildingsApiService.delete(currentId);
        buildingsChangeHandle?.();
        toast.success('Элемент успешно удален', { id: 'BuildingsTable' });
        onClose();
    };

    return {
        isDeleteModalShow: isModalShow,
        showDeleteModal: showModalDialog,
        handleDeleteConfirm: onDelete,
        handleDeleteCancel: onClose,
    };
};

export default useBuildingsDeleteModal;