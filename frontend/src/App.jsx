import PropTypes from 'prop-types';
import { Container } from 'react-bootstrap';
import { Toaster } from 'react-hot-toast';
import { Outlet } from 'react-router-dom';
import { SearchProvider } from './components/navigation/SearchContext.jsx';
import Footer from './components/footer/Footer.jsx';
import Navigation from './components/navigation/Navigation.jsx';

const App = ({ routes }) => {
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
};

App.propTypes = {
  routes: PropTypes.array,
};

export default App;
