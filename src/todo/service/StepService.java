package todo.service;

import db.Database;
import todo.entity.Step;
import todo.validator.StepValidator;

import java.util.Scanner;

public class StepService {

    public static void addStep(Scanner scanner) {
        System.out.println("Enter task ID: ");
        int taskRef = scanner.nextInt();

        System.out.println("Enter Title: ");
        String title = scanner.nextLine();


    }

    public static void checkStepValidity(int taskRef, String title) {
        StepValidator validator = new StepValidator();
        Step step = new Step();
        step.setTitle(title);
        step.setTaskRef(taskRef);
        validator.validate(step);
    }

    public static void saveStep(int taskRef, String title) {
        Step step = new Step();
        step.setTaskRef(taskRef);
        step.setTitle(title);
        step.setStatus(Step.Status.NOT_STARTED);
        Database.add(step);
    }
}
