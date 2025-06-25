/**
 * Main class to start the Student Management System application
 * Entry point for the CLI-based CRUD system
 *
 * @author Your Name
 * @version 1.0
 */
public class Main {

    /**
     * Main method - entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Create and start the menu handler
            MenuHandler menuHandler = new MenuHandler();
            menuHandler.start();

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.err.println("Please restart the application.");
            e.printStackTrace();
        }
    }
}