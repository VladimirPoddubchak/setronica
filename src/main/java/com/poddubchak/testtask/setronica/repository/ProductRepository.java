package com.poddubchak.testtask.setronica.repository;

import com.poddubchak.testtask.setronica.model.Currency;
import com.poddubchak.testtask.setronica.model.Language;
import com.poddubchak.testtask.setronica.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {

    @Query(value = "SELECT p.id,p.created,p.modified " +
            "FROM product AS p, product_info AS pin, product_product_info AS ppi " +
            "WHERE pin.language =:lang " +
                "AND ppi.product_info_id = pin.id " +
                "AND p.id = ppi.product_id;", nativeQuery = true)
    List<Product> findAllByLanguage(@Param("lang") String lang);

    @Query(value = "SELECT p.id,p.created,p.modified " +
            "FROM product AS p, price_info AS pin, product_price_info AS ppi " +
            "WHERE pin.currency =:curr " +
                "AND ppi.price_info_id = pin.id " +
                "AND p.id = ppi.product_id;", nativeQuery = true)
    List<Product> findAllByCurrency(@Param("curr") String curr);

    @Query(value = "SELECT p.id,p.created,p.modified " +
            "FROM product AS p, product_info, product_product_info,price_info, product_price_info " +
            "WHERE product_info.language =:lang " +
                "AND price_info.currency =:curr " +
                "AND product_product_info.product_info_id = product_info.id " +
                "AND product_price_info.price_info_id = price_info.id " +
                "AND p.id = product_product_info.product_id " +
                "AND p.id = product_price_info.product_id;", nativeQuery = true)
    List<Product> findAllByLanguageAndCurrency(@Param("lang") String lang,@Param("curr") String curr);

    @Query(value = "SELECT p.id,p.created,p.modified " +
            "FROM product AS p, product_info, product_product_info,price_info, product_price_info " +
            "WHERE product_info.language =:lang " +
                "AND price_info.currency =:curr " +
                "AND (product_info.name LIKE '%'||:text||'%' " +
                    "OR product_info.description LIKE '%'||:text||'%') " +
                "AND product_product_info.product_info_id = product_info.id " +
                "AND product_price_info.price_info_id = price_info.id " +
                "AND p.id = product_product_info.product_id " +
                "AND p.id = product_price_info.product_id;", nativeQuery = true)
    List<Product> searchByLanguageAndCurrency(@Param("lang") String lang,@Param("curr") String curr,@Param("text") String text);

//    List<Product> findAllByProductInfoMapLanguage(Language lang);
//    List<Product> findAllByPriceInfoMapCurrency(Currency curr);
}
