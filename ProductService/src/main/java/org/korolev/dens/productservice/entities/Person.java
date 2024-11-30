package org.korolev.dens.productservice.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.korolev.dens.productservice.validation.ZonedDateTimeDeserializer;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Person implements Comparable<Person> {

    @NotNull(message = "Поле name не может быть null")
    @NotEmpty(message = "Строка name не может быть пустой")
    @Column(name = "person_name")
    private String name;

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @NotNull(message = "Поле birthday не может быть null")
    @Column(name = "person_birthday")
    private ZonedDateTime birthday;

    @Positive(message = "Значение поля height должно быть больше 0")
    @Column(name = "person_height")
    private Float height;

    @NotEmpty(message = "Строка passportID не может быть пустой")
    @Column(name = "person_passport_id")
    private String passportID;

    @Valid
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "person_location_x")),
            @AttributeOverride(name = "y", column = @Column(name = "person_location_y")),
            @AttributeOverride(name = "name", column = @Column(name = "person_location_name"))
    })
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(birthday, person.birthday)
                && Objects.equals(height, person.height) && Objects.equals(passportID, person.passportID)
                && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, height, passportID, location);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", passportID='" + passportID + '\'' +
                ", location=" + location +
                '}';
    }

    @Override
    public int compareTo(Person other) {
        return this.birthday.compareTo(other.birthday);
    }
}
