package com.fsdeindopdracht.dtos.inputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ImageInputDto {

    public Long id;
    public Double name;
    public Double type;
    public Double category;
    public int Height;
    public int width;
    public String filePath;
}
