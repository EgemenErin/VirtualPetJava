package org.example.virtualpetsimulator.main;
import java.util.Scanner;
import manager.GameManager;

public class VirtualPetSimulatorApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameManager gameManager = new GameManager();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Virtual Pet Simulator ===");
            System.out.println("1. Adopt a Pet");
            System.out.println("2. Feed Pet");
            System.out.println("3. Play with Pet");
            System.out.println("4. Check Pet Status");
            System.out.println("5. Save Game");
            System.out.println("6. Load Game");
            System.out.println("7. List Pets");
            System.out.println("8. Update Pet Name");
            System.out.println("9. Delete Pet");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    gameManager.adoptPet(scanner);
                    break;
                case "2":
                    gameManager.feedPet();
                    break;
                case "3":
                    gameManager.playWithPet();
                    break;
                case "4":
                    gameManager.checkPetStatus();
                    break;
                case "5":
                    gameManager.saveGame();
                    break;
                case "6":
                    gameManager.loadGame();
                    break;
                case "7":
                    gameManager.listPets();
                    break;
                case "8":
                    gameManager.updatePetName(scanner);
                    break;
                case "9":
                    gameManager.deletePet(scanner);
                    break;
                case "10":
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
