package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {

    @Override
    public void validate(Entity entity) {
        if (!(entity instanceof Task))
            throw new IllegalArgumentException("ERROR: Entity must be a task");
        Task task = (Task) entity;
        if (task.getTitle() == null || task.getTitle().isEmpty())
            throw new IllegalArgumentException("ERROR: Task title Cannot be empty");
    }

}
