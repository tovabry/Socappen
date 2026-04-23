package com.example.socapplication.service;

import com.example.socapplication.handlers.exception.EncryptionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {

    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); // ← add this


    @Value("${encryption.secret-key}")
    private String secretKey;

    private SecretKeySpec getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey); //converts base64 string → raw 32 bytes
        return new SecretKeySpec(keyBytes, "AES"); //wraps bytes into a key object Java crypto can use
    }

    public String encrypt(String plaintext) {
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];

            SECURE_RANDOM.nextBytes(iv);
            //generates a random 12-byte Initialization Vector, IV (nonce)
            //CRITICAL: must be unique per encryption — reusing an IV with the same key breaks GCM completely

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(), new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            //initializes the cipher in encrypt mode with your key and the random IV

            byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            //encrypts the plaintext — output is ciphertext + 16 byte auth tag appended automatically by GCM

            //Prepend IV to ciphertext so we can use it during decryption
            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
            //joins IV + ciphertext into one array: [IV (12 bytes)][ciphertext + tag]
            //IV is not secret, but needs to be stored so we can decrypt later

            return Base64.getEncoder().encodeToString(combined);
            //converts to base64 string so it can be safely stored in the database
        } catch (GeneralSecurityException e) {
            throw new EncryptionException("Encryption failed", e);
        }
    }

    public String decrypt(String ciphertext) {
        try {
            byte[] combined = Base64.getDecoder().decode(ciphertext);
            //reverses the base64 back to raw bytes: [IV (12 bytes)][ciphertext + tag]


            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] encrypted = new byte[combined.length - GCM_IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, iv.length, encrypted, 0, encrypted.length);
            //splits the combined array back into IV and ciphertext


            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey(), new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            //initializes cipher in decrypt mode with the same key and the extracted IV


            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
            //decrypts and verifies the auth tag — if the data was tampered with this throws an exception
        } catch (GeneralSecurityException e) {
            throw new EncryptionException("Encryption failed", e);
        }
    }
}
