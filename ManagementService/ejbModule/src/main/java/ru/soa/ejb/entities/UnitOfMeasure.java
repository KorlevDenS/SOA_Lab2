package ru.soa.ejb.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UnitOfMeasure {
    METERS,
    CENTIMETERS,
    MILLILITERS,
    GRAMS;

        @JsonValue
    public String toLowerCase() {
        return name().toLowerCase().replace("_", " ");
    }

    @JsonCreator
    public static UnitOfMeasure fromString(String value) {
        String normalizedValue = value.toUpperCase().replace(" ", "_");
        for (UnitOfMeasure type : UnitOfMeasure.values()) {
            if (type.name().equals(normalizedValue)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown UnitOfMeasure: " + value);
    }
}
