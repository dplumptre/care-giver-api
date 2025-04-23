package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.Badge;
import com.overallheuristic.care_giver.model.Task;
import com.overallheuristic.care_giver.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskSetupCommand implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public TaskSetupCommand(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Task task1 = Task.builder()
                .title("Organize Daily Use Items")
                .description("Place essential items like the remote control, water bottle, and medications on the stroke survivor’s stronger side to encourage independent use.")
                .build();

        Task task2 = Task.builder()
                .title("Ensure Easy Access to Switches")
                .description("Reposition furniture or use extension switches to make power outlets and light switches easily reachable from the bed or wheelchair.")
                .build();

        Task task3 = Task.builder()
                .title("Adjust Bedside Setup")
                .description("Ensure the bedside has a stable table, accessible lamp, and necessary support structures to improve safety and comfort.")
                .build();

        Task task4 = Task.builder()
                .title("Set Up Bathroom Safety")
                .description("Install grab bars near the toilet and inside the shower to help prevent falls and support safe movement in the bathroom.")
                .build();


        List<Task> taskList = List.of(task1, task2, task3, task4);


        for (Task task : taskList) {
            boolean exists = taskRepository
                    .findByTitleAndDescription(
                            task.getTitle(),
                            task.getDescription()
                    ).isPresent();

            if (!exists) {
                taskRepository.save(task);
            }
        }


    }


}
