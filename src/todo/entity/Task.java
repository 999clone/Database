package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    private String title;
    private String description;
    private Date dueDate;
    private Status status;

    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return status;
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
        return getLastModificationDate();
    }

    @Override
    public int getEntityCode() {
        return 0;
    }
}
