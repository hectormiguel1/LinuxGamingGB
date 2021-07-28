package src.Exceptions.Data;

import src.Models.Game;

public class GameAlreadyExistsException extends Exception{
    public GameAlreadyExistsException(Game game) {
        super("Game: " + game.getUID().toString() + " already exits");
    }
}
