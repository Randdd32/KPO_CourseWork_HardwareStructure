package com.hardware.hardware_structure.service.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class PdfExportStrategy implements ReportExporterStrategy {
    @Override
    public byte[] export(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        return out.toByteArray();
    }
}
