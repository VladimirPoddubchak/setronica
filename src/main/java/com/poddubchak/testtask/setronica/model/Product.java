package com.poddubchak.testtask.setronica.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "modified")
    private LocalDateTime modified;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_PRODUCT_INFO",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "product_info_id", referencedColumnName = "id")})
    @MapKey(name = "language")
    private Map<LanguageEnum,ProductInfo> productInfoMap;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_PRICE_INFO",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "price_info_id", referencedColumnName = "id")})
    @MapKey(name = "currency")
    private Map<CurrencyEnum,PriceInfo> priceInfoMap;

    public Product(LocalDateTime created, LocalDateTime modified, Map<LanguageEnum, ProductInfo> productInfoMap, Map<CurrencyEnum, PriceInfo> priceInfoMap) {
        this.created = created;
        this.modified = modified;
        this.productInfoMap = productInfoMap;
        this.priceInfoMap = priceInfoMap;
    }

}
