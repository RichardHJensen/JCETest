package com.rhjensen.encryption;

import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

/**
 * User: Richard H. Jensen
 * Date: 9/21/11
 * Time: 6:08 PM
 */
public class JCEExampleTest {
    @Test
    public void decryptingCiphertextShouldReturnOriginalPlaintext() {
        assertEquals("Original Plaintext", decrypt(encrypt("Original Plaintext")));
    }

    private String decrypt(String cipherText) {
        return cipherText;
    }

    private String encrypt(String plainText) {
        byte[] sessionKey = null;
        byte[] iv = new byte[] { 0x7F, 0x6E, 0x5D, 0x4C, 0x3B, 0x2A, 0x19, 0x08 };
        byte[] ciphertext = null;
        Cipher cipher;
        try {
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            kGen.init(128);
            sessionKey = kGen.generateKey().getEncoded();
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //You can use ENCRYPT_MODE or DECRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));

            ciphertext = cipher.doFinal(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return ciphertext.toString();

    }
}
