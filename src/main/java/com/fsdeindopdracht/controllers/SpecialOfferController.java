package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.SpecialOfferInputDto;
import com.fsdeindopdracht.dtos.outputDto.SpecialOfferOutputDto;
import com.fsdeindopdracht.models.SpecialOffer;
import com.fsdeindopdracht.services.SpecialOfferService;
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
@RequestMapping("/specialoffers")
public class SpecialOfferController {

    private final SpecialOfferService specialOfferService;

    public SpecialOfferController(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }


    // GetMapping request for a specialoffer.
    @GetMapping("{id}")
    public ResponseEntity<SpecialOfferOutputDto> getSpecialOffer(@PathVariable Long id) {
        return ResponseEntity.ok(specialOfferService.getSpecialOffer(id));
    }


    // GetMapping request all specialoffers.
    @GetMapping("")
    public ResponseEntity<List<SpecialOfferOutputDto>> getAllAccounts() {
        return ResponseEntity.ok(specialOfferService.getAllSpecialOffers());
    }


    // PostMapping request for a specialOffer
    @PostMapping("")
    public ResponseEntity<Object> createSpecialOffer(@Valid @RequestBody SpecialOfferInputDto specialOfferInputDto,
                                                 BindingResult bindingResult) throws ValidationException {
        Utils.reportErrors(bindingResult);

        SpecialOffer savedSpecialOffer = specialOfferService.createSpecialOffer(specialOfferInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath
                ().path("/specialoffer/" + savedSpecialOffer.getId()).toUriString());

        return ResponseEntity.created(uri).body(savedSpecialOffer);
    }


    // PutMapping request for a specialOffer
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccount(@PathVariable Long id, @RequestBody SpecialOfferInputDto specialOfferInputDto) {
        SpecialOfferOutputDto specialOfferOutputDto = specialOfferService.updateSpecialOffer(id, specialOfferInputDto);
        return ResponseEntity.ok().body(specialOfferOutputDto);
    }


    // DeleteMapping request for a specialOffer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpecialOffer(@PathVariable Long id) {
        specialOfferService.deleteSpecialOffer(id); //
        return ResponseEntity.noContent().build();
    }

}
