package src.Exceptions.Auth;

public class UsernamePasswordMismatchException extends Exception {
    public UsernamePasswordMismatchException() {
        super("Provided username and password is not valid");
    }
}
