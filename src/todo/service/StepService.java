package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import todo.entity.Step;
import todo.entity.Task;
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


            saveStep(step);
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input for task ID. Please enter a valid integer.");
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add step.");
            System.out.println("Error: " + e.getMessage());
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    public static void saveStep(Step step) {
        step.setStatus(Step.Status.NOT_STARTED);
        Database.add(step);
        System.out.println("Step added to task with taskID " + step.getTaskRef() + " successfully.");
        System.out.println("ID: " + step.id);
        System.out.println("Creation Date: " + step.getCreationDate());
    }

    public static void deleteStepsOfTask(int id) {
        try {
            Step step = new Step();
            ArrayList<Entity> steps = Database.getAll(step.getEntityCode());

            for (Entity stepEntity : steps) {
                step = (Step) stepEntity;
                if (step.getTaskRef() == id) {
                    Database.delete(step.id);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while deleting steps of task with ID=" + id + ": " + e.getMessage());

        }
    }

    public static void updateStep(Scanner scanner) {
        try {
            String oldValue = "";
            System.out.println("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Field: ");
            String field = scanner.nextLine();

            System.out.println("New Value: ");
            String newValue = scanner.nextLine();

            Entity entity = Database.get(id);
            if (entity instanceof Step) {
                Step step = (Step) entity;
                switch (field) {
                    case "title":
                        oldValue = step.getTitle();
                        step.setTitle(newValue);
                        break;
                    case "status":
                        oldValue = step.getStatus().toString();
                        step.setStatus(Step.Status.valueOf(newValue));
                        Database.update(step);
                        if (allStepsComplete((Task) Database.get(step.getTaskRef()))) {
                            TaskService.setAsCompleted((Task) Database.get(step.getTaskRef()));
                        }
                        break;
                    default:
                        System.out.println("Invalid field.");
                }
                Database.update(step);
                System.out.println("Successfully updated the step.");
                System.out.println("Field: " + field);
                System.out.println("Old Value: " + oldValue);
                System.out.println("New Value: " + newValue);
                System.out.println("Modification Date: " + step.getLastModificationDate());
            }
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("Invalid input for field. Please enter a valid field.");
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void changeAllStepsStatus(Task task) {
        ArrayList<Entity> steps = Database.getAll(new Step().getEntityCode());
        for (Entity stepEntity : steps) {
            Step step = (Step) stepEntity;
            if (step.getTaskRef() == task.id) {
                step.setStatus(Step.Status.COMPLETED);
                Database.update(step);
            }
        }
    }

    public static boolean allStepsComplete(Task task) {
        ArrayList<Entity> steps = Database.getAll(new Step().getEntityCode());

        for (Entity stepEntity : steps) {
            Step step = (Step) stepEntity;
            if ((step.getTaskRef() == task.id) && (step.getStatus() != Step.Status.COMPLETED)) {
                return false;
            }
        }
        return true;
    }


    public static void getAllSteps(Task task) {
        ArrayList<Entity> steps = Database.getAll(new Step().getEntityCode());
        System.out.println("Steps:");
        boolean hasSteps = false;

        for (Entity stepEntity : steps) {
            Step step = (Step) stepEntity;
            if (step.getTaskRef() == task.id) {
                hasSteps = true;
                System.out.println("    + " + step.getTitle() + ":");
                System.out.println("        ID: " + step.id);
                System.out.println("        Status: " + step.getStatus());
            }
        }
        if (!hasSteps) {
            System.out.println("No steps available for this task.");
        }

    }
}
