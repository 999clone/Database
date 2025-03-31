package db;

import db.exception.EntityNotFoundException;
import example.HumanValidator;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    static int UUID = 1000;

    public static void add(Entity e) {
        e.id = UUID++;
        entities.add(e.clone());
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);
    }

    public static Entity get(int id) throws EntityNotFoundException {
        boolean found = false;
        for (Entity e : entities) {
            if (e.id == id) {
                return e.clone();
            }
        }
        throw new EntityNotFoundException(id);
    }


    public static void delete(int id) throws EntityNotFoundException {
        boolean found = false;
        for (Entity e : entities) {
            if (e.id == id) {
                entities.remove(e);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new EntityNotFoundException(id);
        }
    }

    public static void update(Entity e) throws EntityNotFoundException {
        boolean found = false;

        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);

        for (int i = 0; i < entities.size(); i++) {
            if (e.id == entities.get(i).id) {
                entities.set(i, e.clone());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new EntityNotFoundException(e.id);
        }
    }
    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsValue(entityCode)) {
            throw new IllegalArgumentException("Entity with code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }
}