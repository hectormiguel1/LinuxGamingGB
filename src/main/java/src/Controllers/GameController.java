package src.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Models.Game;
import src.Models.Report;
import src.Services.GameService;
import src.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class GameController
 */
@RestController
public class GameController {

  //
  // Fields
  //

  private GameService gameService;
  private ReportService reportService;
  private final Logger logger = LoggerFactory.getLogger(GameController.class);
  
  //
  // Constructors
  //
  @Autowired
  public GameController (GameService gameService, ReportService reportService) {
    this.gameService = gameService;
    this.reportService = reportService;

  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of gameService
   * @param newVar the new value of gameService
   */
  public void setGameService (GameService newVar) {
    gameService = newVar;
  }

  /**
   * Get the value of gameService
   * @return the value of gameService
   */
  public GameService getGameService () {
    return gameService;
  }

  /**
   * Set the value of reportService
   * @param newVar the new value of reportService
   */
  public void setReportService (ReportService newVar) {
    reportService = newVar;
  }

  /**
   * Get the value of reportService
   * @return the value of reportService
   */
  public ReportService getReportService () {
    return reportService;
  }

  //
  // Other methods
  //

  /**
   * @param        searchString
   */
  public void search(String searchString)
  {
  }


  /**
   * @return       boolean
   * @param        newGame at start the new game will have no repeorts.
   */
  public boolean addNewGame(Game newGame)
  {
    return false;
  }


  /**
   * //Adds new report to a game.
   * @param        newReport
   */
  public void addNewReport(Report newReport)
  {
  }


  /**
   * //Returns the reports for a given name.
   * @return       List<Report>
   * @param        gameUID
   */
  public List<Report> getReports(String gameUID)
  {
    return null;
  }


}
