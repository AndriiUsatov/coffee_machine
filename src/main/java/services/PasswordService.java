package services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordService {
    private static PasswordService passwordServiceInstance;

    private PasswordService() {}

    public static PasswordService getPasswordServiceInstance() {
        if (passwordServiceInstance == null) {
            synchronized (PasswordService.class) {
                if (passwordServiceInstance == null)
                    passwordServiceInstance = new PasswordService();
            }
        }
        return passwordServiceInstance;
    }

    public synchronized String encrypt(String password) {
        String result = null;
        try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedByteArray = digest.digest(password.getBytes("UTF-8"));
        result = Base64.getEncoder().encodeToString(hashedByteArray);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
