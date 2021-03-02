package com.poddubchak.testtask.setronica.service.client;

import com.poddubchak.testtask.setronica.dto.ClientProductDto;
import com.poddubchak.testtask.setronica.exception.*;

import java.util.List;

public interface ProductClientServiceInterface {
    List<ClientProductDto> searchByLanguageAndCurrency(String lang, String curr, String text)
            throws NotFoundByLanguageOrCurrencyException, NoSuchLanguageException, NoSuchCurrencyException, IllegalSearchParameterException;

    List<ClientProductDto> findAllByLanguageAndCurrency(String lang, String curr)
            throws NotFoundByLanguageOrCurrencyException, NoSuchLanguageException, NoSuchCurrencyException;

    ClientProductDto findByIdByLanguageAndCurrency(String id, String lang, String curr)
            throws ProductNotFoundException, NotFoundByLanguageOrCurrencyException, NoSuchLanguageException, NoSuchCurrencyException;
}
