package com.hardware.hardware_structure.web.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StructureElementModelDto {
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

    @NotNull
    @Min(0)
    @Max(100)
    private Integer workEfficiency;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer reliability;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer energyEfficiency;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer userFriendliness;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer durability;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer aestheticQualities;
}
