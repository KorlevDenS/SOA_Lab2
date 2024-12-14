package ru.soa.web.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.soa.ejb.beans.EbayServiceBeanRemote;
import ru.soa.ejb.exceptions.InvalidParamsException;
import ru.soa.ejb.exceptions.ProductNotFoundException;
import ru.soa.ejb.exceptions.ProductServiceException;
import ru.soa.ejb.exceptions.RequestMessage;

@Path("/filter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilterResource {


    @EJB
    private EbayServiceBeanRemote ebayServiceBeanRemote;

    @GET
    @Path("/manufacturer/{manufacturer-id}")
    public Response getAllProductsByManufacturer(@PathParam("manufacturer-id") String manufacturerId)
            throws JsonProcessingException, InvalidParamsException, ProductNotFoundException, ProductServiceException {
        RequestMessage result = ebayServiceBeanRemote.askForAllByManufacturer(manufacturerId);
           if (result.getCode() == 200) {
        return Response.ok(result.getData()).build();
    } else {
        return Response.status(result.getCode())
                .entity(result.getMessage())
                .build();
    }
    }

}
