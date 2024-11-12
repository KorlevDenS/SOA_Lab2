package org.korolev.dens.managementservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.korolev.dens.managementservice.exceptions.InvalidParamsException;
import org.korolev.dens.managementservice.exceptions.ProductServiceException;
import org.korolev.dens.managementservice.services.ProductsService;

@Path("/price")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PriceResource {

    private final ProductsService productsService;

    public PriceResource() {
        this.productsService = new ProductsService();
    }

    @POST
    @Path("/decrease/{decreasePercent}")
    public Response decreaseAllByPercent(@PathParam("decreasePercent") Double decreasePercent)
            throws InvalidParamsException, JsonProcessingException, ProductServiceException {
        return productsService.askToDecreaseAllByPercent(decreasePercent);
    }

}
