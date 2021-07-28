package src.Exceptions.Auth;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String email) {
        super("User with email: " + email + " not found");

    }
}
