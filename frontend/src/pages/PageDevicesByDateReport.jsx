import { useState, useEffect, useRef } from 'react';
import ReportApiService from '../components/api/ReportApiService';
import downloadFile from '../components/utils/FileDownload';
import LoadingElement from '../components/utils/LoadingElement'
import toast from 'react-hot-toast';

import DatePicker, { registerLocale, setDefaultLocale } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ru from 'date-fns/locale/ru';

registerLocale('ru', ru);
setDefaultLocale('ru');

const PageDevicesByDateReport = () => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  const [reportHtml, setReportHtml] = useState('');

  const [isLoading, setIsLoading] = useState(false);

  const reportContainerRef = useRef(null);

  const reportApiService = new ReportApiService();

  const formatDateTimeForBackend = (date) => {
    if (!date)
      return null;
    return date.toISOString();
  };

  const validateDates = () => {
    if (!startDate || !endDate) {
      toast.error('Пожалуйста, выберите обе даты: начала и конца.');
      return false;
    }

    if (startDate.getTime() > endDate.getTime()) {
      toast.error('Дата начала не может быть позже даты конца.');
      return false;
    }
    return true;
  };

  const handleShowReport = async () => {
    if (!validateDates()) {
      return;
    }

    setIsLoading(true);
    setReportHtml('');
    try {
      const reportDto = {
        dateFrom: formatDateTimeForBackend(startDate),
        dateTo: formatDateTimeForBackend(endDate)
      };
      const htmlContent = await reportApiService.getHtml('devices-by-date', reportDto);
      setReportHtml(htmlContent);
      toast.success('Отчет успешно загружен!');
    } catch (error) {
      toast.error('Ошибка при загрузке отчета: ' + (error.message || 'Неизвестная ошибка'));
    } finally {
      setIsLoading(false);
    }
  };

  const handleDownloadReport = async (format) => {
    if (!validateDates()) {
      return;
    }

    setIsLoading(true);
    try {
      const reportDto = {
        dateFrom: formatDateTimeForBackend(startDate),
        dateTo: formatDateTimeForBackend(endDate)
      };

      switch (format) {
        case 'pdf':
          await downloadFile(
            reportApiService.downloadPdf('devices-by-date', reportDto),
            'report.pdf'
          );
          break;
        case 'docx':
          await downloadFile(
            reportApiService.downloadDocx('devices-by-date', reportDto),
            'report.docx'
          );
          break;
        case 'xlsx':
          await downloadFile(
            reportApiService.downloadXlsx('devices-by-date', reportDto),
            'report.xlsx'
          );
          break;
        case 'html': {
          try {
            const htmlContent = await reportApiService.getHtml('devices-by-date', reportDto);
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
          Список устройств (по дате покупки)
        </div>
      </div>

      <div className="col-12 mb-2 p-4 bg-light rounded">
        <div className="d-flex flex-column flex-md-row align-items-center justify-content-center g-3">
          <div className="d-flex flex-column align-items-center me-md-5">
            <label htmlFor="startDate" className="form-label fs-5 fw-medium mb-2">
              Начало периода
            </label>
            <DatePicker
              selected={startDate ? new Date(startDate) : null}
              onChange={(date) => setStartDate(date)}
              showTimeSelect
              showTimeSelectSeconds
              timeFormat="HH:mm:ss"
              timeIntervals={15}
              timeCaption="Время"
              dateFormat="yyyy-MM-dd HH:mm:ss"
              className="form-control rounded-2"
              locale="ru"
              placeholderText="Выберите дату"
            />
          </div>

          <div className="d-flex flex-column align-items-center mt-3 mt-md-0">
            <label htmlFor="endDate" className="form-label fs-5 fw-medium mb-2">
              Конец периода
            </label>
            <DatePicker
              selected={endDate ? new Date(endDate) : null}
              onChange={(date) => setEndDate(date)}
              showTimeSelect
              showTimeSelectSeconds
              timeFormat="HH:mm:ss"
              timeIntervals={15}
              timeCaption="Время"
              dateFormat="yyyy-MM-dd HH:mm:ss"
              className="form-control rounded-2"
              locale="ru"
              placeholderText="Выберите дату"
            />
          </div>
        </div>
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
      {isLoading && (
        <LoadingElement />
      )}
    </div>
  );
};

export default PageDevicesByDateReport;