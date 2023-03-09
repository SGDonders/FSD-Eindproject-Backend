package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.inputDto.SpecialOfferInputDto;
import com.fsdeindopdracht.dtos.outputDto.SpecialOfferOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.SpecialOffer;
import com.fsdeindopdracht.repositories.SpecialOfferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SpecialOfferService {

    private final SpecialOfferRepository specialOfferRepository;

    public SpecialOfferService(SpecialOfferRepository specialOfferRepository) {
        this.specialOfferRepository = specialOfferRepository;
    }


    // Function for getMapping a pecialOffer.
    public SpecialOfferOutputDto getSpecialOffer(Long id) {
        Optional<SpecialOffer> requestedSpecialOffer = specialOfferRepository.findById(id);
        if (requestedSpecialOffer.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            return transferSpecialOfferToOutputDto(requestedSpecialOffer.get());
        }
    }


    // Function for getMapping all specialOffers.
    public List<SpecialOfferOutputDto> getAllSpecialOffers() {
        List<SpecialOffer> optionalSpecialOffer = specialOfferRepository.findAll();
        List<SpecialOfferOutputDto> SpecialOfferOutputDtoList = new ArrayList<>();

        if (optionalSpecialOffer.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            for (SpecialOffer specialOffer : optionalSpecialOffer) {
                SpecialOfferOutputDto specialOfferOutputDto = transferSpecialOfferToOutputDto(specialOffer);

                SpecialOfferOutputDtoList.add(specialOfferOutputDto);

            }
        }
        return SpecialOfferOutputDtoList;
    }


    // Function for PostMapping.
    public SpecialOffer createSpecialOffer(SpecialOfferInputDto specialOfferInputDto) {
        SpecialOffer newSpecialOffer = transferInputDtoToSpecialOffer(specialOfferInputDto);
        return specialOfferRepository.save(newSpecialOffer);
    }


    // Function for patchMapping.
    public SpecialOfferOutputDto updateSpecialOffer(Long id, SpecialOfferInputDto specialOfferInputDto) {

        Optional<SpecialOffer> optionalSpecialOffer = specialOfferRepository.findById(id);

        if (optionalSpecialOffer.isPresent()) {

            SpecialOffer specialOfferUpdate = optionalSpecialOffer.get();

            if (specialOfferInputDto.getName() != null) {
                specialOfferUpdate.setName(specialOfferInputDto.getName());
            }
            if (specialOfferInputDto.getDescription() != null) {
                specialOfferUpdate.setDescription(specialOfferInputDto.getDescription());
            }
            if (specialOfferInputDto.getStartDate() != null) {
                specialOfferUpdate.setStartDate(specialOfferInputDto.getStartDate());
            }
            if (specialOfferInputDto.getEndDate() != null) {
                specialOfferUpdate.setEndDate(specialOfferInputDto.getEndDate());
            }
            if (specialOfferInputDto.getDiscount() != null) {
                specialOfferUpdate.setDiscount(specialOfferInputDto.getDiscount());
            }
            if (specialOfferInputDto.getEnabled() != null) {
                specialOfferUpdate.setEnabled(specialOfferInputDto.getEnabled());
            }

            SpecialOffer updatedSpecialOffer = specialOfferRepository.save(specialOfferUpdate);
            return transferSpecialOfferToOutputDto(updatedSpecialOffer);
        } else {
            throw new RecordNotFoundException("SpecialOffer not found!");

        }
    }


    // Function for deleteMapping.
    public void deleteSpecialOffer(Long id) {
        Optional<SpecialOffer> optionalSpecialOffer = specialOfferRepository.findById(id);
        // optional tv.
        if (optionalSpecialOffer.isEmpty()) {
            throw new RecordNotFoundException("Television already removed or doesn't exist!");
            // throw exception.
        } else {
            SpecialOffer SpecialOfferObj = optionalSpecialOffer.get();
            // er een tv van.
            specialOfferRepository.delete(SpecialOfferObj);
        }
    }


    // Mapper method inputDto to specialOffer.
    public SpecialOffer transferInputDtoToSpecialOffer(SpecialOfferInputDto specialOfferInputDto) {

        SpecialOffer newSpecialOffer = new SpecialOffer();

        newSpecialOffer.setName(specialOfferInputDto.getName());
        newSpecialOffer.setDescription(specialOfferInputDto.getDescription());
        newSpecialOffer.setStartDate(specialOfferInputDto.getStartDate());
        newSpecialOffer.setEndDate(specialOfferInputDto.getEndDate());
        newSpecialOffer.setDiscount(specialOfferInputDto.getDiscount());
        newSpecialOffer.setEnabled(specialOfferInputDto.getEnabled());

        return newSpecialOffer;
    }


    // Mapper method specialOffer to outputDto.
    public SpecialOfferOutputDto transferSpecialOfferToOutputDto(SpecialOffer specialOffer) {

        SpecialOfferOutputDto specialOfferOutputDto = new SpecialOfferOutputDto();

        specialOfferOutputDto.setName(specialOffer.getName());
        specialOfferOutputDto.setDescription(specialOffer.getDescription());
        specialOfferOutputDto.setStartDate(specialOffer.getStartDate());
        specialOfferOutputDto.setEndDate(specialOffer.getEndDate());
        specialOfferOutputDto.setDiscount(specialOffer.getDiscount());
        specialOfferOutputDto.setEnabled(specialOffer.getEnabled());

        return specialOfferOutputDto;
    }
}
