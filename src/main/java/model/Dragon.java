package model;

import javax.persistence.*;

@Entity
@Table(name = "cats")
public class Dragon extends Pet {
    public Dragon(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Rawr!");
    }
}