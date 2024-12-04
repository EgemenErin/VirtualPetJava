package model;

import javax.persistence.*;

@Entity
@Table(name = "dogs")
public class Dog extends Pet {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof!");
    }
}