package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.inputDto.ImageInputDto;
import com.fsdeindopdracht.dtos.outputDto.ImageOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Image;
import com.fsdeindopdracht.repositories.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository ciModuleRepository;

    public ImageService(ImageRepository ciModuleRepository) {
        this.ciModuleRepository = ciModuleRepository;
    }


    // Wrapper functie
    public Image transferInputDtoToImage(ImageInputDto ciModuleInputDto) {

        Image newImage = new Image();

        newImage.setName(ciModuleInputDto.getName());
        newImage.setType(ciModuleInputDto.getType());
        newImage.setFilePath(ciModuleInputDto.getFilePath());

        return ciModuleRepository.save(newImage);
    }

    // Wrapper Functie
    public ImageOutputDto transferImageToOutputDto(Image ciModule) {

        ImageOutputDto ciModuleOutputDto = new ImageOutputDto();

        ciModuleOutputDto.setName(ciModule.getName());
        ciModuleOutputDto.setType(ciModule.getType());
        ciModuleOutputDto.setFilePath(ciModule.getFilePath());

        return ciModuleOutputDto;
    }

    // Functie voor GetMapping van alle Images.
    public List<ImageOutputDto> getAllImages() {
        List<Image> optionalImage = ciModuleRepository.findAll();
        List<ImageOutputDto> ImageOutputDtoList = new ArrayList<>();

        if (optionalImage.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            for (Image ciModule : optionalImage) {
                ImageOutputDto ciModuleOutputDto = transferImageToOutputDto(ciModule);

                ImageOutputDtoList.add(ciModuleOutputDto);

            }
        }
        return ImageOutputDtoList;
    }

    // Functie voor getMapping één Image.
    public ImageOutputDto getImage(Long id) {
        Optional<Image> requestedImage = ciModuleRepository.findById(id);
        if (requestedImage.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            return transferImageToOutputDto(requestedImage.get());
        }
    }

    // Functie voor deleteMapping.
    public void deleteImage(Long id) {
        Optional<Image> optionalImage = ciModuleRepository.findById(id);
        // optional tv.
        if (optionalImage.isEmpty()) {
            throw new RecordNotFoundException("Television already removed or doesn't exist!");
            // throw exception.
        } else {
            Image ImageObj = optionalImage.get();
            // er een tv van.
            ciModuleRepository.delete(ImageObj);
        }
    }

    // Functie voor PostMapping
    public Image createImage(ImageInputDto ciModuleInputDto) {
        Image newImage = transferInputDtoToImage(ciModuleInputDto);
        return ciModuleRepository.save(newImage);
    }


    // Functie voor PutMapping.
    public ImageOutputDto updateImage(Long id, ImageInputDto ciModuleInputDto) {

        Optional<Image> optionalImage = ciModuleRepository.findById(id);

        if (optionalImage.isPresent()) {

            Image ciModuleUpdate = optionalImage.get();

            if (ciModuleInputDto.getName() != null) {
                ciModuleUpdate.setName(ciModuleInputDto.getName());
            }
            if (ciModuleInputDto.getType() != null) {
                ciModuleUpdate.setType(ciModuleInputDto.getType());
            }
            if (ciModuleInputDto.getFilePath() != null) {
                ciModuleUpdate.setFilePath(ciModuleInputDto.getFilePath());
            }
            Image updatedImage = ciModuleRepository.save(ciModuleUpdate);
            return transferImageToOutputDto(updatedImage);
        } else {
            throw new RecordNotFoundException("Image not found!");

        }
    }
}
