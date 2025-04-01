package todo.service;

import db.Database;
import db.Entity;
import todo.entity.Step;
import todo.validator.StepValidator;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StepService {

    public static void addStep(Scanner scanner) {
        try {
            System.out.println("Enter task ID: ");
            int taskRef = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Enter Title: ");
            String title = scanner.nextLine();

            Step step = new Step();
            step.setTitle(title);
            step.setTaskRef(taskRef);

            checkStepValidity(step);

            saveStep(step);
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input for task ID. Please enter a valid integer.");
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add step.");
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void checkStepValidity(Step step) {
        StepValidator validator = new StepValidator();
        validator.validate(step);

    }

    public static void saveStep(Step step) {
        step.setStatus(Step.Status.NOT_STARTED);
        Database.add(step);
        System.out.println("Step saved successfully.");
        System.out.println("ID: " + step.getTaskRef());
        System.out.println("Creation Date: " + step.getCreationDate());
    }

    public static void deleteStepsOfTask(int id) {
        try {
            Step step = new Step();
            ArrayList<Entity> steps = Database.getAll(step.getEntityCode());

            for (Entity stepEntity : steps) {
                step = (Step) stepEntity;
                if (step.getTaskRef() == id) {
                    Database.delete(step.getTaskRef());
                }
            }
        } catch (Exception e) {
            System.out.println("Error while deleting steps of task with ID=" + id + ": " + e.getMessage());

        }
    }
}
