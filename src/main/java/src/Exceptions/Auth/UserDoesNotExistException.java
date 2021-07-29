package src.Exceptions.Auth;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(String uid) {
        super("User with UID: " + uid + " does not exist");
    }
}
