package src.Models;

import java.util.List;
import java.util.UUID;

/**
 * Class Game
 */
public class Game {

  //
  // Fields
  //

  private int currentRating;
  private long numOfReports;
  private List<String> tags;
  private UUID UID;
  private boolean isNative;
  private String name;
  
  //
  // Constructors
  //
  public Game () {
    this.UID = UUID.randomUUID();
  };

  public Game(int currentRating, long numOfReports, List<String> tags, boolean isNate, String name) {
    this();
    this.currentRating = currentRating;
    this.numOfReports = numOfReports;
    this.tags = tags;
    this.isNative = isNate;
    this.name = name;
  }
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of currentRating
   * @param newVar the new value of currentRating
   */
  public void setCurrentRating (int newVar) {
    currentRating = newVar;
  }

  /**
   * Get the value of currentRating
   * @return the value of currentRating
   */
  public int getCurrentRating () {
    return currentRating;
  }

  /**
   * Set the value of numOfReports
   * @param newVar the new value of numOfReports
   */
  public void setNumOfReports (long newVar) {
    numOfReports = newVar;
  }

  /**
   * Get the value of numOfReports
   * @return the value of numOfReports
   */
  public long getNumOfReports () {
    return numOfReports;
  }

  /**
   * Set the value of tags
   * @param newVar the new value of tags
   */
  public void setTags (List<String> newVar) {
    tags = newVar;
  }

  /**
   * Get the value of tags
   * @return the value of tags
   */
  public List<String> getTags () {
    return tags;
  }

  /**
   * Set the value of UID
   * @param newVar the new value of UID
   */
  public void setUID (UUID newVar) {
    UID = newVar;
  }

  /**
   * Get the value of UID
   * @return the value of UID
   */
  public UUID getUID () {
    return UID;
  }

  /**
   * Set the value of isNative
   * @param newVar the new value of isNative
   */
  public void setIsNative (boolean newVar) {
    isNative = newVar;
  }

  /**
   * Get the value of isNative
   * @return the value of isNative
   */
  public boolean getIsNative () {
    return isNative;
  }

  /**
   * Set the value of name
   * @param newVar the new value of name
   */
  public void setName (String newVar) {
    name = newVar;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  public String getName () {
    return name;
  }

  //
  // Other methods
  //


  @Override
  public String toString() {
    return "Game{" +
            "currentRating=" + currentRating +
            ", numOfReports=" + numOfReports +
            ", tags=" + tags +
            ", UID='" + UID + '\'' +
            ", isNative=" + isNative +
            ", name='" + name + '\'' +
            '}';
  }
}
