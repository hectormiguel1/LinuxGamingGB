package src.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.Exceptions.Auth.UserAlreadyExistsException;
import src.Exceptions.Auth.UsernamePasswordMismatchException;
import src.Models.AuthToken;
import src.Models.LoginRequest;
import src.Models.RegisterRequest;
import src.Models.User;
import src.Services.TokenService;
import src.Services.UserService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController (UserService userService, TokenService tokenService) {
    this.userService = userService;
    logger.info("User Controller: UP");
  };

  /**
   * Function used to authenticate a user.
   * @param loginRequest: Login Request Object, contains email and password(salted)
   * @return Response Entity with Authentication Token is successful, Exceptions are thrown otherwise.
   * @throws UsernamePasswordMismatchException When the provided username and password dont match to what is in server.
   * @throws ExecutionException When connection to database fails.
   * @throws InterruptedException When connection to database is interrupted.
   */
  @PostMapping
  @CrossOrigin
  public ResponseEntity<AuthToken> login(@RequestBody LoginRequest loginRequest)
          throws UsernamePasswordMismatchException, ExecutionException, InterruptedException {
    return new ResponseEntity<>(
         userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword()), HttpStatus.OK
    );
  }


  /**
   * Function handles register requests on /auth/register
   * @param registerRequest Request containing new user to register
   * @return Response Entity containing Authentication Token if successful, Exceptions are thrown otherwise.
   * @throws UserAlreadyExistsException Thrown when attempting to register a user that already exists.
   * @throws ExecutionException Thrown when connection to database fails.
   * @throws InterruptedException Thrown when connection to database is interrupted.
   */
  @PostMapping("/register")
  @CrossOrigin
  public ResponseEntity<AuthToken> register(@RequestBody RegisterRequest registerRequest)
          throws UserAlreadyExistsException, ExecutionException, InterruptedException {
    return new ResponseEntity<>(
            userService.registerNewUser(registerRequest.user()), HttpStatus.OK
    );
  }


}
