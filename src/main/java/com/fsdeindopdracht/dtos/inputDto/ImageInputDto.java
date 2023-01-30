package com.fsdeindopdracht.dtos.inputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ImageInputDto {

    public Long id;
    private String fileName;
    private String type;
    private String filePath;
}
