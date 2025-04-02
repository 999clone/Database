
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            switch (command) {
                case "add task":
                    TaskService.addTask(scanner);
                    break;
                case "add step":
                    StepService.addStep(scanner);
                    break;
                case "delete":
                    TaskService.deleteEntity(scanner);
                    break;
                case "update task":
                    TaskService.updateTask(scanner);
                    break;
                case "update step":
                    StepService.updateStep(scanner);
                    break;
                case "get task-by-id":
                    TaskService.getTaskById(scanner);
                    break;
                case "get all-tasks":
                    TaskService.getAllTasks(scanner);
                    break;
                case "get incomplete-tasks":
                    TaskService.getIncompleteTasks(scanner);
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }
}
