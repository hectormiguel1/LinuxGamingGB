package src.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Exceptions.Auth.InvalidTokenException;
import src.Exceptions.Auth.OperationNotAllowedException;
import src.Exceptions.Auth.TokenNotFoundException;
import src.Exceptions.Data.GameAlreadyExistsException;
import src.Exceptions.Data.GameNotFoundException;
import src.Exceptions.Data.UnfulfilledRequestException;
import src.Models.AuthToken;
import src.Models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.Privilages.RequestType;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class GameService
 */
@Service
public class GameService {

  private final static Logger logger = LoggerFactory.getLogger(GameService.class);

  private StorageService storageService;
  private TokenService tokenService;

  //
  // Constructors
  //
  public GameService () { }

  @Autowired
  public GameService(StorageService storageService, TokenService tokenService) {
    this.storageService = storageService;
    this.tokenService = tokenService;
    logger.info("Game Service: UP");
  }

  /**
   * Function verifies that the request token is valid, if so, handle request to db, otherwise return false.
   * @param newGame New game to be added.
   * @param token Token sed to authenticate the request.
   * @return Boolean True if the request was completed, otherwise exceptions are thrown.
   * @throws TokenNotFoundException When authentication token is not valid or not found.
   * @throws ExecutionException When connection to database fails.
   * @throws InterruptedException When the connection to database is interrupted.
   * @throws GameAlreadyExistsException When the game is already present in the database.
   * @throws OperationNotAllowedException When the passed token is not allowed to make this request.
   * @throws InvalidTokenException When the token is no longer valid.
   */
  public Boolean newGame(Game newGame, AuthToken token) throws TokenNotFoundException,
          ExecutionException, InterruptedException, GameAlreadyExistsException, InvalidTokenException,
          OperationNotAllowedException {
    if(tokenService.validateToken(token, RequestType.Post)) {
      return storageService.addGame(newGame);
    }

    throw new InvalidTokenException(token);
  }

  /**
   * Attempt to query database for game who's name contains the searchString.
   * @param searchString String to query for.
   * @param token Request Token.
   * @return List<Game> List containing games who's name match the search query.
   * @throws TokenNotFoundException When the token is not found in db or is expired.
   * @throws ExecutionException When connection to the database fails.
   * @throws InterruptedException When connection to database is interrupted.
   * @throws InvalidTokenException When the token is not valid.
   * @throws OperationNotAllowedException When the token does not have the rights to make this request.
   */
  public List<Game> searchGame(String searchString, AuthToken token) throws TokenNotFoundException, ExecutionException,
          InterruptedException, OperationNotAllowedException, InvalidTokenException {
    if(tokenService.validateToken(token, RequestType.Read)) {
      return storageService.queryGames(searchString);
    }
    else throw new InvalidTokenException(token);
  }

  /**
   * Attempts to locate a game by UID.
   * @param gameUID UID of game to locate.
   * @param token Token used to authenticate request.
   * @return Game if request is valid and game is found, exceptions are thrown otherwise.
   * @throws InvalidTokenException When the token is not valid
   * @throws TokenNotFoundException When the token is not found in the server, or is not valid.
   * @throws ExecutionException When connection to database fails.
   * @throws InterruptedException When the connection to the database is interrupted.
   * @throws GameNotFoundException When a game with the request UID is not located.
   * @throws OperationNotAllowedException  When the request token does not have the rights to do the request.
   */
  public Game getGame(String gameUID, AuthToken token) throws InvalidTokenException, TokenNotFoundException,
          ExecutionException, InterruptedException, GameNotFoundException, OperationNotAllowedException {
   if(tokenService.validateToken(token, RequestType.Read)) {
     return storageService.getGame(gameUID);
   }

   throw new InvalidTokenException(token);
  }


}
