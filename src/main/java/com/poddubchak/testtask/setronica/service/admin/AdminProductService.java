package com.poddubchak.testtask.setronica.service.admin;

import com.poddubchak.testtask.setronica.dto.PriceDto;
import com.poddubchak.testtask.setronica.dto.ProductDto;
import com.poddubchak.testtask.setronica.dto.InfoDto;
import com.poddubchak.testtask.setronica.model.Currency;
import com.poddubchak.testtask.setronica.model.*;
import com.poddubchak.testtask.setronica.repository.ProductInfoRepository;
import com.poddubchak.testtask.setronica.repository.ProductPriceRepository;
import com.poddubchak.testtask.setronica.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AdminProductService {

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

    @Transactional
    public UUID createProduct(ProductDto dto){

        Map<Language, ProductInfo> productInfoMap = new HashMap<>();
        Map<Currency, PriceInfo> priceInfoMap = new HashMap<>();

        productInfoMap.put(dto.getLanguage(),new ProductInfo(dto.getLanguage(), dto.getName(), dto.getDescription(),LocalDateTime.now(), LocalDateTime.now()));
        priceInfoMap.put(dto.getCurrency(),new PriceInfo(dto.getCurrency(), dto.getPrice(),LocalDateTime.now(), LocalDateTime.now()));

        Product product = new Product(LocalDateTime.now(), LocalDateTime.now(),productInfoMap,priceInfoMap);

        return productRepository.save(product).getId();
    }



    public Product findById (String id) throws NoSuchElementException{
        UUID uuid = UUID.fromString(id);
        return productRepository.findById(uuid).orElseThrow(NoSuchElementException::new);
    }

    public Product findByLanguageAndCurrencyAndId (String lang, String curr, String id) throws NoSuchElementException{
        if (lang==null||curr==null||id==null) throw new IllegalArgumentException("Illegal argument");

        UUID uuid = UUID.fromString(id);
        Language  language = Language.valueOf(lang.toUpperCase());
        Currency currency = Currency.valueOf(curr.toUpperCase());

        if (!productRepository.existsById(uuid)) throw new NoSuchElementException("Product doesn't exist");

        Product product = productRepository.findById(uuid).get();

        if (!product.getProductInfoMap().containsKey(language)) throw new NoSuchElementException("No product info. Language: "+language);
        if (!product.getPriceInfoMap().containsKey(currency)) throw new NoSuchElementException("No product price. Currency: "+currency);

        return product;
    }

    public List<Product> searchByLanguageAndCurrency (String lang, String curr, String text) throws NoSuchElementException{
        if (lang==null||curr==null||text==null||text.trim()=="") throw new IllegalArgumentException("Illegal argument");

        Language  language = Language.valueOf(lang.toUpperCase());
        Currency currency = Currency.valueOf(curr.toUpperCase());

        List<Product> result = productRepository.searchByLanguageAndCurrency(language.name(),currency.name(),text);

        return result;
    }



    public List<Product> findAllByLanguageAndCurrency(String lang, String curr){
        Language language = Language.valueOf(lang);
        Currency currency = Currency.valueOf(curr);
        return  productRepository.findAllByLanguageAndCurrency(lang,curr);
    }

    @Transactional
    public Boolean deleteProduct(String id) throws NoSuchElementException{
        UUID uuid = UUID.fromString(id);

        if (!productRepository.existsById(uuid)) throw new NoSuchElementException();

        productRepository.deleteById(uuid);
        return true;
    }

    @Transactional
    public ProductInfo addInfoByProductID(String id, InfoDto dto)throws IllegalArgumentException,NoSuchElementException{

        UUID uuid = UUID.fromString(id);

        if (!productRepository.existsById(uuid)) throw new NoSuchElementException();

        Product product = productRepository.findById(uuid).get();

        if (product.getProductInfoMap().containsKey(dto.getLanguage())) throw new IllegalArgumentException("Already exists");

        LocalDateTime modified = LocalDateTime.now();
        product.getProductInfoMap().put(dto.getLanguage(),
                new ProductInfo(dto.getLanguage(),dto.getName(),dto.getDescription(),modified, modified));

        product.setModified(modified);

        return productRepository.save(product).getProductInfoMap().get(dto.getLanguage());
    }


    @Transactional
    public ProductInfo editInfoByProductID(String id, InfoDto dto)throws IllegalArgumentException,NoSuchElementException{

        UUID uuid = UUID.fromString(id);

        if (!productRepository.existsById(uuid)) throw new NoSuchElementException();

        Product product = productRepository.findById(uuid).get();

        if (!product.getProductInfoMap().containsKey(dto.getLanguage())) throw new NoSuchElementException("Not exists");

        ProductInfo info = infoRepository.findById(product.getProductInfoMap().get(dto.getLanguage()).getId()).get();

        LocalDateTime modified = LocalDateTime.now();

        info.setName(dto.getName());
        info.setDescription(dto.getDescription());
        info.setModified(modified);
        product.setModified(modified);
        productRepository.save(product);

        return infoRepository.save(info);
    }


    @Transactional
    public PriceInfo addPriceByProductID(String id, PriceDto dto)throws IllegalArgumentException,NoSuchElementException{

        UUID uuid = UUID.fromString(id);

        if (!productRepository.existsById(uuid)) throw new NoSuchElementException();

        Product product = productRepository.findById(uuid).get();

        if (product.getPriceInfoMap().containsKey(dto.getCurrency())) throw new IllegalArgumentException("Already exists");

        LocalDateTime modified = LocalDateTime.now();
        product.getPriceInfoMap().put(dto.getCurrency(),
                new PriceInfo(dto.getCurrency(),dto.getPrice(),modified, modified));

        product.setModified(modified);

        return productRepository.save(product).getPriceInfoMap().get(dto.getCurrency());
    }


    @Transactional
    public PriceInfo editPriceByProductID(String id, PriceDto dto)throws IllegalArgumentException,NoSuchElementException{

        UUID uuid = UUID.fromString(id);

        if (!productRepository.existsById(uuid)) throw new NoSuchElementException();

        Product product = productRepository.findById(uuid).get();

        if (!product.getPriceInfoMap().containsKey(dto.getCurrency())) throw new NoSuchElementException("Not exists");

        PriceInfo info = priceRepository.findById(product.getProductInfoMap().get(dto.getCurrency()).getId()).get();

        LocalDateTime modified = LocalDateTime.now();

        info.setPrice(dto.getPrice());
        info.setModified(modified);
        product.setModified(modified);
        productRepository.save(product);

        return priceRepository.save(info);
    }

    public List<Product> findAll () throws NoSuchElementException {
        return (List<Product>) productRepository.findAll();
    }

    public List<Product> findAllByLang(String lang){
        Language language = Language.valueOf(lang);
        return  productRepository.findAllByLanguage(lang);
    }

    public List<Product> findAllByCurrency(String curr){
        Currency currency = Currency.valueOf(curr);
        return  productRepository.findAllByCurrency(curr);
    }
}
