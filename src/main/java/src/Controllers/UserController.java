package src.Controllers;

import src.Models.User;
import src.Services.UserService;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class UserController
 */
@RestController
public class UserController {

  //
  // Fields
  //

  private UserService userService;
  
  //
  // Constructors
  //
  public UserController () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of userService
   * @param newVar the new value of userService
   */
  public void setUserService (UserService newVar) {
    userService = newVar;
  }

  /**
   * Get the value of userService
   * @return the value of userService
   */
  public UserService getUserService () {
    return userService;
  }

  //
  // Other methods
  //

  /**
   * @return       Boolean
   * @param        email
   * @param        password Password is the salted representation.
   */
  public Boolean login(String email, String password)
  {
    return null;
  }


  /**
   * @return       Boolean
   * @param        newUser
   * @param        password this will be the salted version of the password.
   */
  public Boolean register(User newUser, String password)
  {
    return null;
  }


}
