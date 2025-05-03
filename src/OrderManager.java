import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderManager {
    private Queue<Order> orderQueue;
    private List<Order> orders;
    private BookManager bookManager;

    public OrderManager(BookManager bookManager) {
        this.orderQueue = new LinkedBlockingQueue<>();
        this.orders = new ArrayList<>();
        this.bookManager = bookManager; 
    }

    public Order createOrder(String customerName, String shippingAddress) {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        Order newOrder = new Order(orderId, customerName, shippingAddress);
        orderQueue.add(newOrder);
        return newOrder;
    }

    public void addBookToOrder(Order order, String bookId, int quantity) {
        Book book = bookManager.findBookById(bookId);
        if (book != null) {
            order.addBook(book, quantity);
            System.out.println("Book added to order successfully!");
        } else {
            System.out.println("Book not found with ID: " + bookId);
        }
    }

    public Order findOrderById(String orderId) {
        // Search in queue
        for (Order order : orderQueue) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        // Search in completed orders
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public void displayOrderQueue() {
        if (orderQueue.isEmpty()) {
            System.out.println("No orders in queue.");
            return;
        }

        System.out.println("\n===== ORDER QUEUE =====");
        int position = 1;
        for (Order order : orderQueue) {
            System.out.println("\nPosition in queue: " + position);
            order.displayOrder();
            System.out.println("-----------------------");
            position++;
        }
    }
}
