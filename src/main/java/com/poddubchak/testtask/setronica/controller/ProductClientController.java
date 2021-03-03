package com.poddubchak.testtask.setronica.controller;


import com.poddubchak.testtask.setronica.dto.ClientError;
import com.poddubchak.testtask.setronica.dto.ClientProductDto;
import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.service.client.ProductClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/client/products")
public class ProductClientController {

    @Autowired
    ProductClientService productClientService;
    public ProductClientController(ProductClientService productClientService) {
        this.productClientService = productClientService;
        log.info("ProductClientController start");
    }


    @ExceptionHandler({ProductNotFoundException.class, NotFoundByLanguageException.class, NotFoundByCurrencyException.class})
    public ResponseEntity<ClientError> handleNotFoundException(final Exception ex){
        final String error = "Not found error handling";
        log.error(error, ex.getCause());

        ClientError clientError = new ClientError(400, ex.getMessage());
        return new ResponseEntity<ClientError>(clientError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalIdException.class, NoSuchLanguageException.class, NoSuchCurrencyException.class})
    public ResponseEntity<ClientError> handleBadRequestException(final Exception ex){
        final String error = "Illegal argument error handling";
        log.error(error, ex.getCause());

        ClientError clientError = new ClientError(500, ex.getMessage());
        return new ResponseEntity<ClientError>(clientError, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/search/{lang}/{curr}")
    public List<ClientProductDto> searchByLanguageAndCurrency(@PathVariable  String lang,@PathVariable  String curr, @RequestParam(name="text") String text){
        log.info(lang+"//"+curr+"//"+text);
        return productClientService.searchByLanguageAndCurrency(lang,curr,text);
    }

    @GetMapping("all/{lang}/{curr}")
    public List<ClientProductDto> findAllByLanguageAndCurrency(@PathVariable String lang,@PathVariable  String curr){
        return productClientService.findAllByLanguageAndCurrency(lang,curr);
    }

    @GetMapping("id/{id}/{lang}/{curr}")
    public ClientProductDto findByIdByLanguageAndCurrency(@PathVariable String id, @PathVariable String lang, @PathVariable  String curr){
        return productClientService.findByIdByLanguageAndCurrency(id,lang,curr);
    }
}
