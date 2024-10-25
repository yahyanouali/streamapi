package com.stream.api;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.random.RandomGenerator;

@ApplicationScoped
public class LogProducer {

    @Inject
    LogService logService;

    @Scheduled(every = "3s")
    public void produceLog() {
        logService.log("[PLAY] I'm a random number :) " + getRandomInt(200));
    }

    private int getRandomInt(int max) {
        RandomGenerator gen = RandomGenerator.of("L128X256MixRandom");
        return gen.nextInt(max);
    }
}