package com.hardware.hardware_structure.service.report;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

class ReportExporterIntegrationTests extends AbstractIntegrationTest {

    @Autowired
    private ReportExporter reportExporter;

    @Autowired
    private PdfExportStrategy pdfExportStrategy;

    @Autowired
    private DocxExportStrategy docxExportStrategy;

    @Autowired
    private XlsxExportStrategy xlsxExportStrategy;

    private JasperPrint dummyJasperPrint;

    @BeforeEach
    void setUp() {
        dummyJasperPrint = new JasperPrint();
        dummyJasperPrint.setName("TestReport");
        dummyJasperPrint.setPageWidth(595);
        dummyJasperPrint.setPageHeight(842);

        dummyJasperPrint.addPage(new JRBasePrintPage());
    }

    @Test
    void exportPdfTest() throws JRException {
        reportExporter.setStrategy(pdfExportStrategy);

        byte[] result = reportExporter.export(dummyJasperPrint);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.length > 0);

        String fileHeader = new String(result, 0, 4, StandardCharsets.UTF_8);
        Assertions.assertTrue(fileHeader.startsWith("%PDF"), "Результат должен быть PDF файлом");
    }

    @Test
    void exportDocxTest() throws JRException {
        reportExporter.setStrategy(docxExportStrategy);

        byte[] result = reportExporter.export(dummyJasperPrint);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.length > 0);

        // DOCX - это ZIP архив, он должен начинаться с байтов 'PK' (0x50 0x4B)
        Assertions.assertEquals(0x50, result[0]);
        Assertions.assertEquals(0x4B, result[1]);
    }

    @Test
    void exportXlsxTest() throws JRException {
        reportExporter.setStrategy(xlsxExportStrategy);

        byte[] result = reportExporter.export(dummyJasperPrint);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.length > 0);

        // XLSX - это тоже ZIP архив (OpenXML), проверка на 'PK'
        Assertions.assertEquals(0x50, result[0]);
        Assertions.assertEquals(0x4B, result[1]);
    }

    @Test
    void getHtmlTest() throws JRException {
        String htmlOutput = reportExporter.getHtml(dummyJasperPrint);

        Assertions.assertNotNull(htmlOutput);
        Assertions.assertFalse(htmlOutput.isEmpty());

        Assertions.assertTrue(htmlOutput.contains("<html>"));
        Assertions.assertTrue(htmlOutput.contains("</html>"));
    }
}
