package com.poddubchak.testtask.setronica.repository;

import com.poddubchak.testtask.setronica.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {

//       "SELECT product.id,language,name,description, PRODUCT.created,PRODUCT.modified " +
//            "FROM PRODUCT_INFO " +
//            "JOIN product_product_info " +
//                "ON product_info.id = product_product_info.product_info_id " +
//                "AND product_info.language = :lang " +
//            "JOIN product " +
//            "ON product.id = product_product_info.product_id;

    @Query(value = "SELECT p.id,p.created,p.modified " +
                    "FROM product AS p, product_info AS pin, product_product_info AS ppi " +
                    "WHERE pin.language =:lang " +
                        "AND ppi.product_info_id = pin.id " +
                        "AND p.id = ppi.product_id;"
            , nativeQuery = true)
    List<Product> findAllByLanguage(@Param("lang") String lang);


//    "SELECT product.id,currency,price,PRODUCT.created,PRODUCT.modified " +
//        "FROM PRICE_INFO " +
//        "JOIN product_price_info " +
//            "ON price_info.id = product_price_info.price_info_id " +
//            "AND currency = :curr " +
//        "JOIN product " +
//            "ON product.id = product_price_info.product_id;

    @Query(value = "SELECT p.id,p.created,p.modified " +
                    "FROM product AS p, price_info AS pin, product_price_info AS ppi " +
                    "WHERE pin.currency =:curr " +
                        "AND ppi.price_info_id = pin.id " +
                        "AND p.id = ppi.product_id;"
            , nativeQuery = true)
    List<Product> findAllByCurrency(@Param("curr") String curr);


//        "WITH " +
//          "lang as( " +
//              "SELECT DISTINCT product_product_info.product_id AS id " +
//              "FROM PRODUCT_INFO " +
//                  "JOIN product_product_info " +
//                      "ON product_info.id = product_product_info.product_info_id " +
//                      "AND product_info.language = :lang), " +
//          "curr as( " +
//              "SELECT DISTINCT product_price_info.product_id AS id " +
//              "FROM PRICE_INFO " +
//                  "JOIN product_price_info " +
//                      "ON price_info.id = product_price_info.price_info_id " +
//                      "AND price_info.currency=:curr) " +
//        "SELECT product.id,product.created,product.modified " +
//        "FROM product,lang,curr " +
//        "WHERE product.id = lang.id AND product.id = curr.id;

    @Query(value = "SELECT p.id,p.created,p.modified " +
                    "FROM product AS p, product_info, product_product_info,price_info, product_price_info " +
                    "WHERE product_info.language =:lang " +
                        "AND price_info.currency =:curr " +
                        "AND product_product_info.product_info_id = product_info.id " +
                        "AND product_price_info.price_info_id = price_info.id " +
                        "AND p.id = product_product_info.product_id " +
                        "AND p.id = product_price_info.product_id;"
            , nativeQuery = true)
    List<Product> findAllByLanguageAndCurrency(@Param("lang") String lang,@Param("curr") String curr);


//        "WITH " +
//          "lang as( " +
//              "SELECT DISTINCT product_product_info.product_id AS id " +
//              "FROM PRODUCT_INFO " +
//                  "JOIN product_product_info " +
//                      "ON product_info.id = product_product_info.product_info_id " +
//                      "AND product_info.language = :lang " +
//                      "AND (product_info.name LIKE '%'||:text||'%' " +
//                          "OR product_info.description LIKE '%'||:text||'%')), " +
//          "curr as( " +
//              "SELECT DISTINCT product_price_info.product_id AS id " +
//              "FROM PRICE_INFO " +
//                  "JOIN product_price_info " +
//                      "ON price_info.id = product_price_info.price_info_id " +
//                      "AND price_info.currency=:curr) " +
//        "SELECT product.id,product.created,product.modified " +
//        "FROM product,lang,curr " +
//        "WHERE product.id = lang.id AND product.id = curr.id;

    @Query(value = "SELECT p.id,p.created,p.modified " +
                    "FROM product AS p, product_info, product_product_info,price_info, product_price_info " +
                    "WHERE product_info.language =:lang " +
                        "AND price_info.currency =:curr " +
                        "AND (product_info.name LIKE '%'||:text||'%' " +
                            "OR product_info.description LIKE '%'||:text||'%') " +
                        "AND product_product_info.product_info_id = product_info.id " +
                        "AND product_price_info.price_info_id = price_info.id " +
                        "AND p.id = product_product_info.product_id " +
                        "AND p.id = product_price_info.product_id;"
            , nativeQuery = true)
    List<Product> searchByLanguageAndCurrency(@Param("lang") String lang,@Param("curr") String curr,@Param("text") String text);

//    List<Product> findAllByProductInfoMapLanguage(Language lang);
//    List<Product> findAllByPriceInfoMapCurrency(Currency curr);

    @Query(value = "SELECT p.id, p.created, p.modified " +
                    "FROM product AS p, product_info, product_product_info,price_info, product_price_info " +
                    "WHERE p.id = :uuid " +
                        "AND product_info.language =:lang " +
                        "AND price_info.currency =:curr " +
                        "AND product_product_info.product_info_id = product_info.id " +
                        "AND product_price_info.price_info_id = price_info.id " +
                        "AND p.id = product_product_info.product_id " +
                        "AND p.id = product_price_info.product_id;"
            , nativeQuery = true)
    Product findByIdByLanguageAndCurrency(@Param("uuid") UUID uuid, @Param("lang") String lang, @Param("curr") String curr);
}
