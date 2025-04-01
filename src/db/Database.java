package db;

import db.exception.EntityNotFoundException;
import example.HumanValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    static int UUID = 1000;

    public static void add(Entity e) {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null)
            validator.validate(e);

        e.id = UUID++;
        if (e instanceof Trackable trackable) {
            Date now = new Date();
            trackable.setCreationDate(now);
            trackable.setLastModificationDate(now);
        }
        entities.add(e.clone());
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
        if (validator != null)
            validator.validate(e);


        for (int i = 0; i < entities.size(); i++) {
            if (e.id == entities.get(i).id) {
                if (e instanceof Trackable trackable) {
                    trackable.setLastModificationDate(new Date());
                }
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
    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getEntityCode() == entityCode) {
                result.add(e);
            }
        }
        return result;
    }
}