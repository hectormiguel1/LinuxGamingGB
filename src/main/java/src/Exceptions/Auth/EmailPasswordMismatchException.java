package src.Exceptions.Auth;

public class EmailPasswordMismatchException extends Exception {
    public EmailPasswordMismatchException() {
        super("Provided username and password is not valid");
    }
}
