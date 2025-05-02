import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static BookManager bookManager = new BookManager();

    public static void main(String[] args) throws Exception {
        boolean running = true;

        while (running) {
            clearScreen();
            displayMainMenu();
            int choice = getchoice();

            switch (choice) {
                case 1:
                    bookSubMenu();
                    break;
                case 2:
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n===== MENU Online Bookstore =====\n");
        System.out.println("1. Books Management");
        System.out.println("2. Order Management");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayBookSubMenu() {
        System.out.println("\n===== Books Management =====\n");
        System.out.println("1. View All Books");
        System.out.println("2. Search books");
        System.out.println("3. Sort books");
        System.out.println("4. Add to cart");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private static void bookSubMenu() {
        boolean backToMain = false;

        while (!backToMain) {
            clearScreen();
            displayBookSubMenu();
            int choice = getchoice();

            switch (choice) {
                case 1:
                    clearScreen();
                    bookManager.displayAllBooks();

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 2:
                    clearScreen();
                    bookManager.displayAllBooks();

                    System.out.print("\n\nEnter a keyword to search for: ");
                    String keyword = scanner.nextLine();

                    List<Book> result = bookManager.searchBooks(keyword);

                    if (result.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        clearScreen();
                        System.out.println("\n\n========== Search Results ==========");
                        for (Book book : result) {
                            book.displayInfo();
                        }
                    }

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 3:
                    clearScreen();
                    System.out
                            .print("1. Sort by price (ascending)\n2. Sort by price (descending)\nEnter your choice: ");
                    int option = getchoice();

                    List<Book> sortedBooks = bookManager.sortBooksByPrice(option);

                    clearScreen();
                    System.out.println("\n\n========== Sorted Books ==========");
                    for (Book book : sortedBooks) {
                        book.displayInfo();
                    }

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 4:
                    break;
                case 0:
                    backToMain = true;
            }
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static int getchoice() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}
