package org.korolev.dens.productservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Coordinates implements Comparable<Coordinates> {

    @Column(name = "coordinates_x")
    private int x;

    @Min(value = -842, message = "Значение поля y должно быть больше -841")
    @Column(name = "coordinates_y")
    private long y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Coordinates other) {
        return Long.compare(this.x + this.y, other.x + other.y);
    }
}
