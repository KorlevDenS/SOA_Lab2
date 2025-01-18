package org.korolev.dens.productservice.controllers;

import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.entities.UnitOfMeasure;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.RequestMessage;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.korolev.dens.productservice.services.ProductService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) Integer id, @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate creationDate, @RequestParam(required = false) Double price,
            @RequestParam(required = false) String partNumber, @RequestParam(required = false) Integer manufactureCost,
            @RequestParam(required = false) UnitOfMeasure unitOfMeasure,
            @RequestParam(required = false) String sort, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) throws ProductNotFoundException, InvalidParamsException {
        Specification<Product> spec = productService.buildFilterSpecification(id, name, null, creationDate,
                price, partNumber, manufactureCost, unitOfMeasure, null);
        Specification<Product> sortedSpec = productService.addSortCriteria(spec, sort);
        return ResponseEntity.ok(productService.findSpecifiedPage(sortedSpec, page, size));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Validated Product product)
            throws ViolationOfUniqueFieldException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) throws InvalidParamsException,
            ProductNotFoundException {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody @Validated Product product)
            throws ViolationOfUniqueFieldException, InvalidParamsException, ProductNotFoundException {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RequestMessage> deleteProduct(@PathVariable Integer id) throws InvalidParamsException,
            ProductNotFoundException {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new RequestMessage(200, "Продукт с id = " + id + " успешно удалён."));
    }

    @GetMapping("/owner/{ownerId}/count")
    public ResponseEntity<Integer> getCountByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(productService.countByOwner(ownerId));
    }

    @GetMapping("/unitOfMeasure/{unit}/count")
    public ResponseEntity<Integer> getCountByGreaterThanUnitOfMeasure(@PathVariable String unit)
            throws InvalidParamsException {
        return ResponseEntity.ok(productService.countByGreaterThanUnitOfMeasure(unit));
    }

    @GetMapping("/partNumber/search")
    public ResponseEntity<List<Product>> getAllByPartNumberHasSubstring(@RequestParam String substring)
            throws InvalidParamsException, ProductNotFoundException {
        return ResponseEntity.ok(productService.findAllByPartNumberHasSubstring(substring));
    }

}
