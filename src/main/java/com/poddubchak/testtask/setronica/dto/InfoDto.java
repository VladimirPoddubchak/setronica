package com.poddubchak.testtask.setronica.dto;


import com.poddubchak.testtask.setronica.exception.IllegalProductException;
import com.poddubchak.testtask.setronica.exception.IllegalProductInfoException;
import com.poddubchak.testtask.setronica.model.Language;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class InfoDto implements Serializable, Validatable {
    private Language language;
    private String name;
    private String description;

    @Override
    public void validate() throws IllegalProductInfoException {
        if (this.language==null||this.name==null||this.description==null){
            log.error("InfoDto field is null.",this);
            throw new IllegalProductInfoException("InfoDto field is null.");
        }
        if (this.name.isBlank()) {
            log.error("Product name is blank.",this);
            throw new IllegalProductInfoException("Product name is blank.");
        }
    }

}
