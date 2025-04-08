package db;

import db.exception.EntityNotFoundException;
import example.HumanValidator;
import todo.entity.Step;
import todo.entity.Task;
import todo.serialazer.StepSerialazer;
import todo.serialazer.TaskSerialazer;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private static HashMap<Integer, Serializer> serializers = new HashMap<>();
    public static int UUID = 1000;

    public static void add(Entity e) {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null)
            validator.validate(e);

        e.id = UUID;
        UUID++;

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
            throw new IllegalArgumentException("Validator with code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }

    public static void registerSerializer(int entityCode, Serializer serializer) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Validator with code " + entityCode + " already exists");
        }
        serializers.put(entityCode, serializer);
    }

    public static void save() {
        try {
            String ss = "";
            BufferedWriter writer = new BufferedWriter(new FileWriter("db.txt"));
            for (Entity e : entities) {
                Serializer serializer = serializers.get(e.getEntityCode());
                if (e instanceof Task) {
                    TaskSerialazer taskSerialazer = new TaskSerialazer();
                    ss = taskSerialazer.serialize(e);
                }
                if (e instanceof Step) {
                    StepSerialazer stepSerialazer = new StepSerialazer();
                    ss = stepSerialazer.serialize(e);
                }
                    writer.write(ss);
                    writer.newLine();
                    System.out.println("Saving data...");
                }

            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void load() {
        try {

            BufferedReader reader = new BufferedReader(new FileReader("db.txt"));
            String line;
            while ((line = reader.readLine() )!= null){
                String[] parts = line.split(";");
                int entityCode = Integer.parseInt(parts[0]);
                Serializer serializer = serializers.get(entityCode);
                System.out.println("Loading data...");
                if (serializer != null) {
                    Entity e = serializer.deserialize(line);
                    System.out.println("Loading Database...");
                    Database.add(e);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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