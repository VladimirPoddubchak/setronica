package com.poddubchak.testtask.setronica.service.admin;

import com.poddubchak.testtask.setronica.dto.InfoDto;
import com.poddubchak.testtask.setronica.dto.PriceDto;
import com.poddubchak.testtask.setronica.dto.ProductDto;
import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.model.*;
import com.poddubchak.testtask.setronica.repository.ProductInfoRepository;
import com.poddubchak.testtask.setronica.repository.ProductPriceRepository;
import com.poddubchak.testtask.setronica.repository.ProductRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.poddubchak.testtask.setronica.utils.SetronicaUtils.validUUID;

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
//        addProducts();
    }

    @Override
    @Transactional
    public UUID createProduct(@NonNull ProductDto dto) throws IllegalProductException {

        dto.validate();

        Map<LanguageEnum, ProductInfo> productInfoMap = new HashMap<>();
        Map<CurrencyEnum, PriceInfo> priceInfoMap = new HashMap<>();

        LocalDateTime created = LocalDateTime.now();

        productInfoMap.put(dto.getLanguage(),new ProductInfo(dto.getLanguage(), dto.getName(), dto.getDescription(), created, created));
        priceInfoMap.put(dto.getCurrency(),new PriceInfo(dto.getCurrency(), dto.getPrice(), created, created));

        Product product = productRepository.save(new Product(created, created, productInfoMap, priceInfoMap));

        return product.getId();
    }


    @Override
    @Transactional
    public Boolean deleteProductById(@NonNull String  id) throws ProductNotFoundException,IllegalIdException {

        UUID uuid = validUUID(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        productRepository.deleteById(uuid);
        return true;
    }

    @Override
    public Product findProductById(@NonNull String id) throws ProductNotFoundException,IllegalIdException {

        UUID uuid = validUUID(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        Product result = productRepository.findById(uuid).get();

        return result;
    }

    @Override
    @Transactional
    public Long addProductInfoByProductId(@NonNull String id, @NonNull InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException,IllegalIdException {

        UUID uuid = validUUID(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        dto.validate();
        LanguageEnum language = dto.getLanguage();

        Product product = productRepository.findById(uuid).get();

        if (product.getProductInfoMap().containsKey(language)){
            log.error("Product uuid:"+uuid+", already has ProductInfo by language:"+language);
            throw new IllegalProductInfoException("Product uuid:"+uuid+", already has ProductInfo by language:"+language);
        }

        LocalDateTime modified = LocalDateTime.now();

        product.getProductInfoMap().put(language, new ProductInfo(language,dto.getName(),dto.getDescription(),modified, modified));

        product.setModified(modified);

        return productRepository.save(product).getProductInfoMap().get(language).getId();
    }


    @Override
    @Transactional
    public Long addPriceInfoByProductId(@NonNull String id, @NonNull PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException, IllegalIdException {

        UUID uuid = validUUID(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        dto.validate();
        CurrencyEnum currency = dto.getCurrency();

        Product product = productRepository.findById(uuid).get();

        if (product.getPriceInfoMap().containsKey(currency)){
            log.error("Product uuid:"+uuid+", already has PriceInfo by currency:"+currency);
            throw new IllegalProductPriceException("Product uuid:"+uuid+", already has PriceInfo by currency:"+currency);
        }

        LocalDateTime modified = LocalDateTime.now();

        product.getPriceInfoMap().put(currency,new PriceInfo(currency,dto.getPrice(),modified, modified));

        product.setModified(modified);

        return productRepository.save(product).getPriceInfoMap().get(currency).getId();
    }

    @Override
    @Transactional
    public Long editProductInfoByProductId(@NonNull String id,@NonNull  InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException, IllegalIdException {

        UUID uuid = validUUID(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        dto.validate();

        LanguageEnum language = dto.getLanguage();

        Product product = productRepository.findById(uuid).get();

        if (!product.getProductInfoMap().containsKey(language)){
            log.error("Product uuid:"+uuid+", hasn't ProductInfo by language:"+language);
            throw new IllegalProductInfoException("Product uuid:"+uuid+", hasn't ProductInfo by language:"+language);
        }

        LocalDateTime modified = LocalDateTime.now();

        ProductInfo info = infoRepository.findById(product.getProductInfoMap().get(language).getId()).get();

        info.setName(dto.getName());
        info.setDescription(dto.getDescription());
        info.setModified(modified);
        product.setModified(modified);

        productRepository.save(product);

        return infoRepository.save(info).getId();
    }

    @Override
    @Transactional
    public Long editPriceInfoByProductId(@NonNull String id, @NonNull PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException, IllegalIdException {

        UUID uuid = validUUID(id);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid: "+uuid);
            throw new ProductNotFoundException("No product with uuid: "+uuid);
        }

        dto.validate();

        CurrencyEnum currency = dto.getCurrency();

        Product product = productRepository.findById(uuid).get();

        if (!product.getPriceInfoMap().containsKey(currency)){
            log.error("Product uuid:"+uuid+", hasn't PriceInfo by currency:"+currency);
            throw new IllegalProductPriceException("Product uuid:"+uuid+", hasn't PriceInfo by currency:"+currency);
        }

        LocalDateTime modified = LocalDateTime.now();

        PriceInfo price = priceRepository.findById(product.getPriceInfoMap().get(currency).getId()).get();

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

    private void addProducts(){

        Map<LanguageEnum, ProductInfo> productInfoMap = new HashMap<>();
        Map<CurrencyEnum, PriceInfo> priceInfoMap = new HashMap<>();

        LocalDateTime created = LocalDateTime.now();

        productInfoMap.put(LanguageEnum.RU,new ProductInfo(LanguageEnum.RU, "Хлеб","Хлеб. Описание. Рус", created, created));
        productInfoMap.put(LanguageEnum.BE,new ProductInfo(LanguageEnum.BE, "Хлеб","Хлеб. Описание. Бел", created, created));
        productInfoMap.put(LanguageEnum.UK,new ProductInfo(LanguageEnum.UK, "Хлеб","Хлеб. Описание. Укр", created, created));
        productInfoMap.put(LanguageEnum.EN,new ProductInfo(LanguageEnum.EN, "Bread","Bread. Description. Eng", created, created));
        productInfoMap.put(LanguageEnum.IT,new ProductInfo(LanguageEnum.IT, "Bread","Bread. Description. Ita", created, created));
        productInfoMap.put(LanguageEnum.KO,new ProductInfo(LanguageEnum.KO, "Bread","Bread. Description. Kor", created, created));

        priceInfoMap.put(CurrencyEnum.RUB,new PriceInfo(CurrencyEnum.RUB, BigDecimal.valueOf(40), created, created));
        priceInfoMap.put(CurrencyEnum.UAH,new PriceInfo(CurrencyEnum.UAH, BigDecimal.valueOf(40), created, created));
        priceInfoMap.put(CurrencyEnum.USD,new PriceInfo(CurrencyEnum.USD, BigDecimal.valueOf(40), created, created));
        priceInfoMap.put(CurrencyEnum.CAD,new PriceInfo(CurrencyEnum.CAD, BigDecimal.valueOf(40), created, created));
        priceInfoMap.put(CurrencyEnum.GBP,new PriceInfo(CurrencyEnum.GBP, BigDecimal.valueOf(40), created, created));

        productRepository.save(new Product(created, created, productInfoMap, priceInfoMap));

        productInfoMap.clear();
        priceInfoMap.clear();

        productInfoMap.put(LanguageEnum.RU,new ProductInfo(LanguageEnum.RU, "Молоко","Молоко. Описание. Рус", created, created));
        productInfoMap.put(LanguageEnum.BE,new ProductInfo(LanguageEnum.BE, "Молоко","Молоко. Описание. Бел", created, created));
        productInfoMap.put(LanguageEnum.IT,new ProductInfo(LanguageEnum.IT, "Milk","Milk. Description. Ita", created, created));
        productInfoMap.put(LanguageEnum.KO,new ProductInfo(LanguageEnum.KO, "Milk","Milk. Description. Kor", created, created));

        priceInfoMap.put(CurrencyEnum.RUB,new PriceInfo(CurrencyEnum.RUB, BigDecimal.valueOf(50), created, created));
        priceInfoMap.put(CurrencyEnum.UAH,new PriceInfo(CurrencyEnum.UAH, BigDecimal.valueOf(50), created, created));
        priceInfoMap.put(CurrencyEnum.USD,new PriceInfo(CurrencyEnum.USD, BigDecimal.valueOf(50), created, created));

        productRepository.save(new Product(created, created, productInfoMap, priceInfoMap));


        productInfoMap.clear();
        priceInfoMap.clear();

        productInfoMap.put(LanguageEnum.RU,new ProductInfo(LanguageEnum.RU, "Мясо","Мясо. Описание. Рус", created, created));
        productInfoMap.put(LanguageEnum.EN,new ProductInfo(LanguageEnum.EN, "Meat","Meat. Description. Eng", created, created));

        priceInfoMap.put(CurrencyEnum.RUB,new PriceInfo(CurrencyEnum.RUB, BigDecimal.valueOf(100), created, created));
        priceInfoMap.put(CurrencyEnum.GBP,new PriceInfo(CurrencyEnum.GBP, BigDecimal.valueOf(100), created, created));

        productRepository.save(new Product(created, created, productInfoMap, priceInfoMap));


        productInfoMap.clear();
        priceInfoMap.clear();

        productInfoMap.put(LanguageEnum.IT,new ProductInfo(LanguageEnum.IT, "Cheese","Cheese. Description. Ita", created, created));

        priceInfoMap.put(CurrencyEnum.EUR,new PriceInfo(CurrencyEnum.GBP, BigDecimal.valueOf(200), created, created));

        productRepository.save(new Product(created, created, productInfoMap, priceInfoMap));
    }


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
