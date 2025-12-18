import { Link, useNavigate } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useStructureElementTypesDeleteModal from '../hooks/StructureElementTypesDeleteModalHook.js';
import useStructureElementTypes from '../hooks/StructureElementTypeHook.js';
import StructureElementTypesTable from './StructureElementTypesTable.jsx';
import StructureElementTypesTableRow from './StructureElementTypesTableRow.jsx';

const StructureElementTypes = () => {
  const { structureElementTypes, handleStructureElementTypesChange } = useStructureElementTypes();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useStructureElementTypesDeleteModal(handleStructureElementTypesChange);

  const navigate = useNavigate();

  const showEditPage = (id) => {
    navigate(`/admin/structure-element-type/${id}`);
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Типы элементов структуры
        </div>
      </div>
      <StructureElementTypesTable>
        {
          structureElementTypes.map((structureElementType) =>
            <StructureElementTypesTableRow
              key={structureElementType.id}
              structureElementType={structureElementType}
              onDelete={() => showDeleteModal(structureElementType.id)}
              onEditInPage={() => showEditPage(structureElementType.id)}
            />)
        }
      </StructureElementTypesTable>
      <div className="col-12 px-0 mb-2">
        <div className="block mb-4">
          <Link to="/admin/structure-element-type" className="btn btn-success fw-semibold">Добавить тип элемента структуры</Link>
        </div>
      </div>
      <ModalConfirm
        show={isDeleteModalShow}
        onConfirm={handleDeleteConfirm}
        onClose={handleDeleteCancel}
        title='Удаление'
        message='Удалить элемент?' />
    </div>
  );
};

export default StructureElementTypes;