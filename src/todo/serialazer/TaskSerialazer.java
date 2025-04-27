package todo.serialazer;

import db.Entity;
import db.Serializer;
import todo.entity.Task;

import java.util.Date;

public class TaskSerialazer implements Serializer {

    @Override
    public String serialize(Entity e) {
        Task task = (Task) e;
        String status = "";
        switch (task.getStatus()){
            case Task.Status.COMPLETED:
                status = "completed";
                break;
            case Task.Status.IN_PROGRESS:
                status = "inProgress";
                break;
            case Task.Status.NOT_STARTED:
                status = "notStarted";
                break;
        }
        return String.valueOf(task.getEntityCode()) + ";" + String.valueOf(task.id)+ ";" + task.getTitle() + ";" + task.getDescription() + ";" + String.valueOf(task.getDueDate().getTime()) + ";" + String.valueOf(task.getCreationDate().getTime()) + ";" + String.valueOf(task.getLastModificationDate().getTime()) + ";" + status;
    }

    @Override
    public Entity deserialize(String s) {
        String[] split = s.split(";");
        Task task = new Task();
        task.setEntityCode(Integer.parseInt(split[0]));
        task.id = Integer.parseInt(split[1]);
        task.setTitle(split[2]);
        task.setDescription(split[3]);
        task.setDueDate(new Date(Long.parseLong(split[4])));
        task.setCreationDate(new Date(Long.parseLong(split[5])));
        task.setLastModificationDate(new Date(Long.parseLong(split[6])));
        String status = split[7];
        switch (status){
            case "COMPLETED":
                task.setStatus(Task.Status.COMPLETED);
                break;
            case "IN_PROGRESS":
                task.setStatus(Task.Status.IN_PROGRESS);
                break;
            case "NOT_STARTED":
                task.setStatus(Task.Status.NOT_STARTED);
                break;
            default:
                task.setStatus(Task.Status.NOT_STARTED);
        }
        return task;
    }
}
