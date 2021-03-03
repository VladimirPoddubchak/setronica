package com.poddubchak.testtask.setronica.controller;


import com.poddubchak.testtask.setronica.dto.ClientError;
import com.poddubchak.testtask.setronica.dto.InfoDto;
import com.poddubchak.testtask.setronica.dto.PriceDto;
import com.poddubchak.testtask.setronica.dto.ProductDto;
import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.model.CurrencyEnum;
import com.poddubchak.testtask.setronica.model.LanguageEnum;
import com.poddubchak.testtask.setronica.model.Product;
import com.poddubchak.testtask.setronica.service.admin.AdminProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    AdminProductService service;

    public AdminProductController(AdminProductService service) {
        this.service = service;
        log.info("AdminProductController started");
    }

    @GetMapping("/currency")
    public CurrencyEnum[] getCurrency(){
        return CurrencyEnum.values();
    }

    @GetMapping("/language")
    public LanguageEnum[] getLanguage(){
        return LanguageEnum.values();
    }


    /**
     curl -X POST http://localhost:8080/api/admin/products/ -H 'Content-Type: application/json' -d '{"language":"RUS","name":"Product_1","description":"Description for product_1","currency":"RUB","price":100}'
     */
    @PostMapping("/")
    public UUID createProduct(@RequestBody ProductDto dto){

        return service.createProduct(dto);
    }

    /**
     curl http://localhost:8080/api/admin/products
     */
    @GetMapping("/")
    public List<Product> findAllProduct(){

        return service.findAll();
    }


    /**
     curl http://localhost:8080/api/admin/products/<uuid>
     */
    @GetMapping("/{id}")
    public Product findProductById(@PathVariable String id){

        return service.findProductById(id);
    }



    /**
     curl -X DELETE http://localhost:8080/api/admin/products/<uuid>
     */
    @DeleteMapping("/{id}")
    public Boolean deleteProduct(@PathVariable String id){

        return service.deleteProductById(id);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/info/<uuid>  -H 'Content-Type: application/json' -d '{"language":"ENG","name":"Product_eng","description":"Description for product_eng"}'
     */
    @PostMapping("/info/{id}")
    public Long addInfoById(@RequestBody InfoDto dto, @PathVariable String id){

        return service.addProductInfoByProductId(id,dto);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/info/edit/<uuid>  -H 'Content-Type: application/json' -d '{"language":"RUS","name":"Product_1","description":"Description for product_1"}'
     */
    @PostMapping("/info/edit/{id}")
    public Long editInfoById(@RequestBody InfoDto dto, @PathVariable String id){

        return service.editProductInfoByProductId(id,dto);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/price/<uuid>  -H 'Content-Type: application/json' -d '{"currency":"EUR","price":555}'
     */
    @PostMapping("/price/{id}")
    public Long addPriceById(@RequestBody PriceDto dto, @PathVariable String id){

        return service.addPriceInfoByProductId(id,dto);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/price/edit/<uuid>  -H 'Content-Type: application/json' -d '{"language":"RUS","name":"Product_1","description":"Description for product_1"}'
     */
    @PostMapping("/price/edit/{id}")
    public Long editPriceById(@RequestBody PriceDto dto, @PathVariable String id){

        return service.editPriceInfoByProductId(id,dto);
    }

}
