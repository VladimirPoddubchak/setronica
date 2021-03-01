package com.poddubchak.testtask.setronica.controller;


import com.poddubchak.testtask.setronica.dto.PriceDto;
import com.poddubchak.testtask.setronica.dto.ProductDto;
import com.poddubchak.testtask.setronica.dto.InfoDto;
import com.poddubchak.testtask.setronica.model.PriceInfo;
import com.poddubchak.testtask.setronica.model.Product;
import com.poddubchak.testtask.setronica.model.ProductInfo;
import com.poddubchak.testtask.setronica.service.AdminProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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

    /**
     curl -X POST http://localhost:8080/api/admin/products/ -H 'Content-Type: application/json' -d '{"language":"RUS","name":"Product_1","description":"Description for product_1","currency":"RUB","price":100}'
     */
    @PostMapping("/")
    public HttpEntity<UUID> createProduct(@RequestBody ProductDto dto){

        UUID result = null;

        try {
            result = service.createProduct(dto);
        }catch (IllegalArgumentException ex){
            log.warn(ex+" ProductDto "+dto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid ProductDto supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Create product request: ",dto);
        return ResponseEntity.ok(result);
    }

    /**
     curl http://localhost:8080/api/admin/products
     */
    @GetMapping("/")
    public HttpEntity<List<Product>> findAllProduct(){

        List<Product> result = null;
        try {
            result = service.findAll();
         }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Get all products request");
        return ResponseEntity.ok(result);
    }


    /**
     curl http://localhost:8080/api/admin/products/<uuid>
     */
    @GetMapping("/{id}")
    public HttpEntity<Product> findProductById(@PathVariable String id){

        Product result = null;
        try {
            result = service.findById(id);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid UUID supplied: "+id,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UUID supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist, id: "+id,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Get product request: ",id);
        return ResponseEntity.ok(result);
    }


    /**
     curl  http://localhost:8080/api/admin/products/lang/RUS
     */
    @GetMapping("/lang/{lang}")
    public HttpEntity<List<Product>> allProductsByLanguage(@PathVariable String lang){
        List<Product> result = null;
        try {
            result = service.findAllByLang(lang);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid language supplied: "+lang,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid language supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Get all products by language request. lang: "+lang);
        return ResponseEntity.ok(result);
    }

    /**
     curl  http://localhost:8080/api/admin/products/currency/USD
     */
    @GetMapping("/currency/{curr}")
    public HttpEntity<List<Product>> allProductsByCurrency(@PathVariable String curr){
        List<Product> result = null;
        try {
            result = service.findAllByCurrency(curr);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid Currency supplied: "+curr,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid currency supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Get all products by currency request. curr: "+curr);
        return ResponseEntity.ok(result);
    }

    /**
     curl  http://localhost:8080/api/admin/products/ENG/USD
     */
    @GetMapping("/{lang}/{curr}")
    public HttpEntity<List<Product>> findAllProductsByLanguageAndCurrency(@PathVariable String lang,@PathVariable String curr){
        List<Product> result = null;
        try {
            result = service.findAllByLanguageAndCurrency(lang,curr);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid Language or Currency supplied: "+curr,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid currency supplied",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Get all products by currency request: ",curr);
        return ResponseEntity.ok(result);
    }

    /**
     curl  http://localhost:8080/api/admin/products/ENG/USD/<uuid>
     */
    @GetMapping("/{lang}/{curr}/{id}")
    public HttpEntity<Product> findByLanguageAndCurrencyAndId(@PathVariable String lang,@PathVariable String curr,@PathVariable String id){
        Product result = null;
        try {
            result = service.findByLanguageAndCurrencyAndId(lang,curr,id);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid Language or Currency or UUID supplied. ",ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Language or Currency or UUID supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist, id:"+id+ " lang:"+lang+" curr:"+curr,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Get all products by currency request: ",curr);
        return ResponseEntity.ok(result);
    }

    /**
     curl  http://localhost:8080/api/admin/products/search/ENG/USD/<text>
     */
    @GetMapping("/search/{lang}/{curr}/{text}")
    public HttpEntity<List<Product>> searchByLanguageAndCurrency(@PathVariable String lang,@PathVariable String curr,@PathVariable String text){
        List<Product> result = null;
        try {
            result = service.searchByLanguageAndCurrency(lang,curr,text);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid Language or Currency or UUID supplied. ",ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Language or Currency or Text supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist. lang:"+lang+" curr:"+curr+" text:"+text,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Search products by language and currency. text:'"+text+"' lang:"+lang+ " curr:"+curr);
        return ResponseEntity.ok(result);
    }


    /**
     curl -X DELETE http://localhost:8080/api/admin/products/<uuid>
     */
    @DeleteMapping("/{id}")
    public HttpEntity<Boolean> deleteProduct(@PathVariable String id){

        Boolean result = false;
        try {
            result = service.deleteProduct(id);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid UUID supplied: "+id,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UUID supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist, id: "+id,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Delete product by id request. uuid: "+id);
        return ResponseEntity.ok(result);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/info/<uuid>  -H 'Content-Type: application/json' -d '{"language":"ENG","name":"Product_eng","description":"Description for product_eng"}'
     */
    @PostMapping("/info/{id}")
    public HttpEntity<ProductInfo> addInfoById(@RequestBody InfoDto dto, @PathVariable String id){
        ProductInfo result = null;
        try {
            result = service.addInfoByProductID(id,dto);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid UUID or ProductInfoDto supplied. uuid: "+id+ "dto: "+dto,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UUID or ProductInfoDto supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist, id: "+id,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Add product info request. uuid: "+id+ "info:" +dto);
        return ResponseEntity.ok(result);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/info/edit/<uuid>  -H 'Content-Type: application/json' -d '{"language":"RUS","name":"Product_1","description":"Description for product_1"}'
     */
    @PostMapping("/info/edit/{id}")
    public HttpEntity<ProductInfo> editInfoById(@RequestBody InfoDto dto, @PathVariable String id){
        ProductInfo result = null;
        try {
            result = service.editInfoByProductID(id,dto);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid UUID or ProductInfoDto supplied. uuid: "+id+ "dto: "+dto,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UUID or ProductInfoDto supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist, id: "+id,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Edit product info request. uuid: "+id+ "info:" +dto);
        return ResponseEntity.ok(result);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/price/<uuid>  -H 'Content-Type: application/json' -d '{"currency":"EUR","price":555}'
     */
    @PostMapping("/price/{id}")
    public HttpEntity<PriceInfo> addPriceById(@RequestBody PriceDto dto, @PathVariable String id){
        PriceInfo result = null;
        try {
            result = service.addPriceByProductID(id,dto);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid UUID or ProductInfoDto supplied. uuid: "+id+ "dto: "+dto,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UUID or PriceDto supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist, id: "+id,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Add product price request. uuid: "+id+ "price:" +dto);
        return ResponseEntity.ok(result);
    }

    /**
     curl -X POST http://localhost:8080/api/admin/products/price/edit/<uuid>  -H 'Content-Type: application/json' -d '{"language":"RUS","name":"Product_1","description":"Description for product_1"}'
     */
    @PostMapping("/price/edit/{id}")
    public HttpEntity<PriceInfo> editPriceById(@RequestBody PriceDto dto, @PathVariable String id){
        PriceInfo result = null;
        try {
            result = service.editPriceByProductID(id,dto);
        }catch (IllegalArgumentException ex){
            log.warn("Invalid UUID or ProductInfoDto supplied. uuid: "+id+ "dto: "+dto,ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UUID or ProductInfoDto supplied",ex);
        }catch (NoSuchElementException ex){
            log.warn("Product doesn't exist, id: "+id,ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product doesn't exist",ex);
        }catch (DataAccessException ex){
            log.warn(ex.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Service unavailable",ex);
        }
        log.info("Add product price request. uuid: "+id+ "price:" +dto);
        return ResponseEntity.ok(result);
    }
}
