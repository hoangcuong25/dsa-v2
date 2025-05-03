import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerName;
    private String shippingAddress;
    private List<Book> books;
    private List<Integer> quantities;
    private String orderDate;
    private String status;
    private double totalPrice;

    public Order(String orderId, String customerName, String shippingAddress) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.books = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = "Pending";
        this.totalPrice = 0.0;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void addBook(Book book, int quantity) {
        books.add(book);
        quantities.add(quantity);
        totalPrice += book.getPrice() * quantity;
    }

    public void sortBooksByTitle() {
        // Insertion Sort
        for (int i = 1; i < books.size(); i++) {
            Book key = books.get(i);
            int keyQuantity = quantities.get(i);
            int j = i - 1;

            while (j >= 0 && books.get(j).getTitle().compareTo(key.getTitle()) > 0) {
                books.set(j + 1, books.get(j));
                quantities.set(j + 1, quantities.get(j));
                j--;
            }
            books.set(j + 1, key);
            quantities.set(j + 1, keyQuantity);
        }
    }

    public void displayOrder() {
        System.out.println("\n===== ORDER DETAILS =====");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Shipping Address: " + shippingAddress);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("\nBooks in order:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            int quantity = quantities.get(i);
            System.out.println("\nBook " + (i + 1) + ":");
            book.displayInfo();
            System.out.println("Quantity: " + quantity);
            System.out.println("Subtotal: $" + String.format("%.2f", book.getPrice() * quantity));
        }
        System.out.println("\nTotal Price: $" + String.format("%.2f", totalPrice));
        System.out.println("=======================");
    }
}
