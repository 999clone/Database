package todo.entity;

import db.Entity;

public class Step extends Entity {
    private String title;
    private Status status;
    private int taskRef;


    public enum Status {
        NOT_STARTED,
        COMPLETED,
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }
    public void setTaskRef(int taskRef) {
        this.taskRef = taskRef;
    }
    public int getTaskRef() {
        return taskRef;
    }


    @Override
    public int getEntityCode() {
        return 0;
    }
}
