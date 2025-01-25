package ru.soa.ejb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Comparable<Person>, Serializable {

    private String name;

    @JsonProperty("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime birthday;

    private Float height;

    private String passportID;

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
