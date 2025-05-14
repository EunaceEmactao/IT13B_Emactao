
package midterm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RestaurantOrderingSystem {
      public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n Welcome! Please choose an option:");
            System.out.println("[1] Create an Account");
            System.out.println("[2] Log In");

            int choice = -1;
            if (in.hasNextInt()) {
                choice = in.nextInt();
                in.nextLine(); 
            }

            switch (choice) {
                case 1:
                    CreateAccount(in);
                    break;
                case 2:
                    LogIn(in);
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }

        in.close();
    }

    public static void CreateAccount(Scanner in) {
        int key = 3;
        System.out.println("🔒 Create New Account");

        System.out.print("Enter new username: ");
        String createUsername = in.nextLine();

        System.out.print("Enter new password: ");
        String createPassword = in.nextLine();

         String encryptedPassword = passwordEncryption(createPassword, key);
        
        try (FileWriter myWriter = new FileWriter("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\users.txt", true)) {
            myWriter.write(createUsername + "," + encryptedPassword + "\n");
            System.out.println("✅ Account created successfully!");
        } catch (IOException e) {
            System.out.println("❌ An error occurred while saving the account.");
            e.printStackTrace();
        }
    }

    public static String passwordEncryption(String password, int key) {
        char[] chars = password.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] += key;
        }
        return new String(chars);
    }

    public static String passwordDecryption(String encryptedPassword, int key) {
        char[] chars = encryptedPassword.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] -= key;
        }
        return new String(chars);
    }

    public static void LogIn(Scanner in) {
        int key = 3;

        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();

        if (login(username, password, key)) {
            System.out.println("✅ Login successful!");
          RestaurantOrder();  
        } else {
            System.out.println("❌ Login failed. Username or password may be incorrect.");
        }
    }

    private static boolean login(String username, String password, int key) {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    String decryptedPassword = passwordDecryption(parts[1], key);
                    if (password.equals(decryptedPassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }
        return false;
    }
    
    public static void RestaurantOrder() {
        Scanner in = new Scanner(System.in);

        String[] menuItems = {"Tiramisu Cake", "Strawberry Cake", "Affogato"};
        int[] prices = {150, 120, 80}; 
        int[] quantities = new int[menuItems.length];

        int choice;

        do {
            System.out.println("\n--- Restaurant Menu ---");
            for (int i = 0; i < menuItems.length; i++) {
                System.out.println((i + 1) + ". " + menuItems[i] + " (₱" + prices[i] + ")");
            }
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1-4): ");
            choice = in.nextInt();

            if (choice >= 1 && choice <= 3) {
                System.out.print("Enter quantity for " + menuItems[choice - 1] + ": ");
                int qty = in.nextInt();
                quantities[choice - 1] += qty;
            } else if (choice != 4) {
                System.out.println("Invalid choice. Please try again.");
            } 

        } while (choice != 4);
    System.out.println("Exiting menu...");
    
        System.out.println("\n--- Order Summary ---");
        int total = 0;
        for (int i = 0; i < menuItems.length; i++) {
            if (quantities[i] > 0) {
                int itemTotal = quantities[i] * prices[i];
                System.out.println(menuItems[i] + " x " + quantities[i] + " = ₱" + itemTotal);
                total += itemTotal;
            }
        }
        System.out.println("Total Bill: ₱ " + total);
    }
}

