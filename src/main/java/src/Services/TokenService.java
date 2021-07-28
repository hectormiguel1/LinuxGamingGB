package src.Services;

import antlr.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Exceptions.Auth.InvalidTokenException;
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

    private HashMap<AccessLevel, List<Enum<?>>> accessLevels = new HashMap<>();
    {
        accessLevels.put(AccessLevel.NewUser, Arrays.asList(NewUserRights.values()));
        accessLevels.put(AccessLevel.User, Arrays.asList(UserRights.values()));
        accessLevels.put(AccessLevel.Admin, Arrays.asList(AdminRights.values()));
        accessLevels.put(AccessLevel.ServerAdmin, Arrays.asList(ServerAdminRights.values()));
    }

    TokenRepository tokenRepository;

    Logger logger = LoggerFactory.getLogger(TokenService.class);

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
     * @throws InvalidTokenException: When the passed token is not authorized to do the request.
     */
    public Boolean validateToken(AuthToken token, RequestType requestType) throws TokenNotFoundException, InvalidTokenException {
        UUID tokenUUID = token.getToken();

        AuthToken serverVerifiedToken = tokenRepository.findById(tokenUUID).orElseThrow(() -> new TokenNotFoundException(token));
        if(accessLevels.get(serverVerifiedToken.getAccessLevel()).contains(requestType)) {
            logger.info("Validating Token: " + token.getToken().toString() + ", Valid: " + serverVerifiedToken.getIsValid());
            return serverVerifiedToken.getIsValid();
        }
        throw new InvalidTokenException(token);
    }
}
