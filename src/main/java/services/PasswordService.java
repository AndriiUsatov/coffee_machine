package services;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordService {
    private static final String KEY = "KoFFeeMacHIneKEY";
    private static PasswordService passwordServiceInstance;
    private static SecretKeySpec key;

    private PasswordService() {
        key = new SecretKeySpec(KEY.getBytes(), "AES");
    }

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
//        String result = "";
//        byte[] bytes;
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            bytes = cipher.doFinal(password.getBytes());
//            for(byte b : bytes)
//                result += (char)b;
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
//                InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
//        return result;
    }

    public synchronized String decrypt(String encryptedPassword) {
        String result = null;
        byte[] bytes = new byte[encryptedPassword.toCharArray().length];
        char[] chars = encryptedPassword.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        try {
        Cipher decriptCipher = Cipher.getInstance("AES");
        decriptCipher.init(Cipher.DECRYPT_MODE, key);
        result = new String(decriptCipher.doFinal(bytes));
        } catch (NoSuchAlgorithmException | BadPaddingException |
                InvalidKeyException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }


}
