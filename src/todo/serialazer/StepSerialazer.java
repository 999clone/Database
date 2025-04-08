package todo.serialazer;

import db.Entity;
import db.Serializer;
import todo.entity.Step;

import java.util.Date;

public class StepSerialazer implements Serializer {
    @Override
    public String serialize(Entity e) {
        Step step = (Step) e;
        return String.valueOf(step.getEntityCode()) + ";" +String.valueOf(step.id)+ ";" + step.getTitle()+ ";" + String.valueOf(step.getTaskRef()) + ";" + String.valueOf(step.getCreationDate().getTime()) + ";" + String.valueOf(step.getLastModificationDate().getTime()) + ";" + step.getStatus();

    }

    @Override
    public Entity deserialize(String s) {
        String[] split = s.split(";");
        Step step = new Step();
        step.setEntityCode(Integer.parseInt(split[0]));
        step.id = Integer.parseInt(split[1]);
        step.setTitle(split[2]);
        step.setTaskRef(Integer.parseInt(split[3]));
        step.setCreationDate(new Date(Long.parseLong(split[4])));
        step.setLastModificationDate(new Date(Long.parseLong(split[5])));
        String status = split[6];
        switch (status){
            case "COMPLETED":
                step.setStatus(Step.Status.COMPLETED);
                break;
            case "NOT-STARTED":
                step.setStatus(Step.Status.NOT_STARTED);
                break;
            default:
                step.setStatus(Step.Status.NOT_STARTED);
        }

        return step;
    }
}
