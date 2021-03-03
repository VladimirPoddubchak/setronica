package com.poddubchak.testtask.setronica.model;

import com.poddubchak.testtask.setronica.exception.NoSuchCurrencyException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Slf4j
public enum Language {
    RUS,ENG,BEL,ZHO,KOR,SPA,ITA,UKR
}
