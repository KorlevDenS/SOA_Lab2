package org.korolev.dens.productservice.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Поле name не может быть null")
    @NotEmpty(message = "Строка name не может быть пустой")
    private String name;

    @NotNull(message = "Поле coordinates не может быть null")
    @Valid
    @Embedded
    private Coordinates coordinates;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDate creationDate;

    @Positive(message = "Значение поля price должно быть больше 0")
    private Double price;

    @Column(name = "part_number")
    @NotNull(message = "Поле partNumber не может быть null")
    private String partNumber;

    @Column(name = "manufacture_cost")
    @NotNull(message = "Поле manufactureCost не может быть null")
    private Integer manufactureCost;

    @Column(name = "unit_of_measure")
    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @Valid
    @Embedded
    private Person owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", manufactureCost=" + manufactureCost +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }
}
