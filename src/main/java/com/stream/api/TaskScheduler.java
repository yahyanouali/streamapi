package com.stream.api;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Slf4j
public class TaskScheduler {

    private final List<Task> scheduledTasks = new ArrayList<>();

    public List<Task> introduceTaskDelays(List<Task> tasks) {
        scheduledTasks.clear();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            int delay = (i == 0) ? 1 : tasks.get(i - 1).getDuration();
            scheduledTasks.add(new Task(task.getId(), task.getDuration(), task.isNeedHelp(), delay));
        }
        return scheduledTasks;
    }

    public void prepareForHelpIfNeeded(Task currentTask) {
        int currentIndex = scheduledTasks.indexOf(currentTask);
        if (shouldPrepareHelp(currentIndex)) {
            log.info("Preparing help for task {}", scheduledTasks.get(currentIndex + 2).getId());
        }
    }

    private boolean shouldPrepareHelp(int currentIndex) {
        return currentIndex + 2 < scheduledTasks.size() && scheduledTasks.get(currentIndex + 2).isNeedHelp();
    }
}
