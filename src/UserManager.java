import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private User currentUser;

    public UserManager() {
        users = new ArrayList<>();
        // Add only one hardcoded user
        users.add(new User("cuong", "123123123", "hoang cuong", "ha noi"));
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }
}