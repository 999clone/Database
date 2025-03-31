package example;

import db.*;

public class Human extends Entity {
    public String name;
    public int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public Human clone() {
        Human clonedHuman = (Human) super.clone();
        return clonedHuman;
    }
}