package com.stream.api;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@ApplicationScoped
@Slf4j
public class TaskProcessor {

    @Inject
    TaskScheduler taskScheduler;

    public Multi<String> scheduleTasks(List<Task> tasks) {
        List<Task> scheduledTasks = taskScheduler.introduceTaskDelays(tasks);

        return Multi.createFrom()
                .iterable(scheduledTasks)
                .onItem()
                    .transformToUniAndConcatenate(this::processScheduledTask);
    }

    private Uni<String> processScheduledTask(Task task) {
        String message = String.format("Task %s is WIP for %d seconds (delayed by %d seconds)",
                task.getId(), task.getDuration(), task.getDelay());

        return Uni.createFrom()
                .item(message)
                .onItem()
                    .delayIt()
                    .by(Duration.ofSeconds(task.getDelay()))
                    .invoke(log::info)
                    .invoke(() -> taskScheduler.prepareForHelpIfNeeded(task));
    }
}
