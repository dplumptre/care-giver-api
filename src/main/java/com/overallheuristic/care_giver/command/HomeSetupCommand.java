package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.HomeSetupStatus;
import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.Task;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.repositories.HomeSetupStatusRepository;
import com.overallheuristic.care_giver.repositories.PatientRepository;
import com.overallheuristic.care_giver.repositories.TaskRepository;
import com.overallheuristic.care_giver.utils.AuthUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomeSetupCommand implements CommandLineRunner {


    private final TaskRepository taskRepository;
    private final PatientRepository patientRepository;
    private final HomeSetupStatusRepository homeSetupStatusRepository;


    public HomeSetupCommand(TaskRepository taskRepository, PatientRepository patientRepository, HomeSetupStatusRepository homeSetupStatusRepository, AuthUtil authUtil) {
        this.taskRepository = taskRepository;
        this.patientRepository = patientRepository;
        this.homeSetupStatusRepository = homeSetupStatusRepository;
    }

    public void run(String... args) {
        List<Patient> allPatients = patientRepository.findAll();
        List<Task> allTasks = taskRepository.findAll();

        for (Patient patient : allPatients) {
            for (Task task : allTasks) {
                boolean exists = homeSetupStatusRepository.existsByPatientAndTask(patient, task);
                if(!exists) {
                    HomeSetupStatus newStatus = new HomeSetupStatus();
                    newStatus.setPatient(patient);
                    newStatus.setTask(task);
                    newStatus.setIsCompleted(false);
                    homeSetupStatusRepository.save(newStatus);
                }
            }
        }
    }
}
