package com.stream.api;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Slf4j
public class TaskProcessor {

    public Multi<String> scheduleTasks(List<Task> tasks) {
        List<Task> scheduledTasks = introduceTaskDelayFromPreviousTask(tasks);

        return Multi.createFrom().iterable(scheduledTasks)
                .onItem().transformToUniAndConcatenate(scheduledTask -> {
                    // Prepare help if necessary before logging

                    // message to log
                    String message = String.format("task %s is WIP for %d seconds (delayed by %d seconds)",
                            scheduledTask.getId(), scheduledTask.getDuration(), scheduledTask.getDelay());

                    // Use the delay to schedule the start of the task
                    return Uni.createFrom().item(message)
                            .onItem().delayIt().by(Duration.ofSeconds(scheduledTask.getDelay()))
                            .invoke(log::info)
                            .invoke(() ->  prepareForHelpIfNeeded(scheduledTasks, scheduledTask));
                });
    }


    private void prepareForHelpIfNeeded(List<Task> scheduledTasks, Task currentTask) {
        // Get the index of the current task
        int currentIndex = scheduledTasks.indexOf(currentTask);

        if (currentIndex + 2 < scheduledTasks.size() && scheduledTasks.get(currentIndex + 2).isNeedHelp()) {
            log.info("Preparing for help for task {}", scheduledTasks.get(currentIndex + 2).getId());
            // Add your preparation logic here
        }
    }

    private static List<Task> introduceTaskDelayFromPreviousTask(List<Task> tasks) {
        List<Task> scheduledTasks = new ArrayList<>();

        // Populate the new list with tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            int delay = (i == 0) ? 1 : tasks.get(i - 1).getDuration();
            scheduledTasks.add(new Task(task.getId(), task.getDuration(), task.isNeedHelp(), delay));
        }
        return scheduledTasks;
    }
}
