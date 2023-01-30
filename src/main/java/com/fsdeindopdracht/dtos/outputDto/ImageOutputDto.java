package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ImageOutputDto {

    public Long id;
    private String fileName;
    private String type;
    private String filePath;
}