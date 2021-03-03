package com.poddubchak.testtask.setronica.utils;

import com.poddubchak.testtask.setronica.exception.IllegalIdException;
import com.poddubchak.testtask.setronica.exception.IllegalProductException;
import com.poddubchak.testtask.setronica.exception.NoSuchCurrencyException;
import com.poddubchak.testtask.setronica.exception.NoSuchLanguageException;
import com.poddubchak.testtask.setronica.model.*;
import com.poddubchak.testtask.setronica.repository.ProductRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class SetronicaUtils {

    public static UUID validUUID(@NonNull String id){
        try{
            UUID uuid = UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            log.error("Invalid uuid:"+id);
            throw  new IllegalIdException("Invalid uuid:"+id, ex);
        }
    return  UUID.fromString(id);
    }

    public static Currency validCurrency(@NonNull String curr){
        try{
            Currency currency = Currency.valueOf(curr.toUpperCase());
        }catch (IllegalArgumentException ex){
            log.error("Invalid currency. curr:"+curr);
            throw  new NoSuchCurrencyException("Invalid currency. curr:"+curr,ex);
        }
        return Currency.valueOf(curr.toUpperCase());
    }

    public static Language validLanguage(@NonNull String lang){
        try{
            Language language = Language.valueOf(lang.toUpperCase());
        }catch (IllegalArgumentException ex){
            log.error("Invalid language. lang:"+lang);
            throw  new NoSuchLanguageException("Invalid language. lang:"+lang,ex);
        }
        return Language.valueOf(lang.toUpperCase());
    }
}
