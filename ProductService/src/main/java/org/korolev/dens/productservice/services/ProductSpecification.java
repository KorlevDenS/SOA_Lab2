package org.korolev.dens.productservice.services;

import jakarta.persistence.criteria.Expression;
import org.korolev.dens.productservice.entities.Coordinates;
import org.korolev.dens.productservice.entities.Person;
import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.entities.UnitOfMeasure;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProductSpecification {

    public static Specification<Product> hasId(Integer id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Product> sortById() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Product> sortByName() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("name")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> hasCoordinates(Coordinates coordinates) {
        return (root, query, criteriaBuilder) -> coordinates == null ? null : criteriaBuilder.and(
                criteriaBuilder.equal(root.get("coordinates").get("x"), coordinates.getX()),
                criteriaBuilder.equal(root.get("coordinates").get("y"), coordinates.getY())
        );
    }

    public static Specification<Product> sortByCoordinates() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) {
                Expression<Long> sumExpression = criteriaBuilder.sum(
                        root.get("coordinates").get("x"), root.get("coordinates").get("y")
                );
                query.orderBy(criteriaBuilder.asc(sumExpression));
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> hasCreationDate(LocalDate creationDate) {
        return (root, query, criteriaBuilder) ->
                creationDate == null ? null : criteriaBuilder.equal(root.get("creationDate"), creationDate);
    }

    public static Specification<Product> sortByCreationDate() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("creationDate")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> hasPrice(Double price) {
        return (root, query, criteriaBuilder) -> price == null ? null : criteriaBuilder.equal(root.get("price"), price);
    }

    public static Specification<Product> sortByPrice() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("price")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> hasPartNumber(String partNumber) {
        return (root, query, criteriaBuilder) ->
                partNumber == null ? null : criteriaBuilder.equal(root.get("partNumber"), partNumber);
    }

    public static Specification<Product> sortByPartNumber() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("partNumber")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> partNumberIncludesSubstring(String substring) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("partNumber"), "%" + substring + "%");
    }

    public static Specification<Product> hasManufactureCost(Integer manufactureCost) {
        return (root, query, criteriaBuilder) ->
                manufactureCost == null ? null : criteriaBuilder.equal(root.get("manufactureCost"), manufactureCost);
    }

    public static Specification<Product> sortByManufactureCost() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("manufactureCost")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> hasUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        return (root, query, criteriaBuilder) ->
                unitOfMeasure == null ? null : criteriaBuilder.equal(root.get("unitOfMeasure"), unitOfMeasure);
    }

    public static Specification<Product> sortByUnitOfMeasure() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("unitOfMeasure")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Product> hasOwner(Person owner) {
        return (root, query, criteriaBuilder) -> owner == null ? null : criteriaBuilder.and(
                criteriaBuilder.equal(root.get("owner").get("name"), owner.getName()),
                criteriaBuilder.equal(root.get("owner").get("birthday"), owner.getBirthday()),
                criteriaBuilder.equal(root.get("owner").get("height"), owner.getHeight()),
                criteriaBuilder.equal(root.get("owner").get("passportID"), owner.getPassportID()),
                owner.getLocation() == null ? null : criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("owner").get("location").get("x"), owner.getLocation().getX()),
                        criteriaBuilder.equal(root.get("owner").get("location").get("y"), owner.getLocation().getY()),
                        criteriaBuilder.equal(root.get("owner").get("location").get("name"), owner.getLocation().getName())
                )
        );
    }

    public static Specification<Product> sortByOwner() {
        return (root, query, criteriaBuilder) -> {
            if (query != null) query.orderBy(criteriaBuilder.asc(root.get("owner").get("birthday")));
            return criteriaBuilder.conjunction();
        };
    }

}
