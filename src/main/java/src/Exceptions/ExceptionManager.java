package src.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import src.Exceptions.Auth.*;
import src.Exceptions.Data.*;

import java.util.concurrent.ExecutionException;

@ControllerAdvice
public class ExceptionManager {

    private final Logger logger = LoggerFactory.getLogger(ExceptionManager.class);

    public ExceptionManager() {
        logger.info("Exception Manager Loaded");
    }

    /**
     * Handles Token Exceptions and responds back to the client.
     * @param exception: Exception to be handled.
     * @return ResponseEntity: Response entity sent back to client.
     */
    @ExceptionHandler({
            InvalidTokenException.class,
            TokenNotFoundException.class})
    public ResponseEntity<Object> handleTokenExceptions(Exception exception) {
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Handles User Exceptions and responds back to the client.
     * @param ex: Exception to be handled.
     * @return ResponseEntity: Response entity sent back to client.
     */
    @ExceptionHandler({
            UserAlreadyExistsException.class,
            EmailPasswordMismatchException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<Object> handleUserExceptions(Exception ex) {
        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.NOT_ACCEPTABLE
        );
    }

    /**
     * Handles Game Not Found Exception and responds back to the client.
     * @param ex: Exception to be handled.
     * @return ResponseEntity: Response entity sent back to client.
     */
    @ExceptionHandler({
            GameNotFoundException.class
    })
    public ResponseEntity<Object> handleGameNotFound(Exception ex) {
        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.NOT_FOUND
        );
    }

    /**
     * Handles Game Already Exists Exception and responds back to the client.
     * @param ex: Exception to be handled.
     * @return ResponseEntity: Response entity sent back to client.
     */
    @ExceptionHandler({
            GameAlreadyExistsException.class
    })
    public ResponseEntity<Object> handleGameAlreadyExists(Exception ex) {
        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.CONFLICT
        );
    }

    /**
     * Handles Unfulfilled Request Exception and responds back to the client.
     * We should never have this exceptions, others should be thrown instead, but its there as a
     * last ditch effort.
     * @param ex: Exception to be handled.
     * @return ResponseEntity: Response entity sent back to client.
     */
    @ExceptionHandler({UnfulfilledRequestException.class})
    public ResponseEntity<Object> handleUnfulfilledException(Exception ex) {
        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    /**
     * Handles the exceptions thrown the connecting with DB, at this time all we can do is log it and
     * deal with the underlying issue later.
     * @param ex: Exception to be handled.
     */
    @ExceptionHandler({InterruptedException.class, ExecutionException.class})
    public void handleDBExceptions(Exception ex) {
        logger.error(ex.getMessage());
    }


}
