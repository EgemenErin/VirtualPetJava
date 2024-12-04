package model;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "pets")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fields
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hunger")
    private int hunger;

    @Column(name = "happiness")
    private int happiness;

    @Column(name = "health")
    private int health;

    // Many-to-One relationship with Owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // Constructors

    // Default constructor required by JPA
    public Pet() {
    }

    public Pet(String name) {
        this.name = name;
        this.hunger = 50;
        this.happiness = 50;
        this.health = 100;
    }

    // Methods

    public void feed() {
        hunger = Math.max(hunger - 10, 0);
        health = Math.min(health + 5, 100);
        System.out.println(name + " has been fed.");
    }

    public void play() {
        happiness = Math.min(happiness + 10, 100);
        hunger = Math.min(hunger + 5, 100);
        health = Math.max(health - 5, 0);
        System.out.println(name + " enjoyed playing!");
    }

    public void checkStatus() {
        System.out.println("\n--- Pet Status ---");
        System.out.println("Name      : " + name);
        System.out.println("Hunger    : " + hunger + "/100");
        System.out.println("Happiness : " + happiness + "/100");
        System.out.println("Health    : " + health + "/100");
        System.out.println("-------------------");
    }

    // Abstract method to be implemented by subclasses
    public abstract void makeSound();

    // Getters and Setters

    /**
     * Gets the pet's ID.
     * @return the pet's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the pet's name.
     * @return the pet's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     * @param name the name to set for the pet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the pet's hunger level.
     * @return the hunger level.
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Sets the pet's hunger level.
     * @param hunger the hunger level to set.
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * Gets the pet's happiness level.
     * @return the happiness level.
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Sets the pet's happiness level.
     * @param happiness the happiness level to set.
     */
    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    /**
     * Gets the pet's health level.
     * @return the health level.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the pet's health level.
     * @param health the health level to set.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the owner of the pet.
     * @return the owner.
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the pet.
     * @param owner the owner to set.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    // Optional: Override toString() for debugging

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hunger=" + hunger +
                ", happiness=" + happiness +
                ", health=" + health +
                '}';
    }
}