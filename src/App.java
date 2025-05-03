import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static BookManager bookManager = new BookManager();
    private static OrderManager orderManager = new OrderManager(bookManager);
    private static Order currentOrder = null;

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
                    orderSubMenu();
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

    private static void displayOrderSubMenu() {
        System.out.println("\n===== ORDER MANAGEMENT =====");
        System.out.println("1. View Order Queue");
        System.out.println("2. Search Orders");
        System.out.println("0. Back to main menu");
        System.out.println("=====================");
        System.out.print("Enter your choice: ");
    }

    private static void addToCartMenu() {
        clearScreen();
        System.out.println("\n===== ADD TO CART =====");

        if (currentOrder == null) {
            System.out.print("Enter your name: ");
            String customerName = scanner.nextLine();
            System.out.print("Enter shipping address: ");
            String shippingAddress = scanner.nextLine();
            currentOrder = orderManager.createOrder(customerName, shippingAddress);
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
                    currentOrder.displayOrder();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                break;
            case 3:
                if (currentOrder != null) {
                    currentOrder.setStatus("Completed");
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
        System.out.println("1. Search by Order ID");
        System.out.println("2. Search by Customer Name");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");

        int choice = getchoice();
        switch (choice) {
            case 1:
                System.out.print("Enter Order ID: ");
                String orderId = scanner.nextLine();
                Order order = orderManager.findOrderById(orderId);
                if (order != null) {
                    order.displayOrder();
                } else {
                    System.out.println("Order not found.");
                }
                break;
            case 2:
                System.out.print("Enter Customer Name: ");
                String customerName = scanner.nextLine();
                List<Order> orders = orderManager.searchOrdersByCustomerName(customerName);
                if (!orders.isEmpty()) {
                    System.out.println("\nFound " + orders.size() + " orders:");
                    for (Order o : orders) {
                        o.displayOrder();
                        System.out.println("-----------------------");
                    }
                } else {
                    System.out.println("No orders found for this customer.");
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
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
