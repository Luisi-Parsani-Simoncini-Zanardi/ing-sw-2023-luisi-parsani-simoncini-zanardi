package org.projectsw.Util;
import java.util.Random;

/**
 * A utility class for generating random alphanumeric strings.
 */
public class RandomAlphanumericGen {
    private static final String AllowedChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Generates a random alphanumeric string of the specified length.
     * @param length The length of the generated string.
     * @return A random alphanumeric string.
     */
    public String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(AllowedChar.length());
            char randomChar = AllowedChar.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
