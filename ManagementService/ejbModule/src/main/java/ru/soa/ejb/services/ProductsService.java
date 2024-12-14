package ru.soa.ejb.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.core.Response;
import ru.soa.ejb.entities.Product;
import ru.soa.ejb.exceptions.InvalidParamsException;
import ru.soa.ejb.exceptions.ProductNotFoundException;
import ru.soa.ejb.exceptions.ProductServiceException;
import ru.soa.ejb.exceptions.RequestMessage;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsService {

    private final ProductServiceClient productServiceClient;
    private final ObjectMapper objectMapper;

    public ProductsService() {
        this.productServiceClient = new ProductServiceClient();
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

public RequestMessage askToDecreaseAllByPercent(Double percent) throws ProductServiceException, JsonProcessingException, InvalidParamsException {
    if (percent <= 0 || percent >= 100) {
        throw new InvalidParamsException(
                "Неверно указан процент уменьшения — процент должен быть положительным числом и меньше 100");
    }

    Response response = productServiceClient.getData("");
    if (response.getStatus() != 200) {
        String errorMessage = response.readEntity(String.class);
        throw new ProductServiceException("Ошибка при получении данных о продуктах: " + errorMessage, Response.Status.BAD_REQUEST);
    }

    String json = response.readEntity(String.class);
    List<Product> allProducts = objectMapper.readValue(json, new TypeReference<>() {});

    List<Product> decreasedProducts = allProducts.stream().filter(p -> p.getName() != null)
            .peek(p -> p.setPrice(p.getPrice() * ((100 - percent) / 100)))
            .toList();

    if (decreasedProducts.stream().anyMatch(p -> p.getPrice() < 1)) {
        throw new ProductServiceException(
                "Конфликт: не удается снизить цену, так как продукт не может быть уценен.",
                Response.Status.CONFLICT);
    }

    for (Product dp : decreasedProducts) {
        Response updateResponse = productServiceClient.updateData("/" + dp.getId(), dp);
        if (updateResponse.getStatus() != 200) {
            String errorMessage = updateResponse.readEntity(String.class);
            throw new ProductServiceException("Ошибка при обновлении продукта с ID " + dp.getId() + ": " + errorMessage, Response.Status.BAD_REQUEST);
        }
    }

    return new RequestMessage(200, "Цены успешно обновлены.");
}


   public RequestMessage askForAllByManufacturer(String manufacturerId) throws InvalidParamsException,
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

        return new RequestMessage(200, "Продукты найдены", filteredProducts);
    } else {
        return new RequestMessage(response.getStatus(), "Ошибка при получении данных");
    }
}


}
