import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'
import App from './App.jsx'
import ErrorPage from './pages/ErrorPage.jsx';
import PageMain from './pages/PageMain.jsx';
import PageBuilding from './pages/PageBuilding.jsx';
import PageBuildings from './pages/PageBuildings.jsx';
import PageDepartment from './pages/PageDepartment.jsx';
import PageDepartments from './pages/PageDepartments.jsx';
import PageLocation from './pages/PageLocation.jsx';
import PageLocations from './pages/PageLocations.jsx';

const routes = [
  { index: true, path: '/', element: <PageMain />, title: 'Главная страница' },

  { path: '/admin/building/:id?', element: <PageBuilding />, title: 'Панель администратора. Здание' },
  { path: '/admin/buildings', element: <PageBuildings />, title: 'Панель администратора. Здания' },

  { path: '/admin/department/:id?', element: <PageDepartment />, title: 'Панель администратора. Отдел' },
  { path: '/admin/departments', element: <PageDepartments />, title: 'Панель администратора. Отделы' },

  { path: '/admin/location/:id?', element: <PageLocation />, title: 'Панель администратора. Помещение' },
  { path: '/admin/locations', element: <PageLocations />, title: 'Панель администратора. Помещения' },
];

const router = createBrowserRouter([
  {
    path: '/',
    element: <App routes={routes} />,
    children: routes,
    errorElement: <ErrorPage />,
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <RouterProvider router={router} />
);
