package org.korolev.dens.productservice.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.korolev.dens.productservice.entities.Person;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PersonConverter implements Converter<String, Person>, ConverterWithCheck<String, Person> {

    private final ObjectMapper objectMapper;

    public PersonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Person convertWithCheck(String source) throws InvalidParamsException {
        if (source == null || source.isEmpty()) {
            return null;
        }
        Person person = convert(source);
        if (person == null) {
            throw new InvalidParamsException("Некорректное значение фильтра person");
        } else {
            return person;
        }
    }

    @Override
    public Person convert(@Nonnull String source) {
        try {
            return objectMapper.readValue(source, Person.class);
        } catch (IOException e) {
            return null;
        }
    }

}
