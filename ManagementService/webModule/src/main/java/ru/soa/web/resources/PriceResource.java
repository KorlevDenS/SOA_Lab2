package ru.soa.web.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.soa.ejb.beans.EbayServiceBeanRemote;
import ru.soa.ejb.exceptions.InvalidParamsException;
import ru.soa.ejb.exceptions.ProductServiceException;
import ru.soa.ejb.exceptions.RequestMessage;

@Path("/price")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PriceResource {

    @EJB
    private EbayServiceBeanRemote ebayServiceBeanRemote;

    @POST
    @Path("/decrease/{decreasePercent}")
    public Response decreaseAllByPercent(@PathParam("decreasePercent") Double decreasePercent)
            throws InvalidParamsException, JsonProcessingException, ProductServiceException {
        RequestMessage result = ebayServiceBeanRemote.askToDecreaseAllByPercent(decreasePercent);
        return Response.status(result.getCode()).entity(result.getMessage()).build();

    }

}
