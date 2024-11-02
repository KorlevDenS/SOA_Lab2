package org.korolev.dens.managementservice.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.korolev.dens.managementservice.services.ProductsService;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsResource {

    private final ProductsService productsService;

    public ProductsResource() {
        productsService = new ProductsService();
    }

    @GET
    @Path("/filter/manufacturer/{manufacturer-id}")
    public Response getAllProductsByManufacturer(@PathParam("manufacturer-id") String manufacturerId) throws Exception {
        return Response.ok().entity(productsService.findAllByManufacturer(manufacturerId)).build();
    }

}
