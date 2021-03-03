package com.poddubchak.testtask.setronica.dto;

import com.poddubchak.testtask.setronica.exception.IllegalProductException;
import com.poddubchak.testtask.setronica.model.Currency;
import com.poddubchak.testtask.setronica.model.Language;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class ProductDto implements Serializable,Validatable {
    private UUID id;
    private Language language;
    private String name;
    private String description;
    private Currency currency;
    private BigDecimal price;

    @Override
    public void validate() throws IllegalProductException {
        if (this.language==null||this.name==null||this.description==null||this.currency==null||this.price==null){
            log.error("ProductDto or some field is null.",this);
            throw new IllegalProductException("ProductDto or some field is null.");
        }
        if (this.name.isBlank()) {
            log.error("Product name is blank.",this);
            throw new IllegalProductException("Product name is blank.");
        }
        if (this.price.compareTo(BigDecimal.ZERO)<=0) {
            log.error("Product price <= 0.",this);
            throw new IllegalProductException("Product price <= 0.");
        }
    }
}
