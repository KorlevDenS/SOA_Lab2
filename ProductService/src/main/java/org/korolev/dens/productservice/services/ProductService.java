package org.korolev.dens.productservice.services;

import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.entities.UnitOfMeasure;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.korolev.dens.productservice.repositories.ProductRepository;
import org.korolev.dens.productservice.validation.CoordinatesConverter;
import org.korolev.dens.productservice.validation.PersonConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CoordinatesConverter coordinatesConverter;
    private final PersonConverter personConverter;

    public ProductService(ProductRepository productRepository, CoordinatesConverter coordinatesConverter,
                          PersonConverter personConverter) {
        this.productRepository = productRepository;
        this.coordinatesConverter = coordinatesConverter;
        this.personConverter = personConverter;
    }

    public Integer countByGreaterThanUnitOfMeasure(String unit) throws InvalidParamsException {
        UnitOfMeasure unitOfMeasure;
        try {
            unitOfMeasure = UnitOfMeasure.valueOf(unit);
        } catch (IllegalArgumentException ex) {
            throw new InvalidParamsException(
                    "Неверно указана единица измерения: допустимые значения METERS, CENTIMETERS, MILLILITERS, GRAMS.");
        }
        return productRepository.countAllByUnitOfMeasureGreaterThan(unitOfMeasure);
    }

    public Integer countByOwner(String ownerId) {
        return productRepository.countAllByOwner_PassportID(ownerId);
    }

    public void delete(Integer id) throws InvalidParamsException, ProductNotFoundException {
        findById(id);
        productRepository.deleteById(id);
    }

    public Product findById(Integer id) throws InvalidParamsException, ProductNotFoundException {
        if (id <= 0) {
            throw new InvalidParamsException("ID должен быть положительным числом.");
        }
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return foundProduct.get();
        } else {
            throw new ProductNotFoundException("Продукт с ID = " + id + " не найден.");
        }
    }

    public Product update(Integer id, Product updatedProduct) throws InvalidParamsException,
            ViolationOfUniqueFieldException, ProductNotFoundException {
        Product productToUpdate = findById(id);
        if (!productToUpdate.getPartNumber().equals(updatedProduct.getPartNumber())) {
            checkQuantityWithPartNumber(updatedProduct.getPartNumber());
        }
        productToUpdate.setName(updatedProduct.getName());
        productToUpdate.setCoordinates(updatedProduct.getCoordinates());
        productToUpdate.setPrice(updatedProduct.getPrice());
        productToUpdate.setPartNumber(updatedProduct.getPartNumber());
        productToUpdate.setManufactureCost(updatedProduct.getManufactureCost());
        productToUpdate.setUnitOfMeasure(updatedProduct.getUnitOfMeasure());
        productToUpdate.setOwner(updatedProduct.getOwner());
        return productRepository.save(productToUpdate);
    }

    private void checkQuantityWithPartNumber(String partNumber) throws ViolationOfUniqueFieldException {
        if (productRepository.countAllByPartNumber(partNumber) != 0) {
            throw new ViolationOfUniqueFieldException(
                    "Продукт с номером детали " +  partNumber + " уже существует.");
        }
    }

    public Product save(Product product) throws ViolationOfUniqueFieldException {
        checkQuantityWithPartNumber(product.getPartNumber());
        return productRepository.save(product);
    }

    public List<Product> findAllByPartNumberHasSubstring(String substring) throws InvalidParamsException,
            ProductNotFoundException {
        if (substring == null || substring.isEmpty()) {
            throw new InvalidParamsException("Неверно указана подстрока номера детали: строка не может быть пустой.");
        }
        Specification<Product> spec = Specification.where(ProductSpecification.partNumberIncludesSubstring(substring));
        List<Product> products = productRepository.findAll(spec);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Продукты с указанной подстрокой не найдены.");
        }
        return products;
    }

    public Specification<Product> buildFilterSpecification(Integer id, String name, String coordinates,
                                                           LocalDate creationDate, Double price, String partNumber,
                                                           Integer manufactureCost, UnitOfMeasure unitOfMeasure,
                                                           String owner) throws InvalidParamsException {
        return Specification.where(ProductSpecification.hasId(id))
                .and(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasCoordinates(coordinatesConverter.convertWithCheck(coordinates)))
                .and(ProductSpecification.hasCreationDate(creationDate))
                .and(ProductSpecification.hasPrice(price))
                .and(ProductSpecification.hasPartNumber(partNumber))
                .and(ProductSpecification.hasManufactureCost(manufactureCost))
                .and(ProductSpecification.hasUnitOfMeasure(unitOfMeasure))
                .and(ProductSpecification.hasOwner(personConverter.convertWithCheck(owner)));
    }

    public Specification<Product> addSortCriteria(Specification<Product> spec, String sortField)
            throws InvalidParamsException {
        if (sortField == null) {
            return spec;
        }
        return switch (sortField) {
            case "id" -> spec.and(ProductSpecification.sortById());
            case "name" -> spec.and(ProductSpecification.sortByName());
            case "coordinates" -> spec.and(ProductSpecification.sortByCoordinates());
            case "creationDate" -> spec.and(ProductSpecification.sortByCreationDate());
            case "price" -> spec.and(ProductSpecification.sortByPrice());
            case "partNumber" -> spec.and(ProductSpecification.sortByPartNumber());
            case "manufactureCost" -> spec.and(ProductSpecification.sortByManufactureCost());
            case "unitOfMeasure" -> spec.and(ProductSpecification.sortByUnitOfMeasure());
            case "owner" -> spec.and(ProductSpecification.sortByOwner());
            default -> throw new InvalidParamsException("Указан несуществующий параметр сортировки.");
        };
    }

    public List<Product> findSpecifiedPage(Specification<Product> spec, Integer pageNumber, Integer pageSize)
            throws InvalidParamsException, ProductNotFoundException {
        if (pageNumber == null || pageSize == null) {
            return productRepository.findAll(spec);
        }
        if (pageNumber <= 0 || pageSize <= 0) {
            throw new InvalidParamsException("Номер и размер страницы должен быть положительным.");
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Product> products = productRepository.findAll(spec, pageable);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Указанная страница не найдена");
        }
        return products.getContent();
    }

}
