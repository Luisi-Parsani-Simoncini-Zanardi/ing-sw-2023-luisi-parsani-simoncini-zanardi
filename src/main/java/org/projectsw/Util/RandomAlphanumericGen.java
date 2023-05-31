package org.projectsw.Util;
import java.util.Random;
public class RandomAlphanumericGen {
    private static final String AllowedChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
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
