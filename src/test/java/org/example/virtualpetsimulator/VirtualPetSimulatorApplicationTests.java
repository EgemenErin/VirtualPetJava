package org.example.virtualpetsimulator;

import model.Cat;
import model.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class VirtualPetSimulatorApplicationTests {

    @Test
    public void testFeed() {
        Dog dog = new Dog("Buddy");
        int initialHunger = dog.getHunger();
        dog.feed();
        assertEquals(initialHunger - 10, dog.getHunger());
    }

    @Test
    public void testPlay() {
        Cat cat = new Cat("Whiskers");
        int initialHappiness = cat.getHappiness();
        int initialHunger = cat.getHunger();
        cat.play();
        assertEquals(initialHappiness + 10, cat.getHappiness());
        assertEquals(initialHunger + 5, cat.getHunger());
    }
}
