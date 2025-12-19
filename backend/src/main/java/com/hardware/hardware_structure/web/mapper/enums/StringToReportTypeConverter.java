package com.hardware.hardware_structure.web.mapper.enums;

import com.hardware.hardware_structure.model.enums.ReportType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToReportTypeConverter implements Converter<String, ReportType> {
    @Override
    public ReportType convert(String source) {
        return switch (source.toLowerCase()) {
            case "devices-by-date" -> ReportType.DEVICES_BY_DATE;
            case "devices-with-structure" -> ReportType.DEVICES_WITH_STRUCTURE;
            case "locations-with-employees" -> ReportType.LOCATIONS_WITH_EMPLOYEES;
            default -> throw new IllegalArgumentException("Unknown report type: " + source);
        };
    }
}
