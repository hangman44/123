package com.hukapp.api.common.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hukapp.service.auth.common.util.EncryptionUtil;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionUtilTest {
    
    private EncryptionUtil encryptionUtil;

    @BeforeEach
    void setUp() {
        encryptionUtil = new EncryptionUtil();
    }

    @Test
    void testEncryptAndDecrypt() {
        String originalText = "Hello, World!";
        String encrypted = encryptionUtil.encrypt(originalText);
        String decrypted = encryptionUtil.decrypt(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalText, encrypted);
        assertEquals(originalText, decrypted);
    }

    @Test
    void testEncryptNullValue() {
        String encrypted = encryptionUtil.encrypt(null);
        assertNull(encrypted);
    }

    @Test
    void testDecryptNullValue() {
        String decrypted = encryptionUtil.decrypt(null);
        assertNull(decrypted);
    }

    @Test
    void testMultipleEncryptions() {
        String text = "Test text";
        String firstEncryption = encryptionUtil.encrypt(text);
        String secondEncryption = encryptionUtil.encrypt(text);
        
        assertEquals(firstEncryption, secondEncryption, "Same text should produce same encryption");
    }

    @Test
    void testSpecialCharacters() {
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        String encrypted = encryptionUtil.encrypt(specialChars);
        String decrypted = encryptionUtil.decrypt(encrypted);
        
        assertEquals(specialChars, decrypted);
    }

    @Test
    void testLongString() {
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longString.append("a");
        }
        String original = longString.toString();
        
        String encrypted = encryptionUtil.encrypt(original);
        String decrypted = encryptionUtil.decrypt(encrypted);
        
        assertEquals(original, decrypted);
    }

    @Test
    void testInvalidEncryptedData() {
        assertThrows(RuntimeException.class, () -> {
            encryptionUtil.decrypt("InvalidBase64String");
        });
    }
}