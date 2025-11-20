import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import usePositionDeleteModal from '../hooks/PositionsDeleteModalHook.js';
import usePositions from '../hooks/PositionHook.js';
import PositionsTable from './PositionsTable.jsx';
import PositionsTableRow from './PositionsTableRow.jsx';
import PaginationComponent from '../../pagination/Pagination.jsx';
import { useEffect } from 'react';
import { PAGE_SIZE } from '../../utils/Constants.js';

const Positions = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const pageParam = parseInt(searchParams.get('page')) || 1;

  const {
    positions,
    totalPages,
    getPositions,
    positionsRefresh,
    handlePositionsChange
  } = usePositions();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = usePositionDeleteModal(handlePositionsChange);

  useEffect(() => {
    getPositions(pageParam, PAGE_SIZE);
  }, [pageParam, positionsRefresh]);

  const showEditPage = (id) => {
    navigate(`/admin/position/${id}`);
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
          Должности
        </div>
      </div>
      <PositionsTable>
        {
          positions.map((position) =>
            <PositionsTableRow
              key={position.id}
              position={position}
              onDelete={() => showDeleteModal(position.id)}
              onEditInPage={() => showEditPage(position.id)}
            />
          )
        }
      </PositionsTable>
      <PaginationComponent
        totalPages={totalPages}
        currentPage={pageParam}
        onPageChange={handlePageChange}
      />
      <div className="col-12 px-0 mt-3 mb-2">
        <div className="block mb-4">
          <Link to="/admin/position" className="btn btn-success fw-semibold">Добавить должность</Link>
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

export default Positions;