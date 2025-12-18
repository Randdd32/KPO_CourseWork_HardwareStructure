import PropTypes from 'prop-types';
import { createContext, useMemo } from 'react';
import Store from '../../store/Store.js';

const StoreContext = createContext({
  store: null,
});

export const StoreProvider = ({ children }) => {
  const store = useMemo(() => new Store(), []);

  const storeContextValue = useMemo(() => ({ store }), [store]);

  return (
    <StoreContext.Provider value={storeContextValue}>
      {children}
    </StoreContext.Provider>
  );
};

StoreProvider.propTypes = {
  children: PropTypes.node
};

export default StoreContext;