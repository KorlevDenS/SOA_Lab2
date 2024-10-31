package org.korolev.dens.productservice.configuration;

import org.korolev.dens.productservice.entities.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.Comparator;

@Configuration
public class ComparatorConfig {

    @Bean
    public Comparator<Product> idProductComparator() {
        return Comparator.comparingInt(Product::getId);
    }

    @Bean
    public Comparator<Product> nameProductComparator() {
        return Comparator.comparing(Product::getName);
    }

    @Bean
    public Comparator<Product> coordinatesProductComparator() {
        return Comparator.comparing(Product::getCoordinates);
    }

    @Bean
    public Comparator<Product> creationDateProductComparator() {
        return Comparator.comparing(Product::getCreationDate);
    }

    @Bean
    public Comparator<Product> priceProductComparator() {
        return (Product p1, Product p2) -> {
            if (p1.getPrice() == null && p2.getPrice() == null) {
                return 0;
            }
            if (p1.getPrice() == null) {
                return Double.compare(0, p2.getPrice());
            }
            if (p2.getPrice() == null) {
                return Double.compare(p1.getPrice(), 0);
            }
            return p1.getPrice().compareTo(p2.getPrice());
        };
    }

    @Bean
    public Comparator<Product> partNumberProductComparator() {
        return Comparator.comparing(Product::getPartNumber);
    }

    @Bean
    public Comparator<Product> manufactureCostProductComparator() {
        return Comparator.comparing(Product::getManufactureCost);
    }

    @Bean
    public Comparator<Product> unitOfMeasureProductComparator() {
        return (Product p1, Product p2) -> {
            if (p1.getUnitOfMeasure() == null && p2.getUnitOfMeasure() == null) {
                return 0;
            }
            if (p1.getUnitOfMeasure() == null) {
                return Integer.compare(-1, p2.getUnitOfMeasure().ordinal());
            }
            if (p2.getUnitOfMeasure() == null) {
                return Integer.compare(p1.getUnitOfMeasure().ordinal(), -1);
            }
            return Integer.compare(p1.getUnitOfMeasure().ordinal(), p2.getUnitOfMeasure().ordinal());
        };
    }

    @Bean
    public Comparator<Product> ownerProductComparator() {
        return (Product p1, Product p2) -> {
            if (p1.getOwner() == null && p2.getOwner() == null) {
                return 0;
            }
            if (p1.getOwner() == null) {
                return ZonedDateTime.now().compareTo(p2.getOwner().getBirthday());
            }
            if (p2.getOwner() == null) {
                return p1.getOwner().getBirthday().compareTo(ZonedDateTime.now());
            }
            return p1.getOwner().compareTo(p2.getOwner());
        };
    }

}
