package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One owner can have multiple pets
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();

    // Constructors

    // Default constructor required by JPA
    public Owner() {
    }

    public Owner(String name) {
        this.name = name;
    }

    // Getters and Setters

    /**
     * Gets the owner's ID.
     * @return the owner's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the owner's name.
     * @return the owner's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the owner's name.
     * @param name the name to set for the owner.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of pets owned by the owner.
     * @return the list of pets.
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets for the owner.
     * @param pets the list of pets to set.
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    // Utility Methods

    /**
     * Adds a pet to the owner's list of pets and sets the owner in the pet.
     * @param pet the pet to add.
     */
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }

    /**
     * Removes a pet from the owner's list of pets and unsets the owner in the pet.
     * @param pet the pet to remove.
     */
    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setOwner(null);
    }

    // Optional: Override toString() for better debugging

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pets=" + pets +
                '}';
    }
}