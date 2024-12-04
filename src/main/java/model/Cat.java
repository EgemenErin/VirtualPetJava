package model;

import javax.persistence.*;

@Entity
@Table(name = "cats")
public class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Meow!");
    }
}
