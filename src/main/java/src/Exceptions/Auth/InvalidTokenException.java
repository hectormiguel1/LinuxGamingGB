package src.Exceptions.Auth;

import src.Models.AuthToken;

public class InvalidTokenException extends Exception {

    public InvalidTokenException(AuthToken token) {
        super("Token: " + token.getToken().toString() + " expired on: " + token.getExpires().toString() + " an is no longer valid");
    }
}
