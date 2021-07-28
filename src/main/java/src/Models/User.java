package src.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import src.Models.Privilages.AccessLevel;

import java.util.UUID;

/**
 * Class User
 */
public class User {

  private String email;
  private String displayName;
  @JsonIgnore
  private String UID;
  @JsonIgnore
  private AccessLevel accessLevel;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private String salt;
  
  //
  // Constructors
  //
  public User () {
    this.UID = UUID.randomUUID().toString();
    this.accessLevel = AccessLevel.NewUser;
  };

  public User(String email, String displayName, String password) {
    this();
    this.email = email;
    this.displayName = displayName;
    this.password = password;
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

  public String getUID() {
    return UID;
  }

  public void setUID(String UID) {
    this.UID = UID;
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
  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  @Override
  public String toString() {
    return "User{" +
            "email='" + email + '\'' +
            ", UID='" + UID + '\'' +
            ", displayName='" + displayName + '\'' +
            ", accessLevel=" + accessLevel + '\n' +
            '}';
  }
}
