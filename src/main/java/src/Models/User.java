package src.Models;

import src.Models.Privilages.AccessLevel;

import java.util.UUID;

/**
 * Class User
 */
public class User {

  //
  // Fields
  //

  private String email;
  private String UID;
  private String username;
  private String displayName;
  private AccessLevel accessLevel;
  /**

   * salted password
   *    */

  private String password;
  
  //
  // Constructors
  //
  public User () { };

  public User(String email, String username, String displayName) {
    this.email = email;
    this.username = username;
    this.displayName = displayName;
    this.accessLevel = AccessLevel.NewUser;
    this.UID = UUID.randomUUID().toString();
  }
  
  /**
   * Set the value of email
   * @param newVar the new value of email
   */
  public void setEmail (String newVar) {
    email = newVar;
  }

  /**
   * Get the value of email
   * @return the value of email
   */
  public String getEmail () {
    return email;
  }

  /**
   * Set the value of UID
   * @param newVar the new value of UID
   */
  public void setUID (String newVar) {
    UID = newVar;
  }

  /**
   * Get the value of UID
   * @return the value of UID
   */
  public String getUID () {
    return UID;
  }

  /**
   * Set the value of username
   * @param newVar the new value of username
   */
  public void setUsername (String newVar) {
    username = newVar;
  }

  /**
   * Get the value of username
   * @return the value of username
   */
  public String getUsername () {
    return username;
  }

  /**
   * Set the value of displayName
   * @param newVar the new value of displayName
   */
  public void setDisplayName (String newVar) {
    displayName = newVar;
  }

  /**
   * Get the value of displayName
   * @return the value of displayName
   */
  public String getDisplayName () {
    return displayName;
  }

  /**
   * Set the value of password
   * salted password
   * 
   * @param newVar the new value of password
   */
  public void setPassword (String newVar) {
    password = newVar;
  }

  /**
   * Get the value of password
   * salted password
   * 
   * @return the value of password
   */
  public String getPassword () {
    return password;
  }

  public AccessLevel getAccessLevel() {
    return accessLevel;
  }

  public void setAccessLevel(AccessLevel accessLevel) {
    this.accessLevel = accessLevel;
  }

  //
  // Other methods
  //


  @Override
  public String toString() {
    return "User{" +
            "email='" + email + '\'' +
            ", UID='" + UID + '\'' +
            ", username='" + username + '\'' +
            ", displayName='" + displayName + '\'' +
            ", accessLevel=" + accessLevel + '\n' +
            '}';
  }
}
