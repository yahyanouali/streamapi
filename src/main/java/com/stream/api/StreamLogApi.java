package com.stream.api;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/log")
public class StreamLogApi {

    @Inject
    LogService logService;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> streamLogs() {
        return logService.getLogStream();
    }

}
