package org.korolev.dens.productservice.controllers;

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
        List<Product> filteredProducts = productService.findAndFilterAll(id, name, coordinates, creationDate, price,
                partNumber, manufactureCost, unitOfMeasure, owner);
        List<Product> sortedProducts = productService.sortByField(filteredProducts, sort);
        return ResponseEntity.ok(productService.findPage(sortedProducts, page, size));
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
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws InvalidParamsException,
            ProductNotFoundException {
        productService.delete(id);
        return ResponseEntity.ok("Продукт с id = " + id + " успешно удалён.");
    }

}
