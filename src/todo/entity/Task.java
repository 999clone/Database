package todo.entity;

import db.Entity;
import db.Trackable;
import todo.service.TaskService;

import java.util.Date;

public class Task extends Entity implements Trackable {
    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    private Date creationDate;
    private Date lastModificationDate;

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
        if (!TaskService.isValidDate(dueDate.toString()))
            throw new IllegalArgumentException("Invalid due date");
        this.dueDate = dueDate;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setStatus(Status status) {
        if (status == null)
            throw new IllegalArgumentException("status cannot be null");
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }

    @Override
    public void setCreationDate(Date date) {
        if (creationDate == null)
            throw new IllegalArgumentException("Task creationDate cannot be null");
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        if (creationDate == null)
            throw new IllegalArgumentException("creationDate Cannot be null");
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        if (date == null)
            throw new IllegalArgumentException("lastModificationDate cannot be null");
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public int getEntityCode() {
        return 12;
    }
}
