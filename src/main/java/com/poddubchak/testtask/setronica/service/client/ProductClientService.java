package com.poddubchak.testtask.setronica.service.client;


import com.poddubchak.testtask.setronica.dto.ClientProductDto;
import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.model.CurrencyEnum;
import com.poddubchak.testtask.setronica.model.LanguageEnum;
import com.poddubchak.testtask.setronica.model.Product;
import com.poddubchak.testtask.setronica.repository.ClientProductDtoRepository;
import com.poddubchak.testtask.setronica.repository.ProductRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.poddubchak.testtask.setronica.utils.SetronicaUtils.*;


@Service
@Slf4j
public class ProductClientService implements ProductClientServiceInterface{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientProductDtoRepository clientProductRepository;

    public ProductClientService(ProductRepository productRepository, ClientProductDtoRepository clientProductRepository) {
        this.productRepository = productRepository;
        this.clientProductRepository = clientProductRepository;
        log.info("ProductClientService started");
    }


    @Override
    public ClientProductDto findByIdByLanguageAndCurrency(@NonNull String id, @NonNull String lang, @NonNull String curr )
            throws ProductNotFoundException, NotFoundByCurrencyException, NoSuchLanguageException, NoSuchCurrencyException,IllegalIdException{

        log.info("findByIdByLanguageAndCurrency(id:"+id+"lang:"+lang+", curr:"+curr);

        UUID uuid = validUUID(id);
        LanguageEnum language = validLanguage(lang);
        CurrencyEnum currency = validCurrency(curr);

        if (!productRepository.existsById(uuid)){
            log.error("No product with uuid:"+uuid);
            throw new ProductNotFoundException("No product with uuid:"+uuid);
        }
        Product product = productRepository.findById(uuid).get();

        if (!product.getProductInfoMap().containsKey(language)){
            log.error("Product hasn't info by language. uuid:"+uuid+" lang:"+lang);
            throw new NotFoundByLanguageException("Product uuid:"+uuid+" hasn't info by language:"+lang);
        }
        if (!product.getPriceInfoMap().containsKey(currency)){
            log.error("Product hasn't price by currency. uuid:"+uuid+" curr:"+curr);
            throw new NotFoundByCurrencyException("Product uuid:"+uuid+" hasn't price by currency:"+curr);
        }

        ClientProductDto result = new ClientProductDto(
                product.getId(),
                language,
                product.getProductInfoMap().get(language).getName(),
                product.getProductInfoMap().get(language).getDescription(),
                currency,
                product.getPriceInfoMap().get(currency).getPrice(),
                product.getCreated(),
                product.getModified()
        );
        return result;
    }

    @Override
    public List<ClientProductDto> searchByLanguageAndCurrencyPageable(@NonNull String lang, @NonNull String curr, @NonNull String text, @NonNull int page,@NonNull int size)
            throws NoSuchLanguageException, NoSuchCurrencyException, IllegalPageableException{
        log.info("searchByLanguageAndCurrency(lang:"+lang+", curr:"+curr+" text:"+text);

        validLanguage(lang);
        validCurrency(curr);
        validatePageSize(page,size);

        int offset = page*size;

        return clientProductRepository.searchByLanguageAndCurrencyPageable(lang, curr, text, offset, size);
    }

    @Override
    public List<ClientProductDto> findAllByLanguageAndCurrencyPageable(@NonNull String lang, @NonNull String curr, @NonNull int page,@NonNull int size)
            throws NoSuchLanguageException, NoSuchCurrencyException,IllegalPageableException{
        log.info("findAllByLanguageAndCurrency(lang:"+lang+", curr:"+ curr+", page:"+page+", size:"+size);

        validLanguage(lang);
        validCurrency(curr);
        validatePageSize(page,size);

        int offset = page*size;
        log.info("findAllByLanguageAndCurrency(lang:"+lang+", curr:"+ curr+", offset:"+offset+", size:"+size);

        return clientProductRepository.findAllByLanguageAndCurrencyPageable(lang, curr, offset, size);
    }

}
