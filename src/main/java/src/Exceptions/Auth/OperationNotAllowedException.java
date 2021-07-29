package src.Exceptions.Auth;

public class OperationNotAllowedException extends Exception {
    public OperationNotAllowedException() {
        super("Your token can not complete that operation");
    }
}
