package com.stream.api;

import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.util.concurrent.SubmissionPublisher;

@ApplicationScoped
public class LogService {

    private final SubmissionPublisher<String> publisher =
            new SubmissionPublisher<>(Infrastructure.getDefaultExecutor(), Integer.MAX_VALUE);

    public void log(String message) {
        publisher.submit(message);
    }

    public Multi<String> getLogStream() {
        return Multi.createFrom().publisher(publisher);
    }

}
