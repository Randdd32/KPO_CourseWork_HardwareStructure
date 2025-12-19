import { useState, useEffect, useCallback, useContext } from 'react';
import { Offcanvas, Button, Spinner } from 'react-bootstrap';
import { useSearchParams } from 'react-router-dom';
import DeviceCard from './DeviceCard';
import SortSelect from '../input/SortSelect';
import FilterFormContent from './FilterFormContent';
import PaginationComponent from '../pagination/Pagination';
import toast from 'react-hot-toast';
import { SEARCH_PAGE_SIZE } from '../utils/Constants';
import DevicesGetService from './service/DevicesGetServiceClass';
import NotFound from "../../assets/icons/searchNotFound.png";
import SearchContext from '../navigation/SearchContext';
import './deviceList.css';

const DeviceList = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const { updateSearchValue } = useContext(SearchContext);

  const [devices, setDevices] = useState([]);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(true);
  const [showFilterOffcanvas, setShowFilterOffcanvas] = useState(false);

  const currentPage = Number(searchParams.get('page') || 1);
  const sortType = searchParams.get('sortType') || 'PURCHASE_DATE_DESC';

  const manufacturerIds = searchParams.get('manufacturerIds')?.split(',').map(Number).filter(id => !isNaN(id)) || [];
  const buildingIds = searchParams.get('buildingIds')?.split(',').map(Number).filter(id => !isNaN(id)) || [];
  const locationIds = searchParams.get('locationIds')?.split(',').map(Number).filter(id => !isNaN(id)) || [];
  const employeeIds = searchParams.get('employeeIds')?.split(',').map(Number).filter(id => !isNaN(id)) || [];
  const typeIds = searchParams.get('typeIds')?.split(',').map(Number).filter(id => !isNaN(id)) || [];
  const departmentIds = searchParams.get('departmentIds')?.split(',').map(Number).filter(id => !isNaN(id)) || [];

  const isWorking = searchParams.get('isWorking') === 'true' ? true : (searchParams.get('isWorking') === 'false' ? false : null);

  const priceFrom = searchParams.get('priceFrom') || '';
  const priceTo = searchParams.get('priceTo') || '';
  const purchaseDateFrom = searchParams.get('purchaseDateFrom') || '';
  const purchaseDateTo = searchParams.get('purchaseDateTo') || '';
  const warrantyDateFrom = searchParams.get('warrantyDateFrom') || '';
  const warrantyDateTo = searchParams.get('warrantyDateTo') || '';
  const activeSearchInfo = searchParams.get('searchInfo') || '';

  const [localPriceFrom, setLocalPriceFrom] = useState(priceFrom);
  const [localPriceTo, setLocalPriceTo] = useState(priceTo);

  const currentSize = SEARCH_PAGE_SIZE;

  const updateParams = useCallback((
    updates,
    replace = true) => {
    setSearchParams(
      (currentSearchParams) => {
        const newSearchParams = new URLSearchParams(currentSearchParams.toString());
        Object.entries(updates).forEach(([key, value]) => {
          if (value === null || value === '' || (Array.isArray(value) && value.length === 0)) {
            newSearchParams.delete(key);
          } else {
            newSearchParams.set(key, Array.isArray(value) ? value.join(',') : String(value));
          }
        });
        if (!updates.page) {
          newSearchParams.set('page', '1');
        }
        return newSearchParams;
      },
      { replace }
    );
  }, [setSearchParams]);

  const parseDateTimeForInput = useCallback((dateString) => {
    if (!dateString) return '';
    try {
      const d = new Date(dateString);
      if (isNaN(d.getTime())) {
        toast.error('Произошла проблема при работе с датами');
        return '';
      }
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, '0');
      const day = String(d.getDate()).padStart(2, '0');
      const hours = String(d.getHours()).padStart(2, '0');
      const minutes = String(d.getMinutes()).padStart(2, '0');
      const seconds = String(d.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    } catch (error) {
      toast.error(`Ошибка при работе с датами ${error.message}`);
      return '';
    }
  }, []);

  const formatDateTimeForBackend = useCallback((dateString) => {
    if (!dateString) return null;
    try {
      const d = new Date(dateString);
      if (isNaN(d.getTime())) {
        toast.error('Произошла проблема при работе с датами');
        return null;
      }
      return d.toISOString();
    } catch (error) {
      toast.error(`Ошибка при работе с датам: ${error.message}`);
      return null;
    }
  }, []);

  useEffect(() => {
    const fetchDevicesData = async () => {
      setLoading(true);
      try {
        const params = {
          page: currentPage - 1,
          size: currentSize,
          sortType,
          ...(manufacturerIds.length > 0 && { manufacturerIds }),
          ...(buildingIds.length > 0 && { buildingIds }),
          ...(locationIds.length > 0 && { locationIds }),
          ...(employeeIds.length > 0 && { employeeIds }),
          ...(typeIds.length > 0 && { typeIds }),
          ...(departmentIds.length > 0 && { departmentIds }),
          ...(isWorking !== null && { isWorking }),
          ...(priceFrom && { priceFrom: parseFloat(priceFrom) }),
          ...(priceTo && { priceTo: parseFloat(priceTo) }),
          ...(purchaseDateFrom && { purchaseDateFrom: purchaseDateFrom }),
          ...(purchaseDateTo && { purchaseDateTo: purchaseDateTo }),
          ...(warrantyDateFrom && { warrantyDateFrom: warrantyDateFrom }),
          ...(warrantyDateTo && { warrantyDateTo: warrantyDateTo }),
          ...(activeSearchInfo && { searchInfo: activeSearchInfo })
        };

        const data = await DevicesGetService.getAllByFilters(params);
        setDevices(data.items || []);
        setTotalPages(data.totalPages || 1);
      } catch (error) {
        toast.error("Ошибка при загрузке устройств: " + error.message);
        setDevices([]);
        setTotalPages(1);
      } finally {
        setLoading(false);
      }
    };

    fetchDevicesData();
    updateSearchValue(activeSearchInfo);
  }, [
    searchParams.toString()
  ]);

  const handleResetFilters = useCallback(() => {
    const currentSearchInfo = searchParams.get('searchInfo');

    const updates = {
      manufacturerIds: null,
      buildingIds: null,
      locationIds: null,
      employeeIds: null,
      typeIds: null,
      departmentIds: null,
      isWorking: null,
      priceFrom: null,
      priceTo: null,
      purchaseDateFrom: null,
      purchaseDateTo: null,
      warrantyDateFrom: null,
      warrantyDateTo: null,
      sortType: null,
      page: 1
    };

    if (currentSearchInfo) {
      updates.searchInfo = currentSearchInfo;
    } else {
      updates.searchInfo = null;
    }

    updateParams(updates);
    setShowFilterOffcanvas(false);
  }, [searchParams, updateParams]);

  const handlePageChange = useCallback((page) => {
    updateParams({ page });
  }, [updateParams]);

  const handleSortChange = useCallback((event) => {
    const newSortType = event.target.value;
    updateParams({ sortType: newSortType, page: 1 });
  }, [updateParams]);

  return (
    <>
      <div className="d-flex flex-column mb-2">
        <h4 className="mt-3 text-center w-100 fw-bold fs-3">Результаты поиска</h4>
      </div>

      <div className="row g-4">
        <div className="col-lg-3 d-none d-lg-block ps-0 mt-3">
          <div className="filter-sidebar px-4 py-3 shadow-sm border">
            <h5 className="mb-4 text-center fw-bold">Фильтры</h5>
            <FilterFormContent
              manufacturerIds={manufacturerIds}
              buildingIds={buildingIds}
              locationIds={locationIds}
              employeeIds={employeeIds}
              typeIds={typeIds}
              departmentIds={departmentIds}
              isWorking={isWorking}
              localPriceFrom={localPriceFrom}
              setLocalPriceFrom={setLocalPriceFrom}
              localPriceTo={localPriceTo}
              setLocalPriceTo={setLocalPriceTo}
              purchaseDateFrom={purchaseDateFrom}
              purchaseDateTo={purchaseDateTo}
              warrantyDateFrom={warrantyDateFrom}
              warrantyDateTo={warrantyDateTo}
              updateParams={updateParams}
              handleResetFilters={handleResetFilters}
              parseDateTimeForInput={parseDateTimeForInput}
              formatDateTimeForBackend={formatDateTimeForBackend}
            />
          </div>
        </div>

        <div className="col-md-12 col-lg-9 pe-0 mt-3">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <SortSelect sortType={sortType} handleSortChange={handleSortChange} />
            <Button
              variant="light"
              className="d-lg-none filter-button-mobile"
              onClick={() => setShowFilterOffcanvas(true)}
            >
              <i className="bi bi-funnel-fill"></i>
            </Button>
          </div>

          {loading ? (
            <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '300px' }}>
              <Spinner animation="border" role="status">
                <span className="visually-hidden">Загрузка...</span>
              </Spinner>
            </div>
          ) : (
            devices.length > 0 ? (
              <div className="device-cards-grid-container">
                {devices.map((device) => (
                  < DeviceCard device={device} key={device.id} />
                ))}
              </div>
            ) : (
              <div className="alert alert-info text-center" role="alert">
                <img src={NotFound} alt="Ничего не найдено" className="mb-3" style={{ maxWidth: '150px' }} />
                <p className='fw-bold fs-5'>К сожалению, устройств по заданным критериям найти не удалось.</p>
              </div>
            )
          )}

          {totalPages > 1 && (
            <div className="d-flex justify-content-left my-2">
              <PaginationComponent
                totalPages={totalPages}
                currentPage={currentPage}
                onPageChange={handlePageChange}
              />
            </div>
          )}
        </div>
      </div >

      <Offcanvas show={showFilterOffcanvas} onHide={() => setShowFilterOffcanvas(false)} placement="end">
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>Фильтры</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          <FilterFormContent
            manufacturerIds={manufacturerIds}
            buildingIds={buildingIds}
            locationIds={locationIds}
            employeeIds={employeeIds}
            typeIds={typeIds}
            departmentIds={departmentIds}
            isWorking={isWorking}
            localPriceFrom={localPriceFrom}
            setLocalPriceFrom={setLocalPriceFrom}
            localPriceTo={localPriceTo}
            setLocalPriceTo={setLocalPriceTo}
            purchaseDateFrom={purchaseDateFrom}
            purchaseDateTo={purchaseDateTo}
            warrantyDateFrom={warrantyDateFrom}
            warrantyDateTo={warrantyDateTo}
            updateParams={updateParams}
            handleResetFilters={handleResetFilters}
            parseDateTimeForInput={parseDateTimeForInput}
            formatDateTimeForBackend={formatDateTimeForBackend}
          />
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
};

export default DeviceList;