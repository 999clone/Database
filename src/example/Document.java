package example;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Document extends Entity implements Trackable {
    private String content;
    private Date creationDate;
    private Date lastModificationDate;

    public Document(String content) {
        this.content = content;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public int getEntityCode() {
        return 0;
    }

    @Override
    public Document clone() {
        Document copy = (Document) super.clone();
        if (this.creationDate != null) {
            copy.creationDate = (Date) this.creationDate.clone();
        }
        if (this.lastModificationDate != null) {
            copy.lastModificationDate = (Date) this.lastModificationDate.clone();
        }
        return copy;
    }
}
