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

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    // Wrapper functie
    public Image transferInputDtoToImage(ImageInputDto imageInputDto) {

        Image newImage = new Image();

        newImage.setFileName(imageInputDto.getFileName());
        newImage.setType(imageInputDto.getType());
        newImage.setFilePath(imageInputDto.getFilePath());

        return imageRepository.save(newImage);
    }

    // Wrapper Functie
    public ImageOutputDto transferImageToOutputDto(Image image) {

        ImageOutputDto imageOutputDto = new ImageOutputDto();

        imageOutputDto.setFileName(image.getFileName());
        imageOutputDto.setType(image.getType());
        imageOutputDto.setFilePath(image.getFilePath());

        return imageOutputDto;
    }

    // Functie voor GetMapping van alle Images.
    public List<ImageOutputDto> getAllImages() {
        List<Image> optionalImage = imageRepository.findAll();
        List<ImageOutputDto> ImageOutputDtoList = new ArrayList<>();

        if (optionalImage.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            for (Image image : optionalImage) {
                ImageOutputDto imageOutputDto = transferImageToOutputDto(image);

                ImageOutputDtoList.add(imageOutputDto);

            }
        }
        return ImageOutputDtoList;
    }

    // Functie voor getMapping één Image.
    public ImageOutputDto getImage(Long id) {
        Optional<Image> requestedImage = imageRepository.findById(id);
        if (requestedImage.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            return transferImageToOutputDto(requestedImage.get());
        }
    }

    // Functie voor deleteMapping.
    public void deleteImage(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        // optional tv.
        if (optionalImage.isEmpty()) {
            throw new RecordNotFoundException("Television already removed or doesn't exist!");
            // throw exception.
        } else {
            Image ImageObj = optionalImage.get();
            // er een tv van.
            imageRepository.delete(ImageObj);
        }
    }

    // Functie voor PostMapping
    public Image createImage(ImageInputDto imageInputDto) {
        Image newImage = transferInputDtoToImage(imageInputDto);
        return imageRepository.save(newImage);
    }


    // Functie voor PutMapping.
    public ImageOutputDto updateImage(Long id, ImageInputDto imageInputDto) {

        Optional<Image> optionalImage = imageRepository.findById(id);

        if (optionalImage.isPresent()) {

            Image imageUpdate = optionalImage.get();

            if (imageInputDto.getFileName() != null) {
                imageUpdate.setFileName(imageInputDto.getFileName());
            }
            if (imageInputDto.getType() != null) {
                imageUpdate.setType(imageInputDto.getType());
            }
            if (imageInputDto.getFilePath() != null) {
                imageUpdate.setFilePath(imageInputDto.getFilePath());
            }
            Image updatedImage = imageRepository.save(imageUpdate);
            return transferImageToOutputDto(updatedImage);
        } else {
            throw new RecordNotFoundException("Image not found!");

        }
    }
}
