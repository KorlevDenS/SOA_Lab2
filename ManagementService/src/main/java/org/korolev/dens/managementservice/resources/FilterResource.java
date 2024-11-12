package org.korolev.dens.managementservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.korolev.dens.managementservice.exceptions.InvalidParamsException;
import org.korolev.dens.managementservice.exceptions.ProductNotFoundException;
import org.korolev.dens.managementservice.exceptions.ProductServiceException;
import org.korolev.dens.managementservice.services.ProductsService;

@Path("/filter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilterResource {

    private final ProductsService productsService;

    public FilterResource() {
        productsService = new ProductsService();
    }

    @GET
    @Path("/manufacturer/{manufacturer-id}")
    public Response getAllProductsByManufacturer(@PathParam("manufacturer-id") String manufacturerId)
            throws InvalidParamsException, ProductNotFoundException, JsonProcessingException, ProductServiceException {
        return productsService.askForAllByManufacturer(manufacturerId);
    }

}
