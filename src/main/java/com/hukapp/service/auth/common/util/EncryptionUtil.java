package com.hukapp.service.auth.common.util;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Utility class providing encryption and decryption functionality using AES algorithm.
 * This class handles the encryption and decryption of sensitive data using symmetric encryption.
 * 
 * <p>The class uses AES (Advanced Encryption Standard) with a predefined encryption key.
 * Note that in production environments, the encryption key should be properly secured
 * and managed through environment variables or a secure key management system.</p>
 * 
 * <p>Both encryption and decryption methods handle null inputs gracefully by returning null,
 * and throw RuntimeException if any cryptographic operation fails.</p>
 */
@Component
public class EncryptionUtil {
    // TODO: In production, ENCRYPTION_KEY should be generated and stored securely
    // (e.g., in environment variables or a secure key management system)
    private static final String ENCRYPTION_KEY = "YourSecretKey123YourSecretKey123";
    private static final String ALGORITHM = "AES";

    /**
     * Encrypts the given string data using AES encryption algorithm.
     * 
     * @param data The string to be encrypted. Can be null.
     * @return The Base64 encoded encrypted string, or null if input is null
     * @throws RuntimeException if encryption fails for any reason
     */
    public String encrypt(String data) {
        try {
            if (data == null)
                return null;
            SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    /**
     * Decrypts the given encrypted string data using AES encryption algorithm.
     * 
     * @param encryptedData The Base64 encoded encrypted string to be decrypted. Can be null.
     * @return The decrypted string, or null if input is null
     * @throws RuntimeException if decryption fails for any reason
     */
    public String decrypt(String encryptedData) {
        try {
            if (encryptedData == null)
                return null;
            SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}