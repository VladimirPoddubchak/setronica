package com.poddubchak.testtask.setronica.dto;


import com.poddubchak.testtask.setronica.model.Language;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InfoDto {
    private Language language;
    private String name;
    private String description;
}
