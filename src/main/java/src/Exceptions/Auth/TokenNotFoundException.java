package src.Exceptions.Auth;

import src.Models.AuthToken;

public class TokenNotFoundException extends Exception {

    public TokenNotFoundException(AuthToken token) {
        super("Token " + token.getToken().toString() + " could not be found on the server");
    }
}
