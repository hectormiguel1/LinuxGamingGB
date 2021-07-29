package src.Services;

import antlr.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Exceptions.Auth.InvalidTokenException;
import src.Exceptions.Auth.OperationNotAllowedException;
import src.Exceptions.Auth.TokenNotFoundException;
import src.Models.AuthToken;
import src.Models.Privilages.*;
import src.Models.User;
import src.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {

    private final static HashMap<AccessLevel, List<Enum<?>>> accessLevels = new HashMap<>();
    static {
        accessLevels.put(AccessLevel.NewUser, Arrays.asList(NewUserRights.values()));
        accessLevels.put(AccessLevel.User, Arrays.asList(UserRights.values()));
        accessLevels.put(AccessLevel.Admin, Arrays.asList(AdminRights.values()));
        accessLevels.put(AccessLevel.ServerAdmin, Arrays.asList(ServerAdminRights.values()));
    }

    private final static Logger logger = LoggerFactory.getLogger(TokenService.class);


    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        logger.info("AuthToken Service: UP");
    }

    /**
     * Generates a new token for authentication.
     * @return AuthToken: newly generated token.
     */
    public AuthToken generateNewToken(User targetUser) {
        AuthToken newToken = new AuthToken(targetUser.getAccessLevel());
        tokenRepository.save(newToken);
        logger.info("Generating new token with AccessLevel:" + newToken.getAccessLevel());
        return newToken;
    }

    /**
     * Validates that the passed auth token is a valid token
     * @param token: token to be validated.
     * @return Boolean: True of the token is valid, false otherwise.
     * @throws TokenNotFoundException: When the token is not found in the repository.
     * @throws OperationNotAllowedException: When the passed token is not authorized to do the request.
     */
    public Boolean validateToken(AuthToken token, RequestType requestType) throws TokenNotFoundException, OperationNotAllowedException {
        UUID tokenUUID = token.getToken();

        AuthToken serverVerifiedToken = tokenRepository.findById(tokenUUID).orElseThrow(() -> new TokenNotFoundException(token));
        boolean hasRight = false;
        for(var right : accessLevels.get(serverVerifiedToken.getAccessLevel()))  {
            if(right.toString().equalsIgnoreCase(requestType.toString())) {
                hasRight = true;
            }
        }
        if(hasRight) {
            logger.info("Validating Token: " + token.getToken().toString() + ", Valid: " + serverVerifiedToken.getIsValid());
            return serverVerifiedToken.getIsValid();
        }
        throw new OperationNotAllowedException();
    }

    /**
     * Returns a token representation of the UUID string
     * @param tokenUID: String to convert to token
     * @return AuthToken: returns Java Object representation of the AuthToken with given UUID.
     */
    public AuthToken tokenize(String tokenUID) {
        return new AuthToken(tokenUID);
    }
}
