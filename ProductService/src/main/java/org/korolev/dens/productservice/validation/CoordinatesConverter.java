package org.korolev.dens.productservice.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.korolev.dens.productservice.entities.Coordinates;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CoordinatesConverter implements Converter<String, Coordinates>, ConverterWithCheck<String, Coordinates> {

    private final ObjectMapper objectMapper;

    public CoordinatesConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Coordinates convertWithCheck(String source) throws InvalidParamsException {
        if (source == null || source.isEmpty()) {
            return null;
        }
        Coordinates coordinates = convert(source);
        if (coordinates == null) {
            throw new InvalidParamsException("Некорректное значение фильтра coordinates");
        } else {
            return coordinates;
        }
    }

    @Override
    public Coordinates convert(@Nonnull String source) {
        try {
            return objectMapper.readValue(source, Coordinates.class);
        } catch (IOException e) {
            return null;
        }
    }
}
