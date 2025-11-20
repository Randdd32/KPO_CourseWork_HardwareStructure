import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useStructureElementModelDeleteModal from '../hooks/StructureElementModelsDeleteModalHook.js';
import useStructureElementModels from '../hooks/StructureElementModelHook.js';
import StructureElementModelsTable from './StructureElementModelsTable.jsx';
import StructureElementModelsTableRow from './StructureElementModelsTableRow.jsx';
import PaginationComponent from '../../pagination/Pagination.jsx';
import { useEffect } from 'react';
import { PAGE_SIZE } from '../../utils/Constants.js';

const StructureElementModels = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const pageParam = parseInt(searchParams.get('page')) || 1;

  const {
    structureElementModels,
    totalPages,
    getStructureElementModels,
    structureElementModelsRefresh,
    handleStructureElementModelsChange
  } = useStructureElementModels();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useStructureElementModelDeleteModal(handleStructureElementModelsChange);

  useEffect(() => {
    getStructureElementModels(pageParam, PAGE_SIZE);
  }, [pageParam, structureElementModelsRefresh]);

  const showEditPage = (id) => {
    navigate(`/admin/structure-element-model/${id}`);
  };

  const handlePageChange = (page) => {
    setSearchParams(prevSearchParams => {
      const newSearchParams = new URLSearchParams(prevSearchParams);
      newSearchParams.set('page', page);
      return newSearchParams;
    });
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Модели элементов структуры
        </div>
      </div>
      <StructureElementModelsTable>
        {
          structureElementModels.map((model) =>
            <StructureElementModelsTableRow
              key={model.id}
              structureElementModel={model}
              onDelete={() => showDeleteModal(model.id)}
              onEditInPage={() => showEditPage(model.id)}
            />
          )
        }
      </StructureElementModelsTable>
      <PaginationComponent
        totalPages={totalPages}
        currentPage={pageParam}
        onPageChange={handlePageChange}
      />
      <div className="col-12 px-0 mt-3 mb-2">
        <div className="block mb-4">
          <Link to="/admin/structure-element-model" className="btn btn-success fw-semibold">Добавить модель элемента структуры</Link>
        </div>
      </div>
      <ModalConfirm
        show={isDeleteModalShow}
        onConfirm={handleDeleteConfirm}
        onClose={handleDeleteCancel}
        title="Удаление"
        message="Удалить элемент?"
      />
    </div>
  );
};

export default StructureElementModels;