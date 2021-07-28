package src.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Exceptions.Auth.UserAlreadyExistsException;
import src.Exceptions.Auth.EmailPasswordMismatchException;
import src.Exceptions.Auth.UserNotFoundException;
import src.Models.AuthToken;
import src.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * Class UserService
 */
@Service
public class UserService {
  private final StorageService storageService;
  private final TokenService tokenService;
  private final HashingService hashingService;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);
  
  @Autowired
  public UserService(StorageService storageService, TokenService tokenService, HashingService hashingService) {
    this.storageService = storageService;
    this.tokenService = tokenService;
    this.hashingService = hashingService;
    logger.info("User Service: UP");
  }

  /**
   * Registers a new newUser, then generates a new token for the new newUser.
   * @param newUser: User to be registered to the service.
   * @return AuthToken for the newly registered service,
   * @throws UserAlreadyExistsException: Exception is thrown when attempting to register a newUser which already exists.
   * @throws ExecutionException: Thrown when connecting to the db failed.
   * @throws InterruptedException: When the connecting to the db was interrupted.
   */
  public AuthToken registerNewUser(User newUser) throws UserAlreadyExistsException, ExecutionException, InterruptedException {
    newUser.setSalt(new String(hashingService.getNextSalt()));
    newUser.setPassword(new String(hashingService.hashPassword(newUser.getPassword().toCharArray(), newUser.getSalt().getBytes(StandardCharsets.UTF_8))));
    User registeredUser = storageService.createNewUser(newUser);
    logger.info("Registering new User with email: " + newUser.getEmail());
    return tokenService.generateNewToken(registeredUser);
  }

  /**
   * Attempts to login with the newUser with provided email and password.
   * @param email User email.
   * @param password User password (Slated)
   * @return AuthToken for the logged in User (if successfully logged in)
   * @throws EmailPasswordMismatchException When the provided email and password do not match was it stored on
   * server or no results are yield from that combination.
   * @throws ExecutionException Thrown when the connection to the db fails.
   * @throws InterruptedException Thrown when the connection to database is interrupted.
   * @throws UserNotFoundException When no user could be located with provided email.
   */
  public AuthToken loginUser(String email, String password) throws EmailPasswordMismatchException,
          ExecutionException, InterruptedException, UserNotFoundException {
    logger.info("Attempting to authenticate newUser with email: " + email);
    //Attempt to get newUser from the storage service.
    User loggedUser = storageService.getUser(email, password);
    //Create an auth token for the newUser.
    return tokenService.generateNewToken(loggedUser);

  }


}
