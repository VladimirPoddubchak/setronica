package com.poddubchak.testtask.setronica.dto;

import com.poddubchak.testtask.setronica.model.Currency;
import com.poddubchak.testtask.setronica.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientProductDto implements Serializable{
    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Language language;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal price;


}
