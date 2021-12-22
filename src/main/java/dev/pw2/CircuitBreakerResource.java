package dev.pw2;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/check")
public class CircuitBreakerResource {

    @Inject
    private CircuitBreakerService service;

    @GET
    @Path("/availability")
    public Response checkAvailability() {
        try {
            final int availability = service.getAvailability();
            return Response.ok(availability).build();
        } catch (RuntimeException e) {
            final String message = e.getClass().getSimpleName() + ": " + e.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build(); 
        }
    }
}