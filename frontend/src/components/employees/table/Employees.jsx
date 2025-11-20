import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useEmployeesDeleteModal from '../hooks/EmployeesDeleteModalHook.js';
import useEmployees from '../hooks/EmployeeHook.js';
import EmployeesTable from './EmployeesTable.jsx';
import EmployeesTableRow from './EmployeesTableRow.jsx';
import PaginationComponent from '../../pagination/Pagination.jsx';
import { useEffect } from 'react';
import { PAGE_SIZE } from '../../utils/Constants.js';

const Employees = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const pageParam = parseInt(searchParams.get('page')) || 1;

  const {
    employees,
    totalPages,
    getEmployees,
    employeesRefresh,
    handleEmployeesChange
  } = useEmployees();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useEmployeesDeleteModal(handleEmployeesChange);

  useEffect(() => {
    getEmployees(pageParam, PAGE_SIZE);
  }, [pageParam, employeesRefresh]);

  const showEditPage = (id) => {
    navigate(`/admin/employee/${id}`);
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
          Сотрудники
        </div>
      </div>
      <EmployeesTable>
        {employees.map((employee) => (
          <EmployeesTableRow
            key={employee.id}
            employee={employee}
            onDelete={() => showDeleteModal(employee.id)}
            onEditInPage={() => showEditPage(employee.id)}
          />
        ))}
      </EmployeesTable>
      <PaginationComponent
        totalPages={totalPages}
        currentPage={pageParam}
        onPageChange={handlePageChange}
      />
      <div className="col-12 px-0 mt-3 mb-2">
        <div className="block mb-4">
          <Link to="/admin/employee" className="btn btn-success fw-semibold">
            Добавить сотрудника
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

export default Employees;