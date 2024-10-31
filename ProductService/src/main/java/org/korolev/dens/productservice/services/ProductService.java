package org.korolev.dens.productservice.services;

import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.entities.UnitOfMeasure;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.korolev.dens.productservice.repositories.ProductRepository;
import org.korolev.dens.productservice.validation.CoordinatesConverter;
import org.korolev.dens.productservice.validation.PersonConverter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final WebApplicationContext webApplicationContext;
    private final CoordinatesConverter coordinatesConverter;
    private final PersonConverter personConverter;

    public ProductService(ProductRepository productRepository, WebApplicationContext webApplicationContext,
                          CoordinatesConverter coordinatesConverter, PersonConverter personConverter) {
        this.productRepository = productRepository;
        this.webApplicationContext = webApplicationContext;
        this.coordinatesConverter = coordinatesConverter;
        this.personConverter = personConverter;
    }

    public Product saveProduct(Product product) throws ViolationOfUniqueFieldException {
        if (productRepository.countAllByPartNumber(product.getPartNumber()) != 0) {
            throw new ViolationOfUniqueFieldException(
                    "Продукт с номером детали " +  product.getPartNumber() + " уже существует.");
        }
        return productRepository.save(product);
    }

    public List<Product> filterProducts(Integer id, String name, String coordinates, LocalDate creationDate,
                                        Double price, String partNumber, Integer manufactureCost,
                                        UnitOfMeasure unitOfMeasure, String owner)
            throws ProductNotFoundException, InvalidParamsException {
        Specification<Product> spec = Specification.where(ProductSpecification.hasId(id))
                .and(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasCoordinates(coordinatesConverter.convertWithCheck(coordinates)))
                .and(ProductSpecification.hasCreationDate(creationDate))
                .and(ProductSpecification.hasPrice(price))
                .and(ProductSpecification.hasPartNumber(partNumber))
                .and(ProductSpecification.hasManufactureCost(manufactureCost))
                .and(ProductSpecification.hasUnitOfMeasure(unitOfMeasure))
                .and(ProductSpecification.hasOwner(personConverter.convertWithCheck(owner)));
        List<Product> products = productRepository.findAll(spec);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Продукты не найдены по указанным параметрам фильтрации.");
        }
        return products;
    }

    @SuppressWarnings(value = "unchecked")
    public List<Product> sortProduct(List<Product> products, String sortField) throws InvalidParamsException {
        if (sortField == null) {
            return products;
        }
        String comparatorBeanName = sortField + "ProductComparator";
        if (webApplicationContext.containsBean(comparatorBeanName)) {
            Comparator<Product> comparator = webApplicationContext.getBean(comparatorBeanName, Comparator.class);
            products.sort(comparator);
            return products;
        } else {
            throw new InvalidParamsException("Указан несуществующий параметр сортировки.");
        }

    }

    public List<Product> findPageOfProducts(List<Product> products, Integer pageNumber, Integer pageSize)
            throws InvalidParamsException, ProductNotFoundException {
        if (pageNumber == null || pageSize == null) {
            return products;
        }
        if (pageNumber <= 0 || pageSize <= 0) {
            throw new InvalidParamsException("Номер и размер страницы должен быть положительным.");
        }
        if (pageNumber * pageSize - pageSize > products.size() - 1) {
            throw new ProductNotFoundException("Указанная страница не найдена");
        }
        List<Product> page = products.subList(pageNumber * pageSize - pageSize, products.size());
        return page.stream().limit(pageSize).toList();
    }

}
