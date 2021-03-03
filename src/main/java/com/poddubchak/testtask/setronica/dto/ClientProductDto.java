package com.poddubchak.testtask.setronica.dto;

import com.poddubchak.testtask.setronica.model.CurrencyEnum;
import com.poddubchak.testtask.setronica.model.LanguageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientProductDto implements Serializable{
    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private LanguageEnum language;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;
    private BigDecimal price;
    private LocalDateTime created;
    private LocalDateTime modified;
}
