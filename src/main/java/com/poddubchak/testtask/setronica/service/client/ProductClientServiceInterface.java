package com.poddubchak.testtask.setronica.service.client;

import com.poddubchak.testtask.setronica.dto.ClientProductDto;
import com.poddubchak.testtask.setronica.exception.*;

import java.util.List;

public interface ProductClientServiceInterface {

    ClientProductDto findByIdByLanguageAndCurrency(String id, String lang, String curr)
            throws ProductNotFoundException, NotFoundByCurrencyException, NoSuchLanguageException, NoSuchCurrencyException;

    List<ClientProductDto> searchByLanguageAndCurrencyPageable(String lang, String curr, String text, int page,int size)
            throws NotFoundByCurrencyException, NoSuchLanguageException, NoSuchCurrencyException,
            IllegalSearchParameterException, IllegalPageableException;

    List<ClientProductDto> findAllByLanguageAndCurrencyPageable(String lang, String curr, int page,int size)
            throws NotFoundByCurrencyException, NoSuchLanguageException, NoSuchCurrencyException, IllegalPageableException;


}
