import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private List<Book> books;

    public BookManager() {
        books = new ArrayList<>();
        initializeBooks();
    }

    private void initializeBooks() {
        books.add(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", 12.99, "Fiction", 100));
        books.add(new Book("B002", "To Kill a Mockingbird", "Harper Lee", 14.99, "Fiction", 100));
        books.add(new Book("B003", "1984", "George Orwell", 11.99, "Science Fiction", 100));
        books.add(new Book("B004", "Pride and Prejudice", "Jane Austen", 9.99, "Romance", 100));
        books.add(new Book("B005", "The Hobbit", "J.R.R. Tolkien", 16.99, "Fantasy", 100));
        books.add(new Book("B006", "The Catcher in the Rye", "J.D. Salinger", 13.99, "Fiction", 100));
        books.add(new Book("B007", "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 19.99, "Fantasy", 100));
        books.add(new Book("B008", "The Da Vinci Code", "Dan Brown", 15.99, "Mystery", 100));
        books.add(new Book("B009", "The Alchemist", "Paulo Coelho", 10.99, "Fiction", 100));
        books.add(new Book("B010", "The Hunger Games", "Suzanne Collins", 17.99, "Science Fiction", 100));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void displayAllBooks() {
        System.out.println("\n===== LIST OF BOOKS =====");
        for (Book book : books) {
            book.displayInfo();
        }
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> sortBooksByPrice() {
        List<Book> sortedBooks = new ArrayList<>(books);
        mergeSort(sortedBooks, 0, sortedBooks.size() - 1);
        return sortedBooks;
    }

    public static void mergeSort(List<Book> books, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(books, left, middle);
            mergeSort(books, middle + 1, right);

            merge(books, left, middle, right);
        }
    }

    public static void merge(List<Book> books, int left, int middle, int right) {
        int s1 = middle - left + 1;
        int s2 = right - middle;

        List<Book> leftArray = new ArrayList<>();
        List<Book> rightArray = new ArrayList<>();

        // Copy data to temporary arrays
        for (int i = 0; i < s1; i++) {
            leftArray.add(books.get(left + i));
        }
        for (int j = 0; j < s2; j++) {
            rightArray.add(books.get(middle + 1 + j));
        }

        // Merge the temporary arrays
        int i = 0, j = 0, k = left;
        while (i < s1 && j < s2) {
            if (leftArray.get(i).getPrice() <= rightArray.get(j).getPrice()) {
                books.set(k, leftArray.get(i));
                i++;
            } else {
                books.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray if any
        while (i < s1) {
            books.set(k, leftArray.get(i));
            i++;
            k++;
        }

        // Copy remaining elements of rightArray if any
        while (j < s2) {
            books.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    public Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }
}
