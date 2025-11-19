import { useState, useEffect } from 'react';

const usePagination = (totalPages, onPageChange, currentPageFromProps) => {
  const [currPage, setCurrPage] = useState(currentPageFromProps);

  useEffect(() => {
    setCurrPage(currentPageFromProps);
  }, [currentPageFromProps]);

  const changePage = (num) => {
    if (num >= 1 && num <= totalPages) {
      setCurrPage(num);
      onPageChange(num);
    }
  };

  const firstPage = () => changePage(1);
  const lastPage = () => changePage(totalPages);
  const prevPage = () => changePage(currPage - 1);
  const nextPage = () => changePage(currPage + 1);

  const getPageNumbers = () => {
    const maxVisible = 8;
    const pages = [];

    let start = Math.max(1, currPage - Math.floor(maxVisible / 2));
    let end = Math.min(totalPages, start + maxVisible - 1);

    if (end - start < maxVisible - 1) {
      start = Math.max(1, end - maxVisible + 1);
    }

    for (let i = start; i <= end; i++) {
      pages.push(i);
    }

    return pages;
  };

  return {
    currPage,
    changePage,
    firstPage,
    lastPage,
    prevPage,
    nextPage,
    getPageNumbers
  };
};

export default usePagination;
