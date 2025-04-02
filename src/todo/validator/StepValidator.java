package todo.validator;

import db.*;
import todo.entity.Step;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity){
        if (! (entity instanceof Step))
            throw new IllegalArgumentException("ERROR: entity is not a Step");

        Step step = (Step) entity;

        if (step.getTitle() == null || step.getTitle().isEmpty())
            throw new IllegalArgumentException("ERROR: step title is empty");

        if (Database.get(step.getTaskRef()) == null) {
            throw new IllegalArgumentException("ERROR: Cannot find task with ID: " + step.getTaskRef());
        }

    }
}
