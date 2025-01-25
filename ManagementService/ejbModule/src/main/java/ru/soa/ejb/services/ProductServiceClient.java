package ru.soa.ejb.services;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import ru.soa.ejb.entities.Product;
import ru.soa.ejb.exceptions.ProductServiceException;

public class ProductServiceClient {

    private final Client client;
    private final String baseUrl;

    public ProductServiceClient() {
        this.client = ClientBuilder.newClient();
        this.baseUrl = "http://host.docker.internal:8144/catalog/products";
    }

    public Response getData(String apiUrl) throws ProductServiceException {
        try {
            WebTarget target = this.client.target(this.baseUrl + apiUrl);
            return target.request().get();
        } catch (ProcessingException e) {
            System.out.println(e.getMessage());
            throw new ProductServiceException(
                    "Сервис временно недоступен: попробуйте позже.",
                    Response.Status.fromStatusCode(504)
            );
        }
    }

    public Response updateData(String apiUrl, Product updatedProduct) throws ProductServiceException {
        try {
            WebTarget target = this.client.target(this.baseUrl + apiUrl);
            return target.request().put(Entity.json(updatedProduct));
        } catch (ProcessingException e) {
            throw new ProductServiceException(
                    "Сервис временно недоступен: попробуйте позже.",
                    Response.Status.fromStatusCode(504)
            );
        }
    }

}
