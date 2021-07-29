package src.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Exceptions.Auth.InvalidTokenException;
import src.Exceptions.Auth.OperationNotAllowedException;
import src.Exceptions.Auth.TokenNotFoundException;
import src.Exceptions.Auth.UserDoesNotExistException;
import src.Exceptions.Data.GameNotFoundException;
import src.Models.AuthToken;
import src.Models.Privilages.RequestType;
import src.Models.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class ReportService
 */
@Service
public class ReportService {
    private StorageService storageService;
    private TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger(ReportService.class);
    public ReportService () { }

    @Autowired
    public ReportService(StorageService storageService, TokenService tokenService) {
        this.storageService = storageService;
        this.tokenService = tokenService;
        logger.info("Report Service: UP");
    }

    /**
     * Attempts to add a report to the database.
     * @param newReport Report to be added to the database.
     * @param userToken User token used to validate the request.
     * @return Boolean True if the request succeeds, false otherwise.
     * @throws TokenNotFoundException When the token is not found in the auth database.
     * @throws ExecutionException When the connection to the DB fails.
     * @throws InterruptedException When the connection to the DB is interrupted.
     * @throws OperationNotAllowedException When the passed token is not allowed to make this request.
     * @throws GameNotFoundException When trying to add a report for a game that does not exists
     * @throws InvalidTokenException When the token is no longer valid
     * @throws UserDoesNotExistException When the user who issued the report does not exists in DB.
     */
    public Boolean addReport(Report newReport, AuthToken userToken) throws TokenNotFoundException,
            ExecutionException, InterruptedException, GameNotFoundException, OperationNotAllowedException,
            InvalidTokenException, UserDoesNotExistException {
        logger.info("Attempting to add new report to Game: " + newReport.getGameUID() + ", with AuthToken: " + userToken.getToken().toString());
        //Verifies that the token is valid before doing request to database.
        if(tokenService.validateToken(userToken, RequestType.Post)) {
            storageService.getGame(newReport.getGameUID());
            storageService.validateUserExists(newReport.getUserUID());
            return storageService.addReport(newReport);
        }
        throw new InvalidTokenException(userToken);
    }

    /**
     * Gets all the Reports for the given game.
     * @param gameUID UID of the desired game.
     * @param userToken Token of the desired newUser.
     * @return List<Receipt>  List of receipts for the given game, empty list is nothing is found.
     * @throws TokenNotFoundException Thrown when the token can not found or validated.
     * @throws ExecutionException Thrown when the connection to the database fails.
     * @throws InterruptedException Thrown when the connection to the database is interrupted.
     * @throws OperationNotAllowedException When the passed token is not allowed to make this request.
     * @throws InvalidTokenException Thrown when the token is no longer valid.
     */
    public List<Report> getReports(String gameUID, AuthToken userToken) throws TokenNotFoundException,
            ExecutionException, InterruptedException, OperationNotAllowedException, InvalidTokenException {
        logger.info("Attempting to get all Reports for Game: " + gameUID + ", with AuthToken: " + userToken.getToken().toString());

        if(tokenService.validateToken(userToken, RequestType.Read)) {
            return storageService.getAllGameReports(gameUID);
        }
        throw new InvalidTokenException(userToken);
    }


}
