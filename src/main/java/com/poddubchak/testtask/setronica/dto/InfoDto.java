package com.poddubchak.testtask.setronica.dto;


import com.poddubchak.testtask.setronica.exception.IllegalProductInfoException;
import com.poddubchak.testtask.setronica.model.LanguageEnum;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class InfoDto implements Serializable, Validatable {
    private LanguageEnum language;
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
