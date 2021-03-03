package com.poddubchak.testtask.setronica.service.client;

import com.poddubchak.testtask.setronica.dto.ClientProductDto;
import com.poddubchak.testtask.setronica.exception.*;

import java.util.List;

public interface ProductClientServiceInterface {

    ClientProductDto findByIdByLanguageAndCurrency(String id, String lang, String curr)
            throws ProductNotFoundException, NotFoundByCurrencyException, NoSuchLanguageException, NoSuchCurrencyException;

    List<ClientProductDto> searchByLanguageAndCurrency(String lang, String curr, String text)
            throws NotFoundByCurrencyException, NoSuchLanguageException, NoSuchCurrencyException, IllegalSearchParameterException;

    List<ClientProductDto> findAllByLanguageAndCurrency(String lang, String curr)
            throws NotFoundByCurrencyException, NoSuchLanguageException, NoSuchCurrencyException;


}
