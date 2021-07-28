package src.Controllers;

import org.hibernate.annotations.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.Exceptions.Auth.InvalidTokenException;
import src.Exceptions.Auth.TokenNotFoundException;
import src.Exceptions.Data.GameAlreadyExistsException;
import src.Exceptions.Data.GameNotFoundException;
import src.Exceptions.Data.UnfulfilledRequestException;
import src.Models.Game;
import src.Models.GameQuery;
import src.Models.RecordQuery;
import src.Models.Report;
import src.Services.GameService;
import src.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import src.Services.TokenService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/games")
@CrossOrigin
public class GameController {

  private final GameService gameService;
  private final ReportService reportService;
  private final TokenService tokenService;
  private final Logger logger = LoggerFactory.getLogger(GameController.class);
  private final String Auth_Token_Header = "Auth-Token";

  @Autowired
  public GameController (GameService gameService, ReportService reportService, TokenService tokenService) {
    this.gameService = gameService;
    this.reportService = reportService;
    this.tokenService = tokenService;
    logger.info("Game Controller: UP");

  };

  /**
   * Function opens endpoint at /games/query and handles request to search the games database with the query string
   * @param query: Game Query record containing search string and optional tags.
   * @param token: Token used to validate the request.
   * @return ResponseEntity<List<Game>>: Response entity with list of games found to match the search query.
   * @throws InvalidTokenException: Thrown when the token passed does not have the right to do the request.
   * @throws UnfulfilledRequestException: Thrown when the application can not currently handle the request
   * @throws ExecutionException: Thrown when the connection to the database fails.
   * @throws InterruptedException: Thrown when the connection to the database fails.
   * @throws TokenNotFoundException: Thrown when the token is not found or is no longer valid.
   * @GetMapping: Opens mapping to accept GET requests on the /games/query endpoint.
   */
  @GetMapping("/query")
  @CrossOrigin
  public ResponseEntity<List<Game>> search(@RequestBody GameQuery query, @RequestHeader(Auth_Token_Header) String token)
          throws InvalidTokenException, UnfulfilledRequestException, ExecutionException, InterruptedException,
          TokenNotFoundException {
    return new ResponseEntity<List<Game>>(
            gameService.searchGame(query.getSearchQuery(), tokenService.tokenize(token)), HttpStatus.OK
    );
  }

  /**
   *Function handles request to add a new game
   * @param newGame: Game to be added
   * @param token: Token used to validate the request
   * @return ResponseEntity: Containing results of request, true for success, exception otherwise.
   * @throws GameAlreadyExistsException: Thrown when the game already exists.
   * @throws InvalidTokenException: Thrown when the token does not have the right to do the request.
   * @throws ExecutionException: Thrown when the connection to database fails.
   * @throws InterruptedException: Thrown when the connection to database is interrupted.
   * @throws TokenNotFoundException: Thrown when the token is not found or is no longer valid.
   */
  @PostMapping
  @CrossOrigin
  public ResponseEntity<Object> addNewGame(@RequestBody Game newGame, @RequestHeader(Auth_Token_Header) String token)
          throws GameAlreadyExistsException, InvalidTokenException, ExecutionException, InterruptedException,
          TokenNotFoundException {
    return new ResponseEntity<>(
            gameService.newGame(newGame, tokenService.tokenize(token)), HttpStatus.CREATED
    );
  }

  /**
   * Function handles request to post new reports.
   * @param newReport New report to be added.
   * @param token Token used to validate the request.
   * @return ResponseEntity True if request succeeds, otherwise Exceptions are thrown.
   * @throws InvalidTokenException Thrown when the token does not have the rights to do the request.
   * @throws ExecutionException Thrown when the connection to database fails.
   * @throws InterruptedException Thrown when the connection to database is interrupted.
   * @throws TokenNotFoundException Thrown when the token is not found or is not valid.
   * @throws GameNotFoundException Thrown when attempting to add report for game that does not exist.
   */
  @PostMapping("/reports")
  @CrossOrigin
  public ResponseEntity<Object> addNewReport(@RequestBody Report newReport, @RequestHeader(Auth_Token_Header) String token)
          throws InvalidTokenException, ExecutionException, InterruptedException, TokenNotFoundException, GameNotFoundException {
    return new ResponseEntity<>(
            reportService.addReport(newReport, tokenService.tokenize(token)), HttpStatus.CREATED
    );
  }

  /**
   * Handles request to pull all the reports for a give game ID
   * @param recordQuery containing gameUID to query records for.
   * @param token Token used to validate the request
   * @return Response Entity with List of Reports of the give game, otherwise exceptions are thrown.
   * @throws InvalidTokenException Thrown when the token does not have the right to do request.
   * @throws ExecutionException Thrown when the connection to database fails.
   * @throws InterruptedException Thrown when the connection to database is interrupted.
   * @throws TokenNotFoundException Thrown when the token is not found or is no longer valid.
   */
  @GetMapping("/reports")
  public ResponseEntity<Object> getReports(@RequestBody RecordQuery recordQuery, @RequestHeader(Auth_Token_Header) String token)
          throws InvalidTokenException, ExecutionException, InterruptedException, TokenNotFoundException {
    return new ResponseEntity<>(
            reportService.getReports(recordQuery.gameUID(), tokenService.tokenize(token)), HttpStatus.OK
    );
  }


}
