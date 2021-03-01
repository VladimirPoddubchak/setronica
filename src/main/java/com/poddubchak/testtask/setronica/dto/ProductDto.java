package com.poddubchak.testtask.setronica.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.poddubchak.testtask.setronica.model.Currency;
import com.poddubchak.testtask.setronica.model.Language;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto implements Serializable {
    private UUID id;
    private Language language;
    private String name;
    private String description;
    private Currency currency;
    private BigDecimal price;
}
