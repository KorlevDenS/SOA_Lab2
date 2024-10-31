package org.korolev.dens.productservice.validation;

import org.korolev.dens.productservice.exceptions.InvalidParamsException;

public interface ConverterWithCheck <S, T> {
    T convertWithCheck(S source) throws InvalidParamsException;
}
