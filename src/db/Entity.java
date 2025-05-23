package db;

public abstract class Entity implements Cloneable {
    public int id;

    @Override
    public Entity clone() {
        try {
            return (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
    public abstract int getEntityCode();
}
