package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ScaleDto {
    private UUID id;
    private UUID methodologyId;
    private String name;
    private Boolean isTotal;
    private String description;
}
