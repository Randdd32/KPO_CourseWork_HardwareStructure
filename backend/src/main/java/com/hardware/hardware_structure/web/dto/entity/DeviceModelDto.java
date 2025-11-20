package com.hardware.hardware_structure.web.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeviceModelDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    private String description;

    @NotNull
    @Min(1)
    private Long typeId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String typeName;

    @NotNull
    @Min(1)
    private Long manufacturerId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String manufacturerName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer workEfficiency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer reliability;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer energyEfficiency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userFriendliness;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer durability;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer aestheticQualities;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DeviceModelStructureElementIdDto> structureElementsIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<DeviceModelStructureElementDto> structureElements;
}
