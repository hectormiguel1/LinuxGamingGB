package src.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;


@Service
public class HashingService {
    private final SecureRandom random;
    private final static int SALT_SIZE = 32;
    private final static int ITERATIONS = 100;
    private final static int KEY_LENGTH = 512;
    private final Logger logger = LoggerFactory.getLogger(HashingService.class);

    public HashingService() {
        random = new SecureRandom();
        logger.info("Hashing Service: UP");
    }

    /**
     * Generates the next available salt.
     * @return String representation of salt.
     */
    public String getNextSalt() {
        logger.info("Generating new Salt");
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return new String(salt);
    }

    /**
     * Hashes the provided password by using the provided salt and the PBKDF2 With HMAC SHA1 algorithm
     * @param password Password to be salted.
     * @param salt Password salt
     * @return String representation of hashes salted password.
     */
    public String hashPassword(char[] password, byte[] salt) {
        logger.info("Hashing Password");
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return new String(secretKeyFactory.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
           throw new AssertionError("Error while hashing a password");
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Validates a given password by using the known salt and the expected hashed password
     * @param password Password to be checked for verification.
     * @param salt Salt used when the original password has hashed + salted.
     * @param expectedHash Expected output if the password is correct.
     * @return True is the password is the expected password, false otherwise.
     */
    public Boolean verifyPassword(char[] password, byte[] salt, byte[] expectedHash) {
        logger.info("Validating Password");
        byte[] pwdHash = hashPassword(password, salt).getBytes();
        Arrays.fill(password, Character.MIN_VALUE);
        if(pwdHash.length != expectedHash.length ) return false;
        for(int i = 0; i < pwdHash.length; i++) {
            if(pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }
}
