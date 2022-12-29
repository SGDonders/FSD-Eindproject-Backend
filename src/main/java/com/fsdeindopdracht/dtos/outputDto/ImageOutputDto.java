package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ImageOutputDto {
    private Long id;
    private Double name;
    private Double type;
    private Double category;
    private int Height;
    private int width;
}