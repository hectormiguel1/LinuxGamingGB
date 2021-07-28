package src.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.UUID;

/**
 * Class Report
 */
public class Report {
    private String gameUID;
    private String systemConfiguration;
    private String wineVer;
    private List<String> tricks;
    private Long totalPlayTimeHours;
    private int overallRating;
    private String userUID;
    private String comments;
    @JsonIgnore
    private final UUID UID;

    public Report() {
        this.UID = UUID.randomUUID();
    }

    /**
     * Creates a new report with provided details.
     *
     * @param gameUID:             UID of the game the report belongs to.
     * @param systemConfiguration: Configuration of the system where the game ran.
     * @param wineVer:             Version of wine used to run the game.
     * @param tricks:              List of tricks used to get the game running.
     * @param playTime:            Total play time achieved during testing.
     * @param overallRating:       Rating the
     * @param userUID
     * @param comments
     */
    public Report(String gameUID, String systemConfiguration, String wineVer, List<String> tricks, Long playTime,
                  int overallRating, String userUID, String comments) {
        this.gameUID = gameUID;
        this.systemConfiguration = systemConfiguration;
        this.wineVer = wineVer;
        this.tricks = tricks;
        this.totalPlayTimeHours = playTime;
        this.overallRating = overallRating;
        this.userUID = userUID;
        this.comments = comments;
        this.UID = UUID.randomUUID();
    }

    /**
     * Set the value of gameUID
     *
     * @param newVar the new value of gameUID
     */
    public void setGameUID(String newVar) {
        gameUID = newVar;
    }

    /**
     * Get the value of gameUID
     *
     * @return the value of gameUID
     */
    public String getGameUID() {
        return gameUID;
    }

    /**
     * Set the value of systemConfiguration
     *
     * @param newVar the new value of systemConfiguration
     */
    public void setSystemConfiguration(String newVar) {
        systemConfiguration = newVar;
    }

    /**
     * Get the value of systemConfiguration
     *
     * @return the value of systemConfiguration
     */
    public String getSystemConfiguration() {
        return systemConfiguration;
    }

    /**
     * Set the value of wineVer
     *
     * @param newVar the new value of wineVer
     */
    public void setWineVer(String newVar) {
        wineVer = newVar;
    }

    /**
     * Get the value of wineVer
     *
     * @return the value of wineVer
     */
    public String getWineVer() {
        return wineVer;
    }

    /**
     * Set the value of tricks
     *
     * @param newVar the new value of tricks
     */
    public void setTricks(List<String> newVar) {
        tricks = newVar;
    }

    /**
     * Get the value of tricks
     *
     * @return the value of tricks
     */
    public List<String> getTricks() {
        return tricks;
    }

    /**
     * Set the value of totalPlayTimeHours
     *
     * @param newVar the new value of totalPlayTimeHours
     */
    public void setTotalPlayTimeHours(Long newVar) {
        totalPlayTimeHours = newVar;
    }

    /**
     * Get the value of totalPlayTimeHours
     *
     * @return the value of totalPlayTimeHours
     */
    public Long getTotalPlayTimeHours() {
        return totalPlayTimeHours;
    }

    /**
     * Set the value of overallRating
     *
     * @param newVar the new value of overallRating
     */
    public void setOverallRating(int newVar) {
        overallRating = newVar;
    }

    /**
     * Get the value of overallRating
     *
     * @return the value of overallRating
     */
    public int getOverallRating() {
        return overallRating;
    }

    /**
     * Set the value of userUID
     *
     * @param newVar the new value of userUID
     */
    public void setUserUID(String newVar) {
        userUID = newVar;
    }

    /**
     * Get the value of userUID
     *
     * @return the value of userUID
     */
    public String getUserUID() {
        return userUID;
    }

    /**
     * Set the value of comments
     *
     * @param newVar the new value of comments
     */
    public void setComments(String newVar) {
        comments = newVar;
    }

    /**
     * Get the value of comments
     *
     * @return the value of comments
     */
    public String getComments() {
        return comments;
    }


    /**
     * Get the value of UID
     *
     * @return the value of UID
     */
    public UUID getUID() {
        return UID;
    }

    //
    // Other methods
    //


    @Override
    public String toString() {
        return "Report{" +
                "gameUID='" + gameUID + '\'' +
                ", systemConfiguration='" + systemConfiguration + '\'' +
                ", wineVer='" + wineVer + '\'' +
                ", tricks=" + tricks +
                ", totalPlayTimeHours=" + totalPlayTimeHours +
                ", overallRating=" + overallRating +
                ", userUID='" + userUID + '\'' +
                ", comments='" + comments + '\'' +
                ", UID='" + UID.toString() + '\'' +
                '}';
    }
}
