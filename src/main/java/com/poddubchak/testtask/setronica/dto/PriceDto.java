package com.poddubchak.testtask.setronica.dto;


import com.poddubchak.testtask.setronica.model.Currency;
import com.poddubchak.testtask.setronica.model.Language;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriceDto implements Serializable {
    private Currency currency;
    private BigDecimal price;
}
