package com.eazybytes.cards.controller;

import com.eazybytes.cards.constant.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.dto.ResponseDto;
import com.eazybytes.cards.service.ICardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardsController {

    @Autowired
    private ICardsService iCardsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
            @Pattern(regexp ="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                      String mobileNumber) {
         iCardsService.createCard(mobileNumber);

         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                                                         @Pattern(regexp ="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                         String mobileNumber) {
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(cardsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails( @Valid @RequestBody CardsDto cardsDto) {

            boolean isUpdated = iCardsService.updateCard(cardsDto);

            if(isUpdated)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
            else
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam
                                                             @Pattern(regexp="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                             String mobileNumber) {

        boolean isDeleted = iCardsService.deleteCard(mobileNumber);

        if(isDeleted)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
    }
}
