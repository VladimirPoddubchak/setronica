package com.poddubchak.testtask.setronica.dto;


import com.poddubchak.testtask.setronica.exception.IllegalProductException;
import com.poddubchak.testtask.setronica.exception.IllegalProductPriceException;
import com.poddubchak.testtask.setronica.model.Currency;
import com.poddubchak.testtask.setronica.model.Language;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class PriceDto implements Serializable, Validatable{
    private Currency currency;
    private BigDecimal price;

    @Override
    public void validate() throws IllegalProductPriceException {
        if (this.currency==null||this.price==null){
            log.error("PriceDto field is null.",this);
            throw new IllegalProductPriceException("PriceDto field is null.");
        }

        if (this.price.compareTo(BigDecimal.ZERO)<=0) {
            log.error("Product price <= 0.",this);
            throw new IllegalProductPriceException("Product price <= 0.");
        }
    }
}
