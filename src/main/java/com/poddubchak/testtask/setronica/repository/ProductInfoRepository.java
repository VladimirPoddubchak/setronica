package com.poddubchak.testtask.setronica.repository;

import com.poddubchak.testtask.setronica.model.ProductInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends PagingAndSortingRepository<ProductInfo,Long> {

}
