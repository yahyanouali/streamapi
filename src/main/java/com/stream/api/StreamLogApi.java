package com.stream.api;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/log")
public class StreamLogApi {

    @Inject
    TaskProcessor taskProcessor;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/schedule")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> scheduleLogs(List<Task> tasks) {
        return taskProcessor.scheduleTasks(tasks);
    }
}
