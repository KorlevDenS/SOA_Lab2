package org.korolev.dens.productservice.controllers;

import jakarta.validation.constraints.Positive;
import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.entities.UnitOfMeasure;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.korolev.dens.productservice.services.ProductService;
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
            @RequestParam(required = false) String coordinates,
            @RequestParam(required = false) LocalDate creationDate, @RequestParam(required = false) Double price,
            @RequestParam(required = false) String partNumber, @RequestParam(required = false) Integer manufactureCost,
            @RequestParam(required = false) UnitOfMeasure unitOfMeasure, @RequestParam(required = false) String owner,
            @RequestParam(required = false) String sort, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) throws ProductNotFoundException, InvalidParamsException {
        List<Product> filteredProducts = productService.filterProducts(id, name, coordinates, creationDate, price,
                partNumber, manufactureCost, unitOfMeasure, owner);
        List<Product> sortedProducts = productService.sortProduct(filteredProducts, sort);
        return ResponseEntity.ok(productService.findPageOfProducts(sortedProducts, page, size));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Validated Product product)
            throws ViolationOfUniqueFieldException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable @Positive(message = "ID должен быть положительным числом.") Integer id) {
        return null;
    }

}
