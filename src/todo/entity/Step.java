package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Step extends Entity implements Trackable {
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
    public void setCreationDate(Date date) {
        setCreationDate(date);
    }

    @Override
    public Date getCreationDate() {
        return getCreationDate();
    }

    @Override
    public void setLastModificationDate(Date date) {
        setLastModificationDate(date);
    }

    @Override
    public Date getLastModificationDate() {
        return getCreationDate();
    }


    @Override
    public int getEntityCode() {
        return 13;
    }
}
