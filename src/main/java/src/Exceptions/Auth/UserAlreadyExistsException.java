package src.Exceptions.Auth;

import src.Models.User;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(User user) {
        super("User with email: " + user.getEmail() + " is already registered in the server");
    }
}
