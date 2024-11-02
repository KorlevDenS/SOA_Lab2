package org.korolev.dens.managementservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.core.Response;
import org.korolev.dens.managementservice.entities.Product;

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


    public List<Product> findAllByManufacturer(String manufacturerId) throws Exception {
        Response response = productServiceClient.getData("");
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            String json = response.readEntity(String.class);
            return objectMapper.readValue(json, new TypeReference<>() {});
        } else {
            throw new Exception(response.getStatusInfo().getStatusCode() + " ERROR");
        }
    }

}
