package src.Exceptions.Data;


public class GameNotFoundException extends Exception{

    public GameNotFoundException(String gameUID) {
        super("Game with UID: " + gameUID + " not found");
    }
}
