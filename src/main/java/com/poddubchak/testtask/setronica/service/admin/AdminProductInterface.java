package com.poddubchak.testtask.setronica.service.admin;

import com.poddubchak.testtask.setronica.dto.InfoDto;
import com.poddubchak.testtask.setronica.dto.PriceDto;
import com.poddubchak.testtask.setronica.dto.ProductDto;
import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.model.Product;

import java.util.UUID;

public interface AdminProductInterface {
    UUID addProduct(ProductDto dto) throws IllegalProductException;
    Boolean deleteProductById(UUID id) throws ProductNotFoundException, IllegalIdException;
    Product findById(UUID id) throws ProductNotFoundException;
    Long addProductInfoByProductId(UUID id, InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException;
    Long addPriceInfoByProductId(UUID id, PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException;
    Long editProductInfoByProductId(UUID id, InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException;
    Long editPriceInfoByProductId(UUID id, PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException;
}
