package example;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (! (entity instanceof Human)) {
            throw new InvalidEntityException("Invalid entity");
        }

        Human human = (Human) entity;

        if (human.age < 0)
            throw new InvalidEntityException("age cannot be negative");
        if (human.name == null)
            throw new InvalidEntityException("name cannot be null");
    }
}