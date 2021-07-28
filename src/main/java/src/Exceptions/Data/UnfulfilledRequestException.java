package src.Exceptions.Data;

public class UnfulfilledRequestException extends Exception{
    public UnfulfilledRequestException() {
        super("Request could not be completed. ");
    }
}
