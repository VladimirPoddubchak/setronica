package com.poddubchak.testtask.setronica.repository;

import com.poddubchak.testtask.setronica.model.PriceInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends PagingAndSortingRepository<PriceInfo,Long> {
}
