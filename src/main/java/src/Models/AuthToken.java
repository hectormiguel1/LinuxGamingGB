package src.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import src.Models.Privilages.AccessLevel;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AuthToken {

    @JsonIgnore
    final private LocalDateTime expires;

    @Id final private UUID token;

    @JsonIgnore
    final AccessLevel accessLevel;

    public AuthToken() {
        this(AccessLevel.NewUser);
    }


    public AuthToken(AccessLevel accessLevel) {
        this.token = UUID.randomUUID();
        expires = LocalDateTime.now().plusHours(3);
        this.accessLevel = accessLevel;
    }

    public AuthToken(String UUID) {
        this.token = java.util.UUID.fromString(UUID);
        this.accessLevel = AccessLevel.NewUser;
        expires = LocalDateTime.now();
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public UUID getToken() {
        return token;
    }

    public Boolean getIsValid() {
        return LocalDateTime.now().compareTo(expires) <= 0;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "expires=" + expires +
                ", token=" + token +
                ", AccessLevel=" + this.accessLevel +
                '}';
    }
}
