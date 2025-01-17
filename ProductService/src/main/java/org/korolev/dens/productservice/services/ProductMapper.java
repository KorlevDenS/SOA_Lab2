package org.korolev.dens.productservice.services;

import org.korolev.dens.productservice.entities.*;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.jaxb.*;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class ProductMapper {

    public ProductGetResponse toDto(Product product) {
        ProductGetResponse response = new ProductGetResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCoordinates(toDto(product.getCoordinates()));
        response.setCreationDate(toXmlGregorianCalendar(product.getCreationDate()));
        response.setPrice(product.getPrice());
        response.setPartNumber(product.getPartNumber());
        response.setManufactureCost(product.getManufactureCost());
        response.setUnitOfMeasure(product.getUnitOfMeasure().toString());
        if (product.getOwner() != null) {
            response.setOwner(toDto(product.getOwner()));
        }
        return response;
    }

    public Product toEntity(UpdateProductRequest request) throws InvalidParamsException {
        Product product = new Product();
        product.setName(request.getName());
        product.setCoordinates(toEntity(request.getCoordinates()));
        product.setCreationDate(LocalDate.now());
        product.setPrice(request.getPrice());
        product.setPartNumber(request.getPartNumber());
        product.setManufactureCost(request.getManufactureCost());
        try {
            product.setUnitOfMeasure(UnitOfMeasure.valueOf(request.getUnitOfMeasure()));
        } catch (IllegalArgumentException e) {
            throw new InvalidParamsException("Поле unitOfMeasure должно иметь одним из значений:" +
                    " METERS, CENTIMETERS, MILLILITERS, GRAMS");
        }
        if (request.getOwner() != null) {
            product.setOwner(toEntity(request.getOwner()));
        }
        return product;
    }

    public Product toEntity(AddProductRequest request) throws InvalidParamsException {
        Product product = new Product();
        product.setName(request.getName());
        product.setCoordinates(toEntity(request.getCoordinates()));
        product.setCreationDate(LocalDate.now());
        product.setPrice(request.getPrice());
        product.setPartNumber(request.getPartNumber());
        product.setManufactureCost(request.getManufactureCost());
        try {
            product.setUnitOfMeasure(UnitOfMeasure.valueOf(request.getUnitOfMeasure()));
        } catch (IllegalArgumentException e) {
            throw new InvalidParamsException("Поле unitOfMeasure должно иметь одним из значений:" +
                    " METERS, CENTIMETERS, MILLILITERS, GRAMS");
        }
        if (request.getOwner() != null) {
            product.setOwner(toEntity(request.getOwner()));
        }
        return product;
    }

    public PersonGetResponse toDto(Person person) {
        PersonGetResponse response = new PersonGetResponse();
        response.setName(person.getName());
        response.setBirthday(toXmlGregorianCalendar(person.getBirthday()));
        response.setHeight(person.getHeight());
        response.setPassportID(person.getPassportID());
        if (person.getLocation() != null) {
            response.setLocation(toDto(person.getLocation()));
        }
        return response;
    }

    public Person toEntity(PersonGetResponse response) {
        Person person = new Person();
        person.setName(response.getName());
        person.setBirthday(toZonedDateTime(response.getBirthday()));
        person.setHeight(response.getHeight());
        person.setPassportID(response.getPassportID());
        if (response.getLocation() != null) {
            person.setLocation(toEntity(response.getLocation()));
        }
        return person;
    }

    public CoordinatesGetResponse toDto(Coordinates coordinates) {
        CoordinatesGetResponse response = new CoordinatesGetResponse();
        response.setX(coordinates.getX());
        response.setY(coordinates.getY());
        return response;
    }

    public Coordinates toEntity(CoordinatesGetResponse response) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(response.getX());
        coordinates.setY(response.getY());
        return coordinates;
    }

    public LocationGetResponse toDto(Location location) {
        LocationGetResponse response = new LocationGetResponse();
        response.setX(location.getX());
        response.setY(location.getY());
        response.setName(location.getName());
        return response;
    }

    public Location toEntity(LocationGetResponse response) {
        Location location = new Location();
        location.setX(response.getX());
        location.setY(response.getY());
        location.setName(response.getName());
        return location;
    }

    public LocalDate toLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public ZonedDateTime toZonedDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
        return date.toInstant().atZone(ZoneId.systemDefault());
    }

    public XMLGregorianCalendar toXmlGregorianCalendar(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zonedDateTime);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public XMLGregorianCalendar toXmlGregorianCalendar(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
