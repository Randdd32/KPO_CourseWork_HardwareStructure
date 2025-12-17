import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import ModalConfirm from '../../modal/ModalConfirm.jsx';
import useUserDeleteModal from '../hooks/UsersDeleteModalHook.js';
import useUsers from '../hooks/UserHook.js';
import UsersTable from './UsersTable.jsx';
import UsersTableRow from './UsersTableRow.jsx';
import PaginationComponent from '../../pagination/Pagination.jsx';
import { useContext, useEffect } from 'react';
import { PAGE_SIZE } from '../../utils/Constants.js';
import { observer } from "mobx-react-lite";
import StoreContext from '../../users/StoreContext.jsx';

const Users = observer(() => {
  const { store } = useContext(StoreContext);
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const pageParam = parseInt(searchParams.get('page')) || 1;

  const {
    users,
    totalPages,
    getUsers,
    usersRefresh,
    handleUsersChange
  } = useUsers();

  const {
    isDeleteModalShow,
    showDeleteModal,
    handleDeleteConfirm,
    handleDeleteCancel,
  } = useUserDeleteModal(handleUsersChange);

  useEffect(() => {
    getUsers(pageParam, PAGE_SIZE);
  }, [pageParam, usersRefresh]);

  const showEditPage = (id) => {
    navigate(`/admin/user/${id}`);
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
          Пользователи
        </div>
      </div>
      <UsersTable>
        {users
          .filter((user) => {
            if (store.isRegularAdmin) {
              return user.role === 'Пользователь';
            }
            return true;
          })
          .map((user) => (
            <UsersTableRow
              key={user.id}
              user={user}
              onDelete={() => showDeleteModal(user.id)}
              onEditInPage={() => showEditPage(user.id)}
            />
          ))}
      </UsersTable>
      <PaginationComponent
        totalPages={totalPages}
        currentPage={pageParam}
        onPageChange={handlePageChange}
      />
      <div className="col-12 px-0 mt-3 mb-2">
        <div className="block mb-4">
          <Link to="/admin/user" className="btn btn-success fw-semibold">
            Добавить пользователя
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
});

export default Users;