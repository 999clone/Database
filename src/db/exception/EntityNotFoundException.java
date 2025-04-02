package db.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super();
    }
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException(int id) {
        super("Cannot find entity with id: " + id);
    }

}