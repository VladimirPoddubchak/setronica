package com.poddubchak.testtask.setronica.repository;

import com.poddubchak.testtask.setronica.dto.ClientProductDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientProductDtoRepository extends PagingAndSortingRepository<ClientProductDto, UUID> {


    @Query(value = "SELECT p.id,product_info.language,product_info.name,product_info.description, price_info.currency, price_info.price, p.created, p.modified " +
            "FROM product AS p, product_info, product_product_info,price_info, product_price_info " +
            "WHERE p.id = :uuid " +
            "AND product_info.language =:lang " +
            "AND price_info.currency =:curr " +
            "AND product_product_info.product_info_id = product_info.id " +
            "AND product_price_info.price_info_id = price_info.id " +
            "AND p.id = product_product_info.product_id " +
            "AND p.id = product_price_info.product_id;", nativeQuery = true)
    ClientProductDto findByIdByLanguageAndCurrency(@Param("uuid") UUID uuid, @Param("lang") String lang, @Param("curr") String curr);

    @Query(value = "WITH " +
                    "lang as(" +
                        "SELECT product_product_info.product_id AS id,language,name,description " +
                        "FROM PRODUCT_INFO " +
                        "JOIN product_product_info " +
                            "ON product_info.id = product_product_info.product_info_id " +
                            "AND product_info.language = :lang " +
                            "AND (product_info.name LIKE %:text% " +
                                "OR product_info.description LIKE %:text%)), " +
                    "curr as( " +
                        "SELECT DISTINCT product_price_info.product_id AS id,currency,price " +
                        "FROM PRICE_INFO " +
                        "JOIN product_price_info " +
                            "ON price_info.id = product_price_info.price_info_id " +
                            "AND price_info.currency=:curr) " +
                    "SELECT product.id,lang.language,lang.name,lang.description,curr.currency,curr.price,product.created,product.modified " +
                    "FROM product,lang,curr " +
                    "WHERE product.id = lang.id AND product.id = curr.id;", nativeQuery = true)
    List<ClientProductDto> searchByLanguageAndCurrency(@Param("lang") String lang, @Param("curr") String curr, @Param("text") String text);



    @Query(value = "SELECT p.id,product_info.language,product_info.name,product_info.description, price_info.currency,price_info.price, p.created, p.modified " +
            "FROM product AS p, product_info, product_product_info,price_info, product_price_info " +
            "WHERE product_info.language =:lang " +
            "AND price_info.currency =:curr " +
            "AND product_product_info.product_info_id = product_info.id " +
            "AND product_price_info.price_info_id = price_info.id " +
            "AND p.id = product_product_info.product_id " +
            "AND p.id = product_price_info.product_id;"
            , nativeQuery = true)
    List<ClientProductDto> findAllByLanguageAndCurrency(@Param("lang") String lang, @Param("curr") String curr);





}






