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
import PageDeviceModel from './pages/PageDeviceModel.jsx';
import PageDeviceModels from './pages/PageDeviceModels.jsx';
import PageDevice from './pages/PageDevice.jsx';
import PageDevices from './pages/PageDevices.jsx';
import PageDeviceType from './pages/PageDeviceType.jsx';
import PageDeviceTypes from './pages/PageDeviceTypes.jsx';
import PageEmployee from './pages/PageEmployee.jsx';
import PageEmployees from './pages/PageEmployees.jsx';
import PageManufacturer from './pages/PageManufacturer.jsx';
import PageManufacturers from './pages/PageManufacturers.jsx';

const routes = [
  { index: true, path: '/', element: <PageMain />, title: 'Главная страница' },

  { path: '/admin/building/:id?', element: <PageBuilding />, title: 'Панель администратора. Здание' },
  { path: '/admin/buildings', element: <PageBuildings />, title: 'Панель администратора. Здания' },

  { path: '/admin/department/:id?', element: <PageDepartment />, title: 'Панель администратора. Отдел' },
  { path: '/admin/departments', element: <PageDepartments />, title: 'Панель администратора. Отделы' },

  { path: '/admin/location/:id?', element: <PageLocation />, title: 'Панель администратора. Помещение' },
  { path: '/admin/locations', element: <PageLocations />, title: 'Панель администратора. Помещения' },

  { path: '/admin/device-model/:id?', element: <PageDeviceModel />, title: 'Панель администратора. Модель устройства' },
  { path: '/admin/device-models', element: <PageDeviceModels />, title: 'Панель администратора. Модели устройств' },

  { path: '/admin/device/:id?', element: <PageDevice />, title: 'Панель администратора. Устройство' },
  { path: '/admin/devices', element: <PageDevices />, title: 'Панель администратора. Устройства' },

  { path: '/admin/device-type/:id?', element: <PageDeviceType />, title: 'Панель администратора. Тип устройства' },
  { path: '/admin/device-types', element: <PageDeviceTypes />, title: 'Панель администратора. Типы устройств' },

  { path: '/admin/employee/:id?', element: <PageEmployee />, title: 'Панель администратора. Сотрудник' },
  { path: '/admin/employees', element: <PageEmployees />, title: 'Панель администратора. Сотрудники' },

  { path: '/admin/manufacturer/:id?', element: <PageManufacturer />, title: 'Панель администратора. Производитель' },
  { path: '/admin/manufacturers', element: <PageManufacturers />, title: 'Панель администратора. Производители' },
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
