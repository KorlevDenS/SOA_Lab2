package ru.soa.web.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import ru.soa.ejb.beans.EbayServiceBeanRemote;

@Path("/ping")
public class PingResource {

    @EJB
    private EbayServiceBeanRemote ebayServiceBeanRemote;

    @GET
    public String getPingResponse() {
        return ebayServiceBeanRemote.ping();
    }
}
