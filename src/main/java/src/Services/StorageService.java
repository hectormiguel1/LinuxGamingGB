package src.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import src.Exceptions.Auth.UserAlreadyExistsException;
import src.Exceptions.Auth.UserDoesNotExistException;
import src.Exceptions.Auth.UserNotFoundException;
import src.Exceptions.Auth.EmailPasswordMismatchException;
import src.Exceptions.Data.GameAlreadyExistsException;
import src.Exceptions.Data.GameNotFoundException;
import src.Models.Game;
import src.Models.Report;
import src.Models.User;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * Class StorageService
 */
@Service
public class StorageService {

    private final static String PathToServiceAccountCredentials = "src/main/resources/backend-service-account.json";
    private final static Logger logger = LoggerFactory.getLogger(StorageService.class);


    private final String UserCollection = "Users";
    private final String GameCollection = "Games";
    private final String ReportCollection = "Reports";
    private final HashingService hashingService;

    //
    // Fields
    //
    private Firestore persistentDB;
    //
    // Constructors
    //
    @Autowired
    public StorageService (HashingService hashingService) {
        this.hashingService = hashingService;
        logger.info("Current Working Directory: " + System.getProperty("user.dir"));
        try {
            InputStream serviceAccount = new FileInputStream(PathToServiceAccountCredentials);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials).build();
            FirebaseApp.initializeApp(options);
            persistentDB = FirestoreClient.getFirestore();
            logger.info("Successfully connected to DB");
            logger.info("Storage Service: UP");
        } catch (FileNotFoundException e) {
            logger.error("Could not open file for read service account config");
        } catch (IOException e) {
            logger.error("Could not properly decode the service account credentials file. ");
        }

    }

    /**
     * Creates a new newUser on the persistent database. Checks if the email has already been registered before registering the newUser.
     * @param newUser: New newUser to be registered.
     * @return User: Returns the new newUser stored on the db.
     * @throws ExecutionException: Thrown when the call to get the data from db fails.
     * @throws InterruptedException: Thrown when the call to get the data from the db fails.
     * @throws UserAlreadyExistsException: Thrown when attempting to register a newUser to the db that already exists.
     */
    public User createNewUser(User newUser) throws ExecutionException, InterruptedException, UserAlreadyExistsException {
        //Check if the newUser already exists in the db.
        var userSnapshot = persistentDB.collection(UserCollection).whereEqualTo("email", newUser.getEmail()).get().get();
        //if the newUser already exists, then throw the exception.
        if(userSnapshot.size() > 0) {
            throw new UserAlreadyExistsException(newUser);
        }
        //Otherwise make request for the newUser to be stored in the db and wait for response from db.
        var response = persistentDB.collection(UserCollection).document(newUser.getUID()).set(newUser).get();

        logger.info("Wrote new newUser, operation was completed on: " + response.getUpdateTime());

        //Return the new newUser stored on the db.
        return persistentDB.collection(UserCollection).document(newUser.getUID()).get().get().toObject(User.class);
    }

    /**
     * Gets a newUser from the persistent Database.
     * Checks that the newUser exists, and that the provided password matches the one provided.
     * @param userEmail: Email of the desired newUser.
     * @param password: Password of the desired newUser.
     * @return User object that matches the provided email and password.
     * @throws ExecutionException: Thrown when attempting to get db data fails.
     * @throws InterruptedException: Thrown when attempting to connect to db fails.
     * @throws EmailPasswordMismatchException : Thrown if there are not results that match the provided email and password combo.
     */
    public User getUser(String userEmail, String password) throws ExecutionException, InterruptedException,
            EmailPasswordMismatchException, UserNotFoundException {
        //Verifies that a newUser with provided email exits on the server.
        var userSnapshot = persistentDB.collection(UserCollection)
                .whereEqualTo("email", userEmail)
                .get().get();
        if(userSnapshot.isEmpty())  {
            throw new UserNotFoundException(userEmail);
        }
        User userObject = userSnapshot.toObjects(User.class).get(0);
        var confirmedPassword = hashingService.verifyPassword(password.toCharArray(), userObject.getSalt().getBytes(), userObject.getPassword().getBytes());
        if(confirmedPassword) {

            logger.info("Successfully found user with email: " + userEmail + " password verification success");
            //There will ever only be on newUser with the provided email and password.
            return userObject;
        } else {
            throw new EmailPasswordMismatchException();
        }
    }

    /**
     * This function adds the new report to the database.
     * @param newReport: Report to be added to the database.
     * @return Boolean: True if the record was added, otherwise exceptions are thrown.
     * @throws ExecutionException: Thrown when the fails to connect to the DB.
     * @throws InterruptedException: Thrown when the connection to DB is interrupted.
     */
    public Boolean addReport(Report newReport) throws ExecutionException, InterruptedException {
        var reportDocument = persistentDB.collection(ReportCollection).document(newReport.getUID());
        var response = reportDocument.set(newReport).get();
        logger.info("Added new report for game: " + newReport.getGameUID() + ", report UID: " + newReport.getUID());
        return true;
    }

    /**
     * Gets all the reports for the matching Game UID.
     * @param gameUID: UID of game to get reports of
     * @return List<Report>: List Containing all the reports associated with the provided gameUID.
     * @throws ExecutionException: Thrown when the connection to DB Fails.
     * @throws InterruptedException: Thrown when the connection to DB is interrupted.
     */
    public List<Report> getAllGameReports(String gameUID) throws ExecutionException, InterruptedException {
        logger.info("Getting all Reports for Game: " + gameUID);
        var gameReports = persistentDB.collection(ReportCollection).whereEqualTo("gameUID", gameUID).get().get();
        return gameReports.toObjects(Report.class);
    }

    /**
     * Function will add a new game to the persistent database.
     * @param newGame: Game to be added.
     * @return Boolean: True if the operation was completed successfully, exceptions are thrown otherwise.
     * @throws ExecutionException: Thrown when connection ot the database fails.
     * @throws InterruptedException: Thrown when the the connection to database is interrupted.
     * @throws GameAlreadyExistsException:
     */
    public Boolean addGame(Game newGame) throws ExecutionException, InterruptedException, GameAlreadyExistsException {
        logger.info("Attempting to add new Game: " + newGame.getUID());
        //Checks the the game is not already present on the DB.
        var dbResponse = persistentDB.collection(GameCollection).whereEqualTo("name", newGame.getName()).get().get();
        if(dbResponse.size() > 0) {
            throw new GameAlreadyExistsException(newGame);
        }
        var writeResponse = persistentDB.collection(GameCollection).document(newGame.getUID()).set(newGame).get();
        logger.info("New Game: " + newGame.getUID() + " added");
        return true;
    }

    /**
     * Does Query on the db for games containing the queryString in their name.
     * @param queryString: string to search for.
     * @return List<Game>: Games found to contain the search query in their name.
     * @throws ExecutionException: Thrown when the connection to database fails.
     * @throws InterruptedException: Thrown when the connection to database is interrupted.
     */
    public List<Game> queryGames(String queryString) throws ExecutionException, InterruptedException {
        List<Game> foundGames = new ArrayList<>();
        logger.info("Querying database for games with name containing: " + queryString);
        var dbResponse = persistentDB.collection(GameCollection).listDocuments();
        for(var document: dbResponse) {
            Game game = document.get().get().toObject(Game.class);
            assert game != null;
            if(game.getName().toLowerCase().contains(queryString.toLowerCase())) {
                foundGames.add(game);
            }
        }
        return foundGames;
    }

    /**
     * Attempts to locate the game in the database, if not found and exception is thrown.
     * @param gameUID: UID of game to locate.
     * @return Game: Game if found.
     * @throws ExecutionException: Thrown when the connection to database fails.
     * @throws InterruptedException: Thrown when the connection to database is interrupted.
     * @throws GameNotFoundException: Thrown when the desired game is not located in the database.
     */
    public Game getGame(String gameUID) throws ExecutionException, InterruptedException, GameNotFoundException {
        var dbResponse = persistentDB.collection(GameCollection).document(gameUID).get().get();
        if(dbResponse.exists()) {
            return dbResponse.toObject(Game.class);
        } else {
            throw new GameNotFoundException(gameUID);
        }
    }

    /**
     * Validates that the provided user exists in the database.
     * @param userUID User UID to validate
     * @return true if the user exists, Exceptions are thrown otherwise.
     * @throws ExecutionException When connection to database fails.
     * @throws InterruptedException When connection to DB is interrupted.
     * @throws UserDoesNotExistException When the desired user does not exist.
     */
    public Boolean validateUserExists(String userUID) throws ExecutionException, InterruptedException, UserDoesNotExistException {
        var dbResponse = persistentDB.collection(UserCollection).document(userUID).get().get();
        if(dbResponse.exists()) {
            return true;
        }
        throw new UserDoesNotExistException(userUID);
    }



}
