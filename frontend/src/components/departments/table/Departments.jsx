import { Link, useNavigate } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useDepartmentsDeleteModal from '../hooks/DepartmentsDeleteModalHook.js';
import useDepartments from '../hooks/DepartmentHook.js';
import DepartmentsTable from './DepartmentsTable.jsx';
import DepartmentsTableRow from './DepartmentsTableRow.jsx';

const Departments = () => {
  const { departments, handleDepartmentsChange } = useDepartments();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useDepartmentsDeleteModal(handleDepartmentsChange);

  const navigate = useNavigate();

  const showEditPage = (id) => {
    navigate(`/admin/department/${id}`);
  };

  return (
    <div className="row gy-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Отделы
        </div>
      </div>
      <DepartmentsTable>
        {
          departments.map((department) =>
            <DepartmentsTableRow
              key={department.id}
              department={department}
              onDelete={() => showDeleteModal(department.id)}
              onEditInPage={() => showEditPage(department.id)}
            />)
        }
      </DepartmentsTable>
      <div className="col-12 px-0 mb-2">
        <div className="block mb-4">
          <Link to="/admin/department" className="btn btn-success fw-semibold">Добавить отдел</Link>
        </div>
      </div>
      <ModalConfirm
        show={isDeleteModalShow}
        onConfirm={handleDeleteConfirm}
        onClose={handleDeleteCancel}
        title='Удаление'
        message='Удалить элемент?'
      />
    </div>
  );
};

export default Departments;