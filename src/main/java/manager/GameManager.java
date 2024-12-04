package manager;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import dao.GenericDAO;


import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 * Manages game operations, including interactions with the database.
 */
public class GameManager {
    private Pet pet;
    private Owner owner;
    private GenericDAO<Owner> ownerDAO;
    private GenericDAO<Pet> petDAO;

    /**
     * Constructor initializes DAOs for Owner and Pet.
     */
    public GameManager() {
        ownerDAO = new GenericDAO<>(Owner.class);
        petDAO = new GenericDAO<>(Pet.class);
    }

    /**
     * Allows the user to adopt a pet.
     *
     * @param scanner the Scanner object for user input.
     */
    public void adoptPet(Scanner scanner) {
        System.out.print("Enter your name: ");
        String ownerName = scanner.nextLine();

        // Create a new owner
        owner = new Owner(ownerName);

        System.out.print("Enter pet type (Dog/Cat/Dragon): ");
        String type = scanner.nextLine();
        System.out.print("Enter pet name: ");
        String name = scanner.nextLine();

        switch (type.toLowerCase()) {
            case "dog":
                pet = new Dog(name);
                break;
            case "cat":
                pet = new Cat(name);
                break;
            case "dragon":
                pet = new Dragon(name);
                break;
            default:
                System.out.println("Invalid pet type.");
                return;
        }

        owner.addPet(pet);

        // Save owner and pet to the database
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(owner); // Use persist() for new entities
            tx.commit();
            System.out.println("Pet adopted successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Feeds the pet.
     */
    public void feedPet() {
        if (checkPetExists()) {
            pet.feed();

            // Update pet in the database
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                pet = (Pet) session.merge(pet); // Use merge() to update
                tx.commit();
                System.out.println("Pet fed successfully!");
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }

    /**
     * Plays with the pet.
     */
    public void playWithPet() {
        if (checkPetExists()) {
            pet.play();

            // Update pet in the database
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                pet = (Pet) session.merge(pet); // Use merge() to update
                tx.commit();
                System.out.println("Played with pet successfully!");
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }

    /**
     * Checks the pet's status.
     */
    public void checkPetStatus() {
        if (checkPetExists()) {
            pet.checkStatus();
        }
    }

    /**
     * Lists all pets owned by the user.
     */
    public void listPets() {
        if (owner == null) {
            System.out.println("You have not adopted any pets yet.");
            return;
        }

        // Reload owner from database to get the latest data
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            owner = session.get(Owner.class, owner.getId());
            List<Pet> pets = owner.getPets();

            if (pets.isEmpty()) {
                System.out.println("You have no pets.");
                return;
            }

            System.out.println("\n--- Your Pets ---");
            for (Pet p : pets) {
                System.out.println("Name: " + p.getName() + ", Type: " + p.getClass().getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Updates a pet's name.
     *
     * @param scanner the Scanner object for user input.
     */
    public void updatePetName(Scanner scanner) {
        if (owner == null || owner.getPets().isEmpty()) {
            System.out.println("You have no pets to update.");
            return;
        }

        System.out.print("Enter current pet name: ");
        String currentName = scanner.nextLine();

        Pet petToUpdate = findPetByName(currentName);

        if (petToUpdate != null) {
            System.out.print("Enter new pet name: ");
            String newName = scanner.nextLine();
            petToUpdate.setName(newName);

            // Update pet in the database
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                petToUpdate = (Pet) session.merge(petToUpdate); // Use merge() to update
                tx.commit();
                System.out.println("Pet name updated successfully!");
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        } else {
            System.out.println("Pet not found.");
        }
    }

    /**
     * Deletes a pet.
     *
     * @param scanner the Scanner object for user input.
     */
    public void deletePet(Scanner scanner) {
        if (owner == null || owner.getPets().isEmpty()) {
            System.out.println("You have no pets to delete.");
            return;
        }

        System.out.print("Enter pet name to delete: ");
        String petName = scanner.nextLine();

        Pet petToDelete = findPetByName(petName);

        if (petToDelete != null) {
            owner.getPets().remove(petToDelete);

            // Delete pet from the database
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                // Ensure petToDelete is managed
                if (!session.contains(petToDelete)) {
                    petToDelete = (Pet) session.merge(petToDelete);
                }

                session.remove(petToDelete); // Use remove() in Hibernate 6
                tx.commit();
                System.out.println("Pet deleted successfully!");
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        } else {
            System.out.println("Pet not found.");
        }
    }

    /**
     * Saves the game state to the database.
     */
    public void saveGame() {
        if (checkPetExists()) {
            // Save to database (state is already being persisted)
            System.out.println("Game saved successfully to the database.");
        }
    }

    /**
     * Loads the game state from the database.
     */
    public void loadGame() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<model.Owner> query = session.createQuery("from model.Owner", model.Owner.class);
            List<Owner> owners = query.getResultList();

            if (owners.isEmpty()) {
                System.out.println("No saved game found.");
                return;
            }

            owner = owners.get(0); // Get the first owner
            if (!owner.getPets().isEmpty()) {
                pet = owner.getPets().get(0); // Load the first pet
                System.out.println("Game loaded successfully. Welcome back, " + pet.getName() + "!");
            } else {
                System.out.println("No pets found for the owner.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



    /**
     * Helper method to check if a pet has been adopted.
     *
     * @return true if a pet exists, false otherwise.
     */
    private boolean checkPetExists() {
        if (pet == null) {
            System.out.println("You need to adopt a pet first.");
            return false;
        }
        return true;
    }

    /**
     * Finds a pet by name from the owner's list of pets.
     *
     * @param name the name of the pet to find.
     * @return the Pet object if found, null otherwise.
     */
    private Pet findPetByName(String name) {
        for (Pet p : owner.getPets()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}