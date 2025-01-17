package org.korolev.dens.productservice.controllers;

import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.services.ProductMapper;
import org.korolev.dens.productservice.services.ProductService;
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
        String flatId = request.getId();
        Product product = productService.findById(flatId);
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productMapper.toDto(product));
        return response;
    }

}
