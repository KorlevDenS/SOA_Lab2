package org.korolev.dens.productservice.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {

    @NotNull(message = "Поле x не может быть null")
    private Long x;

    @NotNull(message = "Поле y не может быть null")
    private Long y;

    @NotNull(message = "Поле name не может быть null")
    @Size(max = 444, message = "Длина строки name не должна быть больше 444")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(x, location.x) && Objects.equals(y, location.y) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}