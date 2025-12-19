package com.hardware.hardware_structure.web.dto.report;

import com.hardware.hardware_structure.core.validation.IsoDate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportDto {
    @IsoDate
    private String dateFrom;

    @IsoDate
    private String dateTo;

    private List<Long> locationIds;

    private List<Long> deviceIds;

    private Boolean ignorePagination;
}
