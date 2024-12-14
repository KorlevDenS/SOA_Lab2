package ru.soa.ejb.beans;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.Remote;
import jakarta.ws.rs.core.Response;
import ru.soa.ejb.exceptions.InvalidParamsException;
import ru.soa.ejb.exceptions.ProductNotFoundException;
import ru.soa.ejb.exceptions.ProductServiceException;
import ru.soa.ejb.exceptions.RequestMessage;

@Remote
public interface EbayServiceBeanRemote {
    String ping();
    RequestMessage askForAllByManufacturer(String manufacturerId) throws InvalidParamsException, ProductNotFoundException, ProductServiceException, JsonProcessingException;

    RequestMessage askToDecreaseAllByPercent(Double percent) throws InvalidParamsException, ProductServiceException, JsonProcessingException;
}
