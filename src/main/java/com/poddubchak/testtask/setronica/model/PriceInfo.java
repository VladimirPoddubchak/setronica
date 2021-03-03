package com.poddubchak.testtask.setronica.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "PRICE_INFO")
public class PriceInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    public PriceInfo(CurrencyEnum currency, BigDecimal price, LocalDateTime created, LocalDateTime modified) {
        this.currency = currency;
        this.price = price;
        this.created = created;
        this.modified = modified;
    }
}
