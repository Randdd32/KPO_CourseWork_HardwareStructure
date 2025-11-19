import { Pagination } from 'react-bootstrap';
import PropTypes from 'prop-types';
import './pagination.css';
import usePagination from './hooks/PaginationHook';

const PaginationComponent = ({ totalPages, onPageChange, currentPage }) => {
  const {
    currPage,
    changePage,
    firstPage,
    lastPage,
    prevPage,
    nextPage,
    getPageNumbers,
  } = usePagination(totalPages, onPageChange, currentPage);

  return (
    <div className="overflow-auto z-1">
      <nav className="ps-0 mt-0" aria-label="Page navigation">
        <Pagination className="pagination-floating justify-content-left my-2 flex-nowrap">
          <Pagination.First onClick={firstPage} disabled={currPage === 1}>
            <span aria-hidden="true">&laquo;&laquo;</span>
          </Pagination.First>
          <Pagination.Prev className='d-none d-sm-block' onClick={prevPage} disabled={currPage === 1}>
            <span aria-hidden="true">&laquo;</span>
          </Pagination.Prev>

          {getPageNumbers().map((page) => (
            <Pagination.Item
              key={page}
              active={page === currPage}
              onClick={() => changePage(page)}
            >
              {page}
            </Pagination.Item>
          ))}

          <Pagination.Next className='d-none d-sm-block' onClick={nextPage} disabled={currPage === totalPages}>
            <span aria-hidden="true">&raquo;</span>
          </Pagination.Next>
          <Pagination.Last onClick={lastPage} disabled={currPage === totalPages}>
            <span aria-hidden="true">&raquo;&raquo;</span>
          </Pagination.Last>
        </Pagination>
      </nav>
    </div>
  );
};

PaginationComponent.propTypes = {
  totalPages: PropTypes.number.isRequired,
  onPageChange: PropTypes.func.isRequired,
  currentPage: PropTypes.number.isRequired
};

export default PaginationComponent;