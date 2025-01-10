package ru.soa.ejb.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.Stateless;
import ru.soa.ejb.exceptions.InvalidParamsException;
import ru.soa.ejb.exceptions.ProductNotFoundException;
import ru.soa.ejb.exceptions.ProductServiceException;
import ru.soa.ejb.exceptions.RequestMessage;
import ru.soa.ejb.services.ProductServiceClient;
import ru.soa.ejb.services.ProductsService;

@Stateless
public class EbayServiceBean implements EbayServiceBeanRemote {

    private final ProductsService productsService;

    public EbayServiceBean() {
        ProductServiceClient productServiceClient = new ProductServiceClient();
        this.productsService = new ProductsService();
    }

    @Override
    public String ping() {
        return "pong";
    }

    @Override
    public RequestMessage askForAllByManufacturer(String manufacturerId) throws InvalidParamsException, ProductNotFoundException, ProductServiceException, JsonProcessingException {
        return productsService.askForAllByManufacturer(manufacturerId);
    }

    @Override
    public RequestMessage askToDecreaseAllByPercent(Double percent) throws InvalidParamsException, ProductServiceException, JsonProcessingException {
        return productsService.askToDecreaseAllByPercent(percent);
    }
}
