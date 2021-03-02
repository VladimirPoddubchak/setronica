package com.poddubchak.testtask.setronica.service.admin;

import com.poddubchak.testtask.setronica.dto.InfoDto;
import com.poddubchak.testtask.setronica.dto.PriceDto;
import com.poddubchak.testtask.setronica.dto.ProductDto;
import com.poddubchak.testtask.setronica.exception.*;
import com.poddubchak.testtask.setronica.model.Product;

import java.util.List;
import java.util.UUID;

public interface AdminProductInterface {
    UUID createProduct(ProductDto dto) throws IllegalProductException;
    Boolean deleteProductById(String id) throws ProductNotFoundException, IllegalIdException;
    Product findProductById(String id) throws ProductNotFoundException;
    Long addProductInfoByProductId(String id, InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException;
    Long addPriceInfoByProductId(String id, PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException;
    Long editProductInfoByProductId(String id, InfoDto dto) throws ProductNotFoundException, IllegalProductInfoException;
    Long editPriceInfoByProductId(String id, PriceDto dto) throws ProductNotFoundException, IllegalProductPriceException;

    List<Product> findAll() throws ProductNotFoundException;
}
