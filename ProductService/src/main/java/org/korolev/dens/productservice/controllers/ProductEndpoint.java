package org.korolev.dens.productservice.controllers;

import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.korolev.dens.productservice.services.ProductMapper;
import org.korolev.dens.productservice.services.ProductService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.korolev.dens.productservice.jaxb.*;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://org/korolev/dens/productservice/jaxb";
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductEndpoint(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) throws InvalidParamsException, ProductNotFoundException {
        String productId = request.getId();
        Product product = productService.findById(productId);
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productMapper.toDto(product));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addProductRequest")
    @ResponsePayload
    public AddProductResponse addProduct(@RequestPayload AddProductRequest request) throws InvalidParamsException,
            ViolationOfUniqueFieldException {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productService.save(product);
        AddProductResponse response = new AddProductResponse();
        response.setProduct(productMapper.toDto(savedProduct));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteProductByIdRequest")
    @ResponsePayload
    public DeleteProductByIdResponse deleteProduct(@RequestPayload DeleteProductByIdRequest request) throws
            InvalidParamsException, ProductNotFoundException {
        String id = request.getId();
        productService.delete(id);
        DeleteProductByIdResponse response = new DeleteProductByIdResponse();
        response.setCode(200);
        response.setMessage("Продукт с id = " + id + " успешно удалён.");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateProductRequest")
    @ResponsePayload
    public UpdateProductResponse updateProduct(@RequestPayload UpdateProductRequest request)
            throws InvalidParamsException, ViolationOfUniqueFieldException, ProductNotFoundException {
        String id = request.getId();
        Product product = productMapper.toEntity(request);
        Product updatedProduct = productService.update(id, product);
        UpdateProductResponse response = new UpdateProductResponse();
        response.setProduct(productMapper.toDto(updatedProduct));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProducts(@RequestPayload GetProductsRequest request) throws InvalidParamsException, ProductNotFoundException {
        Specification<Product> spec = productService.buildFilterSpecification(
                request.getId(), request.getName(),
                request.getCreationDate() != null ? productMapper.toLocalDate(request.getCreationDate()) : null,
                request.getPrice(), request.getPartNumber(), request.getManufactureCost(), request.getUnitOfMeasure()
        );
        Specification<Product> sortedSpec = productService.addSortCriteria(spec, request.getSort());
        GetProductsResponse response = new GetProductsResponse();
        response.getProductsGetResponse()
                .addAll(productService.findSpecifiedPage(sortedSpec, request.getPage(), request.getSize())
                        .stream().map(productMapper::toDto).toList());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "countByOwnerRequest")
    @ResponsePayload
    public CountByOwnerResponse countByOwner(@RequestPayload CountByOwnerRequest request) throws InvalidParamsException {
        CountByOwnerResponse response = new CountByOwnerResponse();
        response.setCount(productService.countByOwner(request.getPassportID()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "countByGreaterUnitOfMeasureRequest")
    @ResponsePayload
    public CountByGreaterUnitOfMeasureResponse countByGreaterUnitOfMeasure(
            @RequestPayload CountByGreaterUnitOfMeasureRequest request) throws InvalidParamsException {
        CountByGreaterUnitOfMeasureResponse response = new CountByGreaterUnitOfMeasureResponse();
        response.setCount(productService.countByGreaterThanUnitOfMeasure(request.getUnit()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getByPartNumberSubstringRequest")
    @ResponsePayload
    public GetByPartNumberSubstringResponse getByPartNumberSubstring(
            @RequestPayload GetByPartNumberSubstringRequest request) throws InvalidParamsException, ProductNotFoundException {
        GetByPartNumberSubstringResponse response = new GetByPartNumberSubstringResponse();
        response.getProductsGetResponse().addAll(productService.findAllByPartNumberHasSubstring(request.getSubstring())
                .stream().map(productMapper::toDto).toList());
        return response;
    }

}
