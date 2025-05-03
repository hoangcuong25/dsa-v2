import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static BookManager bookManager = new BookManager();
    private static OrderManager orderManager = new OrderManager(bookManager);
    private static Order currentOrder = null;
    private static User currentUser = null;
    private static final User DEFAULT_USER = new User("cuong", "123123123", "hoang cuong", "ha noi");

    public static void main(String[] args) throws Exception {
        boolean running = true;

        while (running) {
            if (currentUser == null) {
                if (!loginMenu()) {
                    continue;
                }
            }

            clearScreen();
            displayMainMenu();
            int choice = getchoice();

            switch (choice) {
                case 1:
                    bookSubMenu();
                    break;
                case 2:
                    orderSubMenu();
                    break;
                case 3:
                    currentUser = null;
                    continue;
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

    private static boolean loginMenu() {
        clearScreen();
        System.out.println("\n===== LOGIN MENU =====");
        System.out.println("1. Login");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = getchoice();
        switch (choice) {
            case 1:
                return handleLogin();
            case 0:
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
                return false;
        }
    }

    private static boolean handleLogin() {
        clearScreen();
        System.out.println("\n===== LOGIN =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (username.equals(DEFAULT_USER.getUsername()) && password.equals(DEFAULT_USER.getPassword())) {
            currentUser = DEFAULT_USER;
            System.out.println("\nLogin successful!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return true;
        } else {
            System.out.println("\nInvalid username or password!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return false;
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n===== MENU Online Bookstore =====\n");
        System.out.println("Welcome, " + currentUser.getFullName() + "!");
        System.out.println("\n1. Books Management");
        System.out.println("2. Order Management");
        System.out.println("3. Logout");
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

                    List<Book> sortedBooks;
                    if (option == 1) {
                        sortedBooks = bookManager.sortBooksByPrice();
                    } else if (option == 2) {
                        sortedBooks = bookManager.sortBooksByPrice();
                        // Reverse the list for descending order
                        java.util.Collections.reverse(sortedBooks);
                    } else {
                        System.out.println("Invalid option!");
                        System.out.println("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;
                    }

                    clearScreen();
                    System.out.println("\n\n========== Sorted Books ==========");
                    for (Book book : sortedBooks) {
                        book.displayInfo();
                    }

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 4:
                    addToCartMenu();
                    break;
                case 0:
                    backToMain = true;
                    continue;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayOrderSubMenu() {
        System.out.println("\n===== ORDER MANAGEMENT =====");
        System.out.println("1. View Order Queue");
        System.out.println("2. Search Orders");
        System.out.println("3. View All Orders");
        System.out.println("0. Back to main menu");
        System.out.println("=====================");
        System.out.print("Enter your choice: ");
    }

    private static void orderSubMenu() {
        boolean backToMain = false;

        while (!backToMain) {
            clearScreen();
            displayOrderSubMenu();
            int choice = getchoice();

            switch (choice) {
                case 1:
                    displayOrderQueue();
                    break;
                case 2:
                    searchOrder();
                    break;
                case 3:
                    orderManager.displayAllOrders();
                    break;
                case 0:
                    backToMain = true;
                    continue;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (!backToMain) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private static void addToCartMenu() {
        clearScreen();
        System.out.println("\n===== ADD TO CART =====");

        if (currentOrder == null) {
            currentOrder = orderManager.createOrder(
                    currentUser.getFullName(),
                    currentUser.getAddress());
        }

        System.out.println("\nList of available books:");
        bookManager.displayAllBooks();
        System.out.println("\nEnter book ID to add to cart (or '0' to cancel):");
        String bookId = scanner.nextLine();

        if (bookId.equals("0")) {
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = getchoice();

        Book selectedBook = bookManager.findBookById(bookId);
        if (selectedBook != null) {
            orderManager.addBookToOrder(currentOrder, bookId, quantity);
        } else {
            System.out.println("\nBook not found with ID: " + bookId);
        }

        System.out.println("\n1. Continue shopping");
        System.out.println("2. View cart");
        System.out.println("3. Place order");
        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");

        int choice = getchoice();
        switch (choice) {
            case 1:
                addToCartMenu();
                break;
            case 2:
                if (currentOrder != null) {
                    currentOrder.sortBooksByPrice();
                    currentOrder.displayOrder();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                break;
            case 3:
                if (currentOrder != null) {
                    currentOrder.sortBooksByPrice();
                    currentOrder.displayOrder();
                    System.out.println("\nOrder placed successfully!");
                    currentOrder = null;
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                break;
            case 0:
                if (currentOrder != null) {
                    currentOrder = null;
                    System.out.println("\nOrder cancelled.");
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void displayOrderQueue() {
        orderManager.displayOrderQueue();
    }

    private static void searchOrder() {
        clearScreen();
        System.out.println("\n===== SEARCH ORDERS =====");
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();
        Order order = orderManager.findOrderById(orderId);
        if (order != null) {
            System.out.println("\n1. Display order");
            System.out.println("2. Sort books by price");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            int choice = getchoice();
            switch (choice) {
                case 1:
                    order.displayOrder();
                    break;
                case 2:
                    order.sortBooksByPrice();
                    order.displayOrder();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Order not found.");
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
