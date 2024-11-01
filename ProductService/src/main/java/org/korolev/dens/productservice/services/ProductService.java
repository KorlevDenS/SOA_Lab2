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
import java.util.Optional;

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

    public List<Product> findAndFilterAll(Integer id, String name, String coordinates, LocalDate creationDate,
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
    public List<Product> sortByField(List<Product> products, String sortField) throws InvalidParamsException {
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

    public List<Product> findPage(List<Product> products, Integer pageNumber, Integer pageSize)
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
