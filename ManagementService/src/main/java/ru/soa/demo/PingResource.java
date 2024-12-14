package ru.soa.demo;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/ping")
public class PingResource {

    @Inject
    private PingBean pingBean;

    @GET
    public String getPingResponse() {
        return pingBean.ping();
    }
}
