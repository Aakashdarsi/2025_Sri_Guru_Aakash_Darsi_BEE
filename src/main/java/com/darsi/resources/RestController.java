package com.darsi.resources;

import com.codahale.metrics.annotation.Timed;
import com.darsi.api.CoinChangeRequest;
import com.darsi.api.CoinChangeResponse;
import com.darsi.core.RestService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@Path("/api")

public class RestController {
    private final RestService restService;

    public RestController(RestService restService){
        this.restService = restService;
    }

    @POST
    @Path("/calculate")
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response calculateMinimumCoins(@Valid CoinChangeRequest request) {
        try {
            CoinChangeResponse response = restService.calculateMinimumCoins(request);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new CoinChangeResponse(null, e.getMessage(), false))
                    .build();
        }
    }


}
