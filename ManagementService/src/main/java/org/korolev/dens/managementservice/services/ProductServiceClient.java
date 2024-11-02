package org.korolev.dens.managementservice.services;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.korolev.dens.managementservice.exceptions.ProductServiceException;

public class ProductServiceClient {

    private final Client client;
    private final String baseUrl;

    public ProductServiceClient() {
        this.client = ClientBuilder.newClient();
        this.baseUrl = "http://localhost:8177/products";
    }

    public Response getData(String apiUrl) throws ProductServiceException {
        WebTarget target = this.client.target(this.baseUrl + apiUrl);
        Response response = target.request().get();
        if (response.getStatus() == 503) {
            throw new ProductServiceException(
                    "Сервис временно недоступен: попробуйте позже.",
                    Response.Status.fromStatusCode(504)
            );
        } else if (response.getStatus() == 504) {
            throw new ProductServiceException(
                    "Ошибка шлюза: не удалось получить ответ от внешнего сервиса.",
                    Response.Status.fromStatusCode(504)
            );
        }
        return response;
    }

}
