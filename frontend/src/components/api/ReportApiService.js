import { ApiClient, FileApiClient } from './ApiClient';

class ReportApiService {
  async downloadPdf(reportType, body) {
    return FileApiClient.post(`/admin/report/download/pdf/${reportType}`, body);
  }

  async downloadDocx(reportType, body) {
    return FileApiClient.post(`/admin/report/download/docx/${reportType}`, body);
  }

  async downloadXlsx(reportType, body) {
    return FileApiClient.post(`/admin/report/download/xlsx/${reportType}`, body);
  }

  async getHtml(reportType, body) {
    return ApiClient.post(`/admin/report/html/${reportType}`, body);
  }
}

export default ReportApiService;