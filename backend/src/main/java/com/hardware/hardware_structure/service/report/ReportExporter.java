package com.hardware.hardware_structure.service.report;

import com.hardware.hardware_structure.core.log.Loggable;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
@Getter
@Setter
public class ReportExporter {
    private ReportExporterStrategy strategy;

    @Loggable
    public byte[] export(JasperPrint jasperPrint) throws JRException {
        return strategy.export(jasperPrint);
    }

    @Loggable
    public String getHtml(JasperPrint jasperPrint) throws JRException {
        HtmlExporter exporter = new HtmlExporter();
        StringWriter writer = new StringWriter();
        SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(writer);

        output.setImageHandler(new WebHtmlResourceHandler("https://cdn1.iconfinder.com/data/icons/muop-the-cat/100/022-thinking-question-doubt-wonder-cat-sticker-emoji-1024.png"));

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(output);
        exporter.exportReport();

        return writer.toString();
    }
}
