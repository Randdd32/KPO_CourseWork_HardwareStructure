import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'
import App from './App.jsx'
import { StoreProvider } from './components/users/StoreContext.jsx';
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
import PagePosition from './pages/PagePosition.jsx';
import PagePositions from './pages/PagePositions.jsx';
import PageStructureElementModel from './pages/PageStructureElementModel.jsx';
import PageStructureElementModels from './pages/PageStructureElementModels.jsx';
import PageStructureElementType from './pages/PageStructureElementType.jsx';
import PageStructureElementTypes from './pages/PageStructureElementTypes.jsx';
import PageUser from './pages/PageUser.jsx';
import PageUserProfile from './pages/PageUserProfile.jsx';
import PageUsers from './pages/PageUsers.jsx';
import PageDevicesByDateReport from './pages/PageDevicesByDateReport.jsx';
import PageDevicesWithStructureReport from './pages/PageDevicesWithStructureReport.jsx';
import PageLocationsWithEmployeesReport from './pages/PageLocationsWithEmployeesReport.jsx';
import ProtectedAdminRoute from './components/security/ProtectedAdminRoute.jsx';
import PageDeviceDetails from './pages/PageDeviceDetails.jsx';
import PageSearch from './pages/PageSearch.jsx';

const routes = [
  { index: true, path: '/', element: <PageMain />, title: 'Главная страница' },

  { path: '/admin/building/:id?', element: <ProtectedAdminRoute><PageBuilding /></ProtectedAdminRoute>, title: 'Панель администратора. Здание' },
  { path: '/admin/buildings', element: <ProtectedAdminRoute><PageBuildings /></ProtectedAdminRoute>, title: 'Панель администратора. Здания' },

  { path: '/admin/department/:id?', element: <ProtectedAdminRoute><PageDepartment /></ProtectedAdminRoute>, title: 'Панель администратора. Отдел' },
  { path: '/admin/departments', element: <ProtectedAdminRoute><PageDepartments /></ProtectedAdminRoute>, title: 'Панель администратора. Отделы' },

  { path: '/admin/location/:id?', element: <ProtectedAdminRoute><PageLocation /></ProtectedAdminRoute>, title: 'Панель администратора. Помещение' },
  { path: '/admin/locations', element: <ProtectedAdminRoute><PageLocations /></ProtectedAdminRoute>, title: 'Панель администратора. Помещения' },

  { path: '/admin/device-model/:id?', element: <ProtectedAdminRoute><PageDeviceModel /></ProtectedAdminRoute>, title: 'Панель администратора. Модель устройства' },
  { path: '/admin/device-models', element: <ProtectedAdminRoute><PageDeviceModels /></ProtectedAdminRoute>, title: 'Панель администратора. Модели устройств' },

  { path: '/admin/device/:id?', element: <ProtectedAdminRoute><PageDevice /></ProtectedAdminRoute>, title: 'Панель администратора. Устройство' },
  { path: '/device-details/:id', element: <PageDeviceDetails />, title: 'Устройство. Подробная информация' },
  { path: '/admin/devices', element: <ProtectedAdminRoute><PageDevices /></ProtectedAdminRoute>, title: 'Панель администратора. Устройства' },

  { path: '/admin/device-type/:id?', element: <ProtectedAdminRoute><PageDeviceType /></ProtectedAdminRoute>, title: 'Панель администратора. Тип устройства' },
  { path: '/admin/device-types', element: <ProtectedAdminRoute><PageDeviceTypes /></ProtectedAdminRoute>, title: 'Панель администратора. Типы устройств' },

  { path: '/admin/employee/:id?', element: <ProtectedAdminRoute><PageEmployee /></ProtectedAdminRoute>, title: 'Панель администратора. Сотрудник' },
  { path: '/admin/employees', element: <ProtectedAdminRoute><PageEmployees /></ProtectedAdminRoute>, title: 'Панель администратора. Сотрудники' },

  { path: '/admin/manufacturer/:id?', element: <ProtectedAdminRoute><PageManufacturer /></ProtectedAdminRoute>, title: 'Панель администратора. Производитель' },
  { path: '/admin/manufacturers', element: <ProtectedAdminRoute><PageManufacturers /></ProtectedAdminRoute>, title: 'Панель администратора. Производители' },

  { path: '/admin/position/:id?', element: <ProtectedAdminRoute><PagePosition /></ProtectedAdminRoute>, title: 'Панель администратора. Должность' },
  { path: '/admin/positions', element: <ProtectedAdminRoute><PagePositions /></ProtectedAdminRoute>, title: 'Панель администратора. Должности' },

  { path: '/admin/structure-element-model/:id?', element: <ProtectedAdminRoute><PageStructureElementModel /></ProtectedAdminRoute>, title: 'Панель администратора. Модель элемента структуры' },
  { path: '/admin/structure-element-models', element: <ProtectedAdminRoute><PageStructureElementModels /></ProtectedAdminRoute>, title: 'Панель администратора. Модели элементов структуры' },

  { path: '/admin/structure-element-type/:id?', element: <ProtectedAdminRoute><PageStructureElementType /></ProtectedAdminRoute>, title: 'Панель администратора. Тип элемента структуры' },
  { path: '/admin/structure-element-types', element: <ProtectedAdminRoute><PageStructureElementTypes /></ProtectedAdminRoute>, title: 'Панель администратора. Типы элементов структуры' },

  { path: '/admin/user/:id?', element: <ProtectedAdminRoute><PageUser /></ProtectedAdminRoute>, title: 'Панель администратора. Пользователь' },
  { path: '/user/:id', element: <PageUserProfile />, title: 'Профиль' },
  { path: '/admin/users', element: <ProtectedAdminRoute><PageUsers /></ProtectedAdminRoute>, title: 'Панель администратора. Пользователи' },

  { path: '/admin/reports/devices-by-date', element: <ProtectedAdminRoute><PageDevicesByDateReport /></ProtectedAdminRoute>, title: 'Панель администратора. Устройства по дате - отчет' },
  { path: '/admin/reports/devices-with-structure', element: <ProtectedAdminRoute><PageDevicesWithStructureReport /></ProtectedAdminRoute>, title: 'Панель администратора. Устройства со структурой - отчет' },
  { path: '/admin/reports/locations-with-employees', element: <ProtectedAdminRoute><PageLocationsWithEmployeesReport /></ProtectedAdminRoute>, title: 'Панель администратора. Помещения с сотрудниками - отчет' },

  { path: '/search', element: <PageSearch />, title: 'Поиск устройств' }
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
  <StoreProvider>
    <RouterProvider router={router} />
  </StoreProvider>
);
