import { useState, useEffect, useRef } from 'react';
import ReportApiService from '../components/api/ReportApiService';
import { DevicesGetService } from '../components/devices/service/DevicesApiService';
import downloadFile from '../components/utils/FileDownload';
import LoadingElement from '../components/utils/LoadingElement';
import toast from 'react-hot-toast';
import SearchableSelect from '../components/input/SearchableSelect';

const PageDevicesWithStructureReport = () => {
  const [deviceIds, setDeviceIds] = useState([]);
  const [reportHtml, setReportHtml] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const reportContainerRef = useRef(null);

  const reportApiService = new ReportApiService();

  const validateDevices = () => {
    if (!deviceIds || deviceIds.length === 0) {
      toast.error('Пожалуйста, выберите хотя бы одно устройство.');
      return false;
    }
    return true;
  };

  const handleShowReport = async () => {
    if (!validateDevices()) return;

    setIsLoading(true);
    setReportHtml('');
    try {
      const reportDto = {
        deviceIds
      };
      const htmlContent = await reportApiService.getHtml('devices-with-structure', reportDto);
      setReportHtml(htmlContent);
      toast.success('Отчет успешно загружен!');
    } catch (error) {
      toast.error('Ошибка при загрузке отчета: ' + (error.message || 'Неизвестная ошибка'));
    } finally {
      setIsLoading(false);
    }
  };

  const handleDownloadReport = async (format) => {
    if (!validateDevices()) return;

    setIsLoading(true);
    try {
      const reportDto = {
        deviceIds
      };

      switch (format) {
        case 'pdf':
          await downloadFile(reportApiService.downloadPdf('devices-with-structure', reportDto), 'report.pdf');
          break;
        case 'docx':
          await downloadFile(reportApiService.downloadDocx('devices-with-structure', reportDto), 'report.docx');
          break;
        case 'xlsx':
          await downloadFile(reportApiService.downloadXlsx('devices-with-structure', reportDto), 'report.xlsx');
          break;
        case 'html': {
          try {
            const htmlContent = await reportApiService.getHtml('devices-with-structure', reportDto);
            const blob = new Blob([htmlContent], { type: 'text/html' });
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'report.html';
            document.body.appendChild(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
          } catch (error) {
            toast.error('Ошибка при скачивании отчета: ' + (error.message || 'Неизвестная ошибка'));
          }
          break;
        }
        default:
          toast.error('Неизвестный формат экспорта.');
          break;
      }
    } catch (error) {
      console.error('Ошибка при скачивании отчета:', error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    document.body.classList.add('bg-white');
    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <div className="row g-2">
      <div className="col-12 px-0">
        <div className="block d-flex justify-content-center fs-2 fw-bold admin-title mb-1">
          Устройства со структурой
        </div>
      </div>

      <div className="col-12 mb-2 p-4 bg-light rounded">
        <div className="form-label fs-5 fw-medium mb-2">Выберите устройства:</div>
        <SearchableSelect
          apiService={DevicesGetService}
          placeholder="Выберите устройства"
          displayField="serialNumber"
          valueField="id"
          buildQuery={(term) => `?searchInfo=${encodeURIComponent(term)}&page=0&size=40&sortType=ID_ASC`}
          isMulti
          isPagination
          className="w-100"
          onChange={(values) => setDeviceIds(values)}
          required
        />
      </div>

      <div className="col-12 mb-3 d-flex flex-column flex-md-row align-items-center justify-content-center g-3">
        <button
          onClick={handleShowReport}
          className="btn btn-dark px-4 py-2 fw-semibold rounded-lg shadow-sm mb-3 mb-md-0 me-md-3 d-none d-lg-block"
          disabled={isLoading}
        >
          {isLoading ? 'Загрузка...' : 'Вывести отчет на сайте'}
        </button>
        <div className="dropdown">
          <button
            className="btn btn-dark dropdown-toggle px-5 py-2 fw-semibold rounded-lg shadow-sm d-flex align-items-center"
            type="button"
            id="dropdownMenuButton"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            disabled={isLoading}
          >
            <i className="bi bi-download me-2"></i> Экспорт
          </button>
          <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <li>
              <button
                type="button"
                className="dropdown-item d-flex align-items-center"
                onClick={() => handleDownloadReport('pdf')}
              >
                <i className="bi bi-file-earmark-pdf-fill text-danger me-2"></i> PDF
              </button>
            </li>
            <li>
              <button
                type="button"
                className="dropdown-item d-flex align-items-center"
                onClick={() => handleDownloadReport('docx')}
              >
                <i className="bi bi-file-earmark-word-fill text-primary me-2"></i> Word (docx)
              </button>
            </li>
            <li>
              <button
                type="button"
                className="dropdown-item d-flex align-items-center"
                onClick={() => handleDownloadReport('xlsx')}
              >
                <i className="bi bi-file-earmark-excel-fill text-success me-2"></i> Excel (xlsx)
              </button>
            </li>
            <li>
              <button
                type="button"
                className="dropdown-item d-flex align-items-center"
                onClick={() => handleDownloadReport('html')}
              >
                <i className="bi bi-file-earmark-code-fill text-warning me-2"></i> HTML
              </button>
            </li>
          </ul>
        </div>
      </div>

      {reportHtml && (
        <div className="col-12 mb-3 d-none d-lg-block">
          <div
            ref={reportContainerRef}
            dangerouslySetInnerHTML={{ __html: reportHtml }}
            className="report-content"
          />
        </div>
      )}
      {isLoading && <LoadingElement />}
    </div>
  );
};

export default PageDevicesWithStructureReport;