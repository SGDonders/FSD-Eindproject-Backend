package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.ImageInputDto;
import com.fsdeindopdracht.dtos.outputDto.ImageOutputDto;
import com.fsdeindopdracht.models.Image;
import com.fsdeindopdracht.services.ImageService;
import com.fsdeindopdracht.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    // GetMapping request alle Images.
    @GetMapping("")
    public ResponseEntity<List<ImageOutputDto>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }


    // GetMapping request voor één Image.
    @GetMapping("{id}")
    public ResponseEntity<ImageOutputDto> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImage(id));
    }


    // DeleteMapping request voor een Image.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id); //
        return ResponseEntity.noContent().build();
    }


    // PostMapping request voor een Image.
    @PostMapping("")
    public ResponseEntity<Object> createImage(@Valid @RequestBody ImageInputDto imageInputDto,
                                                 BindingResult bindingResult) throws ValidationException {
        Utils.reportErrors(bindingResult);

        Image savedImage = imageService.createImage(imageInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath
                ().path("/Image/" + savedImage.getId()).toUriString());

        return ResponseEntity.created(uri).body(savedImage);
    }


    // PutMapping voor een Image.
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateImage(@PathVariable Long id, @RequestBody ImageInputDto imageInputDto) {
        ImageOutputDto ciModuleOutputDto = imageService.updateImage(id, imageInputDto);
        return ResponseEntity.ok().body(ciModuleOutputDto);
    }
}
