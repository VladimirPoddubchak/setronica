package com.poddubchak.testtask.setronica.utils;

import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.model.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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

    public static void validateUUID(@NonNull String id){
        try{
            UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            log.error("Invalid uuid:"+id);
            throw  new IllegalIdException("Invalid uuid:"+id, ex);
        }
    }

    public static CurrencyEnum validCurrency(@NonNull String curr){
        try{
            CurrencyEnum currency = CurrencyEnum.valueOf(curr.toUpperCase());
        }catch (IllegalArgumentException ex){
            log.error("Invalid currency:"+curr);
            throw  new NoSuchCurrencyException("Invalid currency:"+curr,ex);
        }
        return CurrencyEnum.valueOf(curr.toUpperCase());
    }

    public static void validateCurrency(@NonNull String curr){
        try{
            CurrencyEnum.valueOf(curr.toUpperCase());
        }catch (IllegalArgumentException ex){
            log.error("Invalid currency:"+curr);
            throw  new NoSuchCurrencyException("Invalid currency:"+curr,ex);
        }
    }

    public static LanguageEnum validLanguage(@NonNull String lang){
        try{
            LanguageEnum.valueOf(lang.toUpperCase());
        }catch (IllegalArgumentException ex){
            log.error("Invalid language. lang:"+lang);
            throw  new NoSuchLanguageException("Invalid language. lang:"+lang,ex);
        }
        return LanguageEnum.valueOf(lang.toUpperCase());
    }

    public static void validateLanguage(@NonNull String lang){
        try{
            LanguageEnum.valueOf(lang.toUpperCase());
        }catch (IllegalArgumentException ex){
            log.error("Invalid language:"+lang);
            throw  new NoSuchLanguageException("Invalid language:"+lang,ex);
        }
    }

    public static void validatePageSize(int page, int size){
        if (page<0) {
            log.error("Invalid page:"+page+" < 0");
            throw  new IllegalPageableException("Invalid page:"+page+" < 0");
        }
        if (size<=0) {
            log.error("Invalid size:"+ size+" <= 0");
            throw  new IllegalPageableException("Invalid size:"+ size+" <= 0");
        }
    };
}
