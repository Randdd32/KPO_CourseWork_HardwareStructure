import PropTypes from 'prop-types';
import { createContext, useState, useMemo } from 'react';

const SearchContext = createContext();

export const SearchProvider = ({ children }) => {
  const [searchValue, setSearchValue] = useState('');

  const updateSearchValue = (newSearchValue) => {
    setSearchValue(newSearchValue);
  };

  const contextValue = useMemo(() => ({
    searchValue,
    updateSearchValue
  }), [searchValue]);

  return (
    <SearchContext.Provider value={contextValue}>
      {children}
    </SearchContext.Provider>
  );
};

SearchProvider.propTypes = {
  children: PropTypes.node,
};

export default SearchContext;