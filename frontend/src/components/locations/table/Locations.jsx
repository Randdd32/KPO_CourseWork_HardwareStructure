import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useLocationDeleteModal from '../hooks/LocationsDeleteModalHook.js';
import useLocations from '../hooks/LocationHook.js';
import LocationsTable from './LocationsTable.jsx';
import LocationsTableRow from './LocationsTableRow.jsx';
import PaginationComponent from '../../pagination/Pagination.jsx';
import { useEffect } from 'react';
import { PAGE_SIZE } from '../../utils/Constants.js';

const Locations = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const pageParam = parseInt(searchParams.get('page')) || 1;

  const {
    locations,
    totalPages,
    getLocations,
    locationsRefresh,
    handleLocationsChange,
  } = useLocations();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useLocationDeleteModal(handleLocationsChange);

  useEffect(() => {
    getLocations(pageParam, PAGE_SIZE);
  }, [pageParam, locationsRefresh]);

  const showEditPage = (id) => {
    navigate(`/admin/location/${id}`);
  };

  const handlePageChange = (page) => {
    setSearchParams((prev) => {
      const newParams = new URLSearchParams(prev);
      newParams.set('page', page);
      return newParams;
    });
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Помещения
        </div>
      </div>
      <LocationsTable>
        {locations.map((location) => (
          <LocationsTableRow
            key={location.id}
            location={location}
            onDelete={() => showDeleteModal(location.id)}
            onEditInPage={() => showEditPage(location.id)}
          />
        ))}
      </LocationsTable>
      <PaginationComponent
        totalPages={totalPages}
        currentPage={pageParam}
        onPageChange={handlePageChange}
      />
      <div className="col-12 px-0 mt-3 mb-2">
        <div className="block mb-4">
          <Link to="/admin/location" className="btn btn-success fw-semibold">
            Добавить помещение
          </Link>
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

export default Locations;