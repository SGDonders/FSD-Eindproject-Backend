package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ImageOutputDto {
    public Long id;
    public Double name;
    public Double type;
    public Double category;
    public int Height;
    public int width;
    public String filePath;
}