package com.poddubchak.testtask.setronica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientError implements Serializable {
    private int errorCode;
    private String errorMessage;
}
