package org.korolev.dens.productservice.services;

import org.korolev.dens.productservice.entities.Coordinates;
import org.korolev.dens.productservice.entities.Location;
import org.korolev.dens.productservice.entities.Person;
import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.jaxb.CoordinatesGetResponse;
import org.korolev.dens.productservice.jaxb.LocationGetResponse;
import org.korolev.dens.productservice.jaxb.PersonGetResponse;
import org.korolev.dens.productservice.jaxb.ProductGetResponse;
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
        response.setOwner(toDto(product.getOwner()));
        return response;
    }

    public PersonGetResponse toDto(Person person) {
        PersonGetResponse response = new PersonGetResponse();
        response.setName(person.getName());
        response.setBirthday(toXmlGregorianCalendar(person.getBirthday()));
        response.setHeight(person.getHeight());
        response.setPassportID(person.getPassportID());
        response.setLocation(toDto(person.getLocation()));
        return response;
    }

    public CoordinatesGetResponse toDto(Coordinates coordinates) {
        CoordinatesGetResponse response = new CoordinatesGetResponse();
        response.setX(coordinates.getX());
        response.setY(coordinates.getY());
        return response;
    }

    public LocationGetResponse toDto(Location location) {
        LocationGetResponse response = new LocationGetResponse();
        response.setX(location.getX());
        response.setY(location.getY());
        response.setName(location.getName());
        return response;
    }



    public LocalDate toLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
