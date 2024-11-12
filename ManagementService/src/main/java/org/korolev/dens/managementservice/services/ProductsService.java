package org.korolev.dens.managementservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.core.Response;
import org.korolev.dens.managementservice.entities.Product;
import org.korolev.dens.managementservice.exceptions.InvalidParamsException;
import org.korolev.dens.managementservice.exceptions.ProductNotFoundException;
import org.korolev.dens.managementservice.exceptions.ProductServiceException;
import org.korolev.dens.managementservice.exceptions.RequestMessage;

import java.util.List;

public class ProductsService {

    private final ProductServiceClient productServiceClient;
    private final ObjectMapper objectMapper;

    public ProductsService() {
        this.productServiceClient = new ProductServiceClient();
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public Response askToDecreaseAllByPercent(Double percent) throws ProductServiceException, JsonProcessingException,
            InvalidParamsException {
        if (percent <= 0 || percent >= 100) {
            throw new InvalidParamsException(
                    "Неверно указан процент уменьшения — процент должен быть положительным числом и меньше 100");
        }
        Response response = productServiceClient.getData("");
        if (response.getStatus() != 200) {
            return response;
        }
        String json = response.readEntity(String.class);
        List<Product> allProducts = objectMapper.readValue(json, new TypeReference<>() {});
        List<Product> decreasedProducts = allProducts.stream().filter(p -> p.getName() != null)
                .peek(p -> p.setPrice(p.getPrice() * ((100 - percent) / 100))).toList();
        if (decreasedProducts.stream().anyMatch(p -> p.getPrice() < 1)) {
            throw new ProductServiceException(
                    "Конфликт: не удается снизить цену, так как продукт не может быть уценен.",
                    Response.Status.CONFLICT);
        }
        for (Product dp : decreasedProducts) {
            Response updateResponse = productServiceClient.updateData("/" + dp.getId(), dp);
            if (updateResponse.getStatus() != 200) {
                return updateResponse;
            }
        }
        return Response.ok(new RequestMessage(200, "Цены успешно обновлены.")).build();
    }

    public Response askForAllByManufacturer(String manufacturerId) throws InvalidParamsException,
            ProductNotFoundException, JsonProcessingException, ProductServiceException {
        if (manufacturerId == null || manufacturerId.isEmpty()) {
            throw new InvalidParamsException("Неверно указан ID производителя: должен быть непустой строкой.");
        }
        Response response = productServiceClient.getData("");
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            String json = response.readEntity(String.class);
            List<Product> allProducts = objectMapper.readValue(json, new TypeReference<>() {});
            List<Product> filteredProducts = allProducts.stream()
                    .filter(p -> p.getOwner() != null)
                    .filter(p -> p.getOwner().getPassportID() != null)
                    .filter(p -> p.getOwner().getPassportID().equals(manufacturerId))
                    .toList();
            if (filteredProducts.isEmpty()) {
                throw new ProductNotFoundException("Продукты с указанным производителем не найдены.");
            }
            return Response.ok(filteredProducts).build();
        } else {
            return response;
        }
    }

}
