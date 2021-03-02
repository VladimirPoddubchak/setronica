package com.poddubchak.testtask.setronica.service.admin;

import com.poddubchak.testtask.setronica.dto.InfoDto;
import com.poddubchak.testtask.setronica.dto.PriceDto;
import com.poddubchak.testtask.setronica.dto.ProductDto;
import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.model.*;
import com.poddubchak.testtask.setronica.repository.ProductInfoRepository;
import com.poddubchak.testtask.setronica.repository.ProductPriceRepository;
import com.poddubchak.testtask.setronica.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class AdminProductService implements AdminProductInterface {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductInfoRepository infoRepository;

    @Autowired
    ProductPriceRepository priceRepository;

    public AdminProductService(ProductRepository productRepository, ProductInfoRepository infoRepository, ProductPriceRepository priceRepository) {
        this.productRepository = productRepository;
        this.infoRepository = infoRepository;
        this.priceRepository = priceRepository;
        log.info("AdminProductService started");
    }

    @Override
    @Transactional
    public UUID createProduct(ProductDto dto) throws IllegalProductException {
        if (dto==null){
            log.error("ProductDto is null.");
            throw new IllegalProductException("ProductDto is null.");
        }
        try{
            dto.validate();
        }catch(IllegalProductException ex){
            throw new IllegalProductException("Invalid ProductDto." ,ex);
        }

        Map<Language, ProductInfo> productInfoMap = new HashMap<>();
        Map<Currency, PriceInfo> priceInfoMap = new HashMap<>();

        LocalDateTime created = LocalDateTime.now();

        productInfoMap.put(dto.getLanguage(),new ProductInfo(dto.getLanguage(), dto.getName(), dto.getDescription(), created, created));
        priceInfoMap.put(dto.getCurrency(),new PriceInfo(dto.getCurrency(), dto.getPrice(), created, created));

        Product product = productRepository.save(new Product(created, created, productInfoMap, priceInfoMap));

        return product.getId();
    }


    @Override
    @Transactional
    public Boolean deleteProductById(String id) throws ProductNotFoundException,IllegalIdException {
        if (id==null){
            log.error("UUID is null.");
            throw new IllegalIdException("UUID is null.");
        }

        UUID uuid = UUID.fromString(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        productRepository.deleteById(uuid);
        return true;
    }

    @Override
    public Product findProductById(String id) throws ProductNotFoundException,IllegalIdException {
        if (id==null){
            log.error("UUID is null.");
            throw new IllegalIdException("UUID is null.");
        }

        UUID uuid = UUID.fromString(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        Product result = productRepository.findById(uuid).get();

        return result;
    }

    @Override
    @Transactional
    public Long addProductInfoByProductId(String id, InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException,IllegalIdException {
        if (id==null){
            log.error("UUID is null.");
            throw new IllegalIdException("UUID is null.");
        }

        UUID uuid = UUID.fromString(id);

        if (dto==null){
            log.error("InfoDto is null.");
            throw new IllegalProductInfoException("InfoDto is null.");
        }

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        try{
            dto.validate();
        }catch(IllegalProductInfoException ex){
            throw new IllegalProductInfoException("Invalid InfoDto." ,ex);
        }

        Product product = productRepository.findById(uuid).get();

        if (product.getProductInfoMap().containsKey(dto.getLanguage())){
            log.error("ProductInfo already exists.");
            throw new IllegalProductInfoException("ProductInfo already exists");
        }

        LocalDateTime modified = LocalDateTime.now();
        product.getProductInfoMap().put(dto.getLanguage(),
                new ProductInfo(dto.getLanguage(),dto.getName(),dto.getDescription(),modified, modified));

        product.setModified(modified);

        return productRepository.save(product).getProductInfoMap().get(dto.getLanguage()).getId();
    }


    @Override
    @Transactional
    public Long addPriceInfoByProductId(String id, PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException,IllegalIdException {

        if (id==null){
            log.error("UUID is null.");
            throw new IllegalIdException("UUID is null.");
        }

        UUID uuid = UUID.fromString(id);

        if (dto==null){
            log.error("PriceDto is null.");
            throw new IllegalProductPriceException("PriceDto is null.");
        }

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        try{
            dto.validate();
        }catch(IllegalProductPriceException ex){
            throw new IllegalProductPriceException("Invalid PriceDto." ,ex);
        }

        Product product = productRepository.findById(uuid).get();

        if (product.getPriceInfoMap().containsKey(dto.getCurrency())){
            log.error("PriceInfo Already exists.");
            throw new IllegalProductPriceException("PriceInfo Already exists");
        }

        LocalDateTime modified = LocalDateTime.now();
        product.getPriceInfoMap().put(dto.getCurrency(),
                new PriceInfo(dto.getCurrency(),dto.getPrice(),modified, modified));

        product.setModified(modified);

        return productRepository.save(product).getPriceInfoMap().get(dto.getCurrency()).getId();
    }

    @Override
    @Transactional
    public Long editProductInfoByProductId(String id, InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException, IllegalIdException {
        if (id==null){
            log.error("UUID is null.");
            throw new IllegalIdException("UUID is null.");
        }

        UUID uuid = UUID.fromString(id);

        if (dto==null){
            log.error("InfoDto is null.");
            throw new IllegalProductInfoException("InfoDto is null.");
        }

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        try{
            dto.validate();
        }catch(IllegalProductInfoException ex){
            throw new IllegalProductInfoException("Invalid InfoDto." ,ex);
        }

        Product product = productRepository.findById(uuid).get();

        if (!product.getProductInfoMap().containsKey(dto.getLanguage())){
            log.error("ProductInfo not exists.");
            throw new IllegalProductInfoException("ProductInfo not exists");
        }

        LocalDateTime modified = LocalDateTime.now();

        ProductInfo info = infoRepository.findById(product.getProductInfoMap().get(dto.getLanguage()).getId()).get();

        info.setName(dto.getName());
        info.setDescription(dto.getDescription());
        info.setModified(modified);
        product.setModified(modified);

        productRepository.save(product);

        return infoRepository.save(info).getId();
    }

    @Override
    @Transactional
    public Long editPriceInfoByProductId(String id, PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException, IllegalIdException {
        if (id==null){
            log.error("UUID is null.");
            throw new IllegalIdException("UUID is null.");
        }

        UUID uuid = UUID.fromString(id);

        if (dto==null){
            log.error("PriceDto is null.");
            throw new IllegalProductPriceException("PriceDto is null.");
        }

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        try{
            dto.validate();
        }catch(IllegalProductPriceException ex){
            throw new IllegalProductPriceException("Invalid PriceDto." ,ex);
        }

        Product product = productRepository.findById(uuid).get();

        if (!product.getPriceInfoMap().containsKey(dto.getCurrency())){
            log.error("PriceInfo not exists.");
            throw new IllegalProductPriceException("PriceInfo not exists");
        }

        LocalDateTime modified = LocalDateTime.now();

        PriceInfo price = priceRepository.findById(product.getPriceInfoMap().get(dto.getCurrency()).getId()).get();

        price.setPrice(dto.getPrice());
        price.setModified(modified);
        product.setModified(modified);

        productRepository.save(product);

        return priceRepository.save(price).getId();
    }

    @Override
    public List<Product> findAll() throws ProductNotFoundException {
        List<Product> result =(List<Product>) productRepository.findAll();
        if (result.isEmpty()){
            log.error("Product base is empty");
            throw new ProductNotFoundException("Product base is empty");
        }
        return result;
    }



//
//
//
//    public Product findByLanguageAndCurrencyAndId (String lang, String curr, String id) throws NoSuchElementException{
//        if (lang==null||curr==null||id==null) throw new IllegalArgumentException("Illegal argument");
//
//        UUID uuid = UUID.fromString(id);
//        Language  language = Language.valueOf(lang.toUpperCase());
//        Currency currency = Currency.valueOf(curr.toUpperCase());
//
//        if (!productRepository.existsById(uuid)) throw new NoSuchElementException("Product doesn't exist");
//
//        Product product = productRepository.findById(uuid).get();
//
//        if (!product.getProductInfoMap().containsKey(language)) throw new NoSuchElementException("No product info. Language: "+language);
//        if (!product.getPriceInfoMap().containsKey(currency)) throw new NoSuchElementException("No product price. Currency: "+currency);
//
//        return product;
//    }
//
//    public List<Product> searchByLanguageAndCurrency (String lang, String curr, String text) throws NoSuchElementException{
//        if (lang==null||curr==null||text==null||text.trim()=="") throw new IllegalArgumentException("Illegal argument");
//
//        Language  language = Language.valueOf(lang.toUpperCase());
//        Currency currency = Currency.valueOf(curr.toUpperCase());
//
//        List<Product> result = productRepository.searchByLanguageAndCurrency(language.name(),currency.name(),text);
//
//        return result;
//    }
//
//    public List<Product> findAllByLanguageAndCurrency(String lang, String curr){
//        Language language = Language.valueOf(lang);
//        Currency currency = Currency.valueOf(curr);
//        return  productRepository.findAllByLanguageAndCurrency(lang,curr);
//    }
//
//
//
//    public List<Product> findAllByLang(String lang){
//        Language language = Language.valueOf(lang);
//        return  productRepository.findAllByLanguage(lang);
//    }
//
//    public List<Product> findAllByCurrency(String curr){
//        Currency currency = Currency.valueOf(curr);
//        return  productRepository.findAllByCurrency(curr);
//    }


}
