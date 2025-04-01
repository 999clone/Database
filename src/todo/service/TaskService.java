package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import todo.entity.Task;
import todo.validator.TaskValidator;
import java.text.SimpleDateFormat;
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

            checkTaskValidity(title, description, dueDate);

            saveTask(title, description, dueDate);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void checkTaskValidity(String title, String description, Date dueDate) {
        TaskValidator taskValidator = new TaskValidator();

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setStatus(Task.Status.NOT_STARTED);
        taskValidator.validate(task);

    }

    public static void saveTask(String title, String description, Date duedate) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(duedate);
        task.setStatus(Task.Status.NOT_STARTED);
        Database.add(task);
        System.out.println("Task saved successfully.");
        System.out.println("ID: " + task.id);
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

    public static void setAsCompleted(int taskId) {
        try {
            Task task = new Task();
            task.setStatus(Task.Status.COMPLETED);
            Database.update(task);
        }catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Task not found whith id: " + taskId);
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
