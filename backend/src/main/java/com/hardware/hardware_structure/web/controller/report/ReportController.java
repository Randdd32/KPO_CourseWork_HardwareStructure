package com.hardware.hardware_structure.web.controller.report;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.model.enums.ReportType;
import com.hardware.hardware_structure.service.report.DocxExportStrategy;
import com.hardware.hardware_structure.service.report.PdfExportStrategy;
import com.hardware.hardware_structure.service.report.ReportExporter;
import com.hardware.hardware_structure.service.report.ReportService;
import com.hardware.hardware_structure.service.report.XlsxExportStrategy;
import com.hardware.hardware_structure.web.dto.report.ReportDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URL + Constants.ADMIN_PREFIX + "/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;
    private final ReportExporter exporter;
    private final PdfExportStrategy pdfStrategy;
    private final XlsxExportStrategy xlsxStrategy;
    private final DocxExportStrategy docxStrategy;

    @PostMapping("/download/pdf/{reportType}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable ReportType reportType, @RequestBody @Valid ReportDto reportDto) throws JRException {
        boolean ignorePagination = reportDto.getIgnorePagination() != null ? reportDto.getIgnorePagination() : false;
        JasperPrint print = getJasperPrint(reportType, reportDto, ignorePagination);
        exporter.setStrategy(pdfStrategy);
        byte[] file = exporter.export(print);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

    @PostMapping("/download/docx/{reportType}")
    public ResponseEntity<byte[]> downloadDocx(@PathVariable ReportType reportType, @RequestBody @Valid ReportDto reportDto) throws JRException {
        boolean ignorePagination = reportDto.getIgnorePagination() != null ? reportDto.getIgnorePagination() : false;
        JasperPrint print = getJasperPrint(reportType, reportDto, ignorePagination);
        exporter.setStrategy(docxStrategy);
        byte[] file = exporter.export(print);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping("/download/xlsx/{reportType}")
    public ResponseEntity<byte[]> downloadXlsx(@PathVariable ReportType reportType, @RequestBody @Valid ReportDto reportDto) throws JRException {
        boolean ignorePagination = reportDto.getIgnorePagination() != null ? reportDto.getIgnorePagination() : true;
        JasperPrint print = getJasperPrint(reportType, reportDto, ignorePagination);
        exporter.setStrategy(xlsxStrategy);
        byte[] file = exporter.export(print);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping("/html/{reportType}")
    public ResponseEntity<String> getHtml(@PathVariable ReportType reportType, @RequestBody @Valid ReportDto reportDto) throws JRException {
        boolean ignorePagination = reportDto.getIgnorePagination() != null ? reportDto.getIgnorePagination() : true;
        JasperPrint print = getJasperPrint(reportType, reportDto, ignorePagination);
        String html = exporter.getHtml(print);
        return ResponseEntity.ok(html);
    }

    private JasperPrint getJasperPrint(ReportType reportType, ReportDto reportDto, boolean ignorePagination) throws JRException {
        return switch (reportType) {
            case DEVICES_BY_DATE -> service.generateDevicesByDateReport(reportDto, ignorePagination);
            case DEVICES_WITH_STRUCTURE -> service.generateDevicesWithStructureReport(reportDto, ignorePagination);
            case LOCATIONS_WITH_EMPLOYEES -> service.generateLocationsWithEmployeesReport(reportDto, ignorePagination);
        };
    }
}
