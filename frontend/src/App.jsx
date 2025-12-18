import { useContext, useEffect } from 'react';
import { Container } from 'react-bootstrap';
import { Toaster } from 'react-hot-toast';
import { Outlet } from 'react-router-dom';
import Footer from './components/footer/Footer.jsx';
import Navigation from './components/navigation/Navigation.jsx';
import LoginModal from './components/modal/LoginModal.jsx'
import OtpVerificationModal from './components/modal/OtpVerificationModal.jsx'
import { SearchProvider } from './components/navigation/SearchContext.jsx';
import StoreContext from './components/users/StoreContext.jsx';
import { observer } from "mobx-react-lite";
import LoadingElement from './components/utils/LoadingElement.jsx';

const App = observer(({ routes }) => {
  useEffect(() => {
    if (localStorage.getItem('token')) {
      store.checkAuth()
    }
  }, [])

  const { store } = useContext(StoreContext);

  if (store.isLoading) {
    return (
      <LoadingElement />
    );
  }

  if (!store.isAuth) {
    return (
      <>
        <LoginModal
          show={!store.isOtpSent}
        />
        <OtpVerificationModal
          show={store.isOtpSent}
        />
        <Toaster position='top-center'
          reverseOrder={true}
          toastOptions={{
            duration: 5000,
          }} />
      </>
    );
  }

  return (
    <SearchProvider>
      <Navigation routes={routes}></Navigation>
      <Container className="d-flex flex-column flex-grow-1 my-1" as="main" fluid="md">
        <Outlet />
      </Container>
      <Footer />
      <Toaster position='top-center'
        reverseOrder={true}
        toastOptions={{
          duration: 4000,
        }} />
    </SearchProvider>
  );
});

export default App;
