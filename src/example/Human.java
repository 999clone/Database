package example;

import db.*;

public class Human extends Entity {
    public String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public Human clone() {
        Human clonedHuman = (Human) super.clone();
        return clonedHuman;
    }
}