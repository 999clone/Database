package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import todo.entity.Step;
import todo.entity.Task;
import todo.validator.TaskValidator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TaskService {

    public static void addTask(Scanner scanner) {

        try {
            System.out.println("Title: ");
            String title = scanner.nextLine();

            System.out.println("Description: ");
            String description = scanner.nextLine();

            System.out.println("due Date (YYYY-MM-DD): ");
            String dueDateString = scanner.nextLine();

            Date dueDate = parseDate(dueDateString);
            if (dueDate == null) {
                System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                return;
            }
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDate);

            checkTaskValidity(task);

            saveTask(task);

        }catch (IllegalArgumentException e){
            System.out.println("Cannot add task.");
            System.out.println(e.getMessage());
        }
    }

    public static void deleteEntity(Scanner scanner) {
        int id = 0;
        try {
            System.out.println("ID: ");
             id = scanner.nextInt();
            scanner.nextLine();

            Entity entity = Database.get(id);
            if (entity instanceof Task)
                StepService.deleteStepsOfTask(id);
            else
                Database.delete(id);
            System.out.println("Entity with ID=" + id + " successfully deleted.");

        } catch (NumberFormatException e) {
            System.out.println("Cannot delete entity.");
            System.out.println("Error: Invalid ID format.");
        }  catch (EntityNotFoundException e ) {
            System.out.println("Cannot delete entity with ID=" + id + ".");
            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Cannot delete entity.");
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void updateTask(Scanner scanner) {
        try {
            System.out.println("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("field: ");
            String field = scanner.nextLine();

            System.out.println("New Value: ");
            String newValue = scanner.nextLine();

            Entity entity = Database.get(id);
            if (entity instanceof Task task) {
                String oldValue = "";
                switch (field) {
                    case "title":
                        oldValue = task.getTitle();
                        task.setTitle(newValue);
                        break;
                    case "description":
                        oldValue = task.getDescription();
                        task.setDescription(newValue);
                        break;
                    case "dueDate":
                        oldValue = task.getDueDate().toString();
                        task.setDueDate(parseDate(newValue));
                        break;
                    case "status":
                        oldValue = task.getStatus().toString();
                        task.setStatus(Task.Status.valueOf(newValue));
                        StepService.changeAllStepsStatus(task);
                    default:
                        System.out.println("Invalid field.");
                }
                Database.update(task);
                System.out.println("Successfully updated the task.");
                System.out.println("Field: " + field);
                System.out.println("Old Value: " + oldValue);
                System.out.println("New Value: " + newValue);
                System.out.println("Modification Date: " + task.getLastModificationDate());
            }

        }catch (IllegalArgumentException e){
            System.out.println("Cannot update entity.");
        }catch (EntityNotFoundException e ) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkTaskValidity(Task task) {
        try {
            TaskValidator taskValidator = new TaskValidator();
            taskValidator.validate(task);
        }catch (IllegalArgumentException e){
            System.out.println("Cannot add task.");
            System.out.println(e.getMessage());
        }
    }

    public static void saveTask(Task task) {
        task.setStatus(Task.Status.NOT_STARTED);
        Database.add(task);
        System.out.println("Task saved successfully.");
        System.out.println("ID: " + task.id);
    }

    public static void getTaskById(Scanner scanner) {
        try {
            System.out.println("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Entity entity = Database.get(id);
            if (entity instanceof Task task) {
                System.out.println("ID: " + task.id);
                System.out.println("Title: " + task.getTitle());
                System.out.println("Due Date: " + task.getDueDate());
                System.out.println("Status: " + task.getStatus());

                StepService.getAllSteps(task);
            }
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public static void getAllTasks(Scanner scanner) {
        ArrayList<Entity> allTasks = Database.getAll(new Task().getEntityCode());

        if (allTasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        allTasks.sort((task1, task2) -> {
            Task t1 = (Task) task1;
            Task t2 = (Task) task2;
            return t1.getDueDate().compareTo(t2.getDueDate());
        });

        for (Entity entity : allTasks) {
            Task task = (Task) entity;
            System.out.println("ID: " + task.id);
            System.out.println("Title: " + task.getTitle());
            System.out.println("Due Date: " + task.getDueDate());
            System.out.println("Status: " + task.getStatus());
            StepService.getAllSteps(task);
        }

    }

    public static void getIncompleteTasks(Scanner scanner) {
        ArrayList<Entity> allTasks = Database.getAll(new Task().getEntityCode());
        boolean foundIncompleteTask = false;
        if (allTasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        for (Entity entity : allTasks) {
            Task task = (Task) entity;

            if (task.getStatus() != Task.Status.COMPLETED) {
                foundIncompleteTask = true;

                System.out.println("ID: " + task.id);
                System.out.println("Title: " + task.getTitle());
                System.out.println("Due Date: " + task.getDueDate());
                System.out.println("Status: " + task.getStatus());
                StepService.getAllSteps(task);
            }
        }
        if (!foundIncompleteTask) {
            System.out.println("No incomplete tasks found.");
        }
    }

    public static void setAsNotStarted(int taskId) {
        try {
            Task task = new Task();
            task.setStatus(Task.Status.NOT_STARTED);
            Database.update(task);
        }catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Task not found with id: " + taskId);
        }
    }

    public static void setAsInProgress(int taskId) {
        try {
            Task task = new Task();
            task.setStatus(Task.Status.IN_PROGRESS);
            Database.update(task);
        }catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Task not found with id: " + taskId);
        }
    }

    public static void setAsCompleted(Task task) {
        try {
            task.setStatus(Task.Status.COMPLETED);
            Database.update(task);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Task not found with id: " + task.id);
        }
    }


    private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

}
