package com.rhjensen.encryption;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Richard H. Jensen
 * Date: 9/21/11
 * Time: 6:08 PM
 */
public class AESEncryptorTest {
    private final AESEncryptor aesExample = new AESEncryptor();
    private static String SAMPLE_TEXT = "Original Plaintext";

    @Test
    public void decryptingCiphertextShouldReturnOriginalPlaintext() throws Exception {
        String cipherText = aesExample.encrypt(SAMPLE_TEXT);
        String plainText = aesExample.decrypt(cipherText);
        assertEquals(SAMPLE_TEXT, plainText);
    }

    @Test
    public void encryptingTheSameStringShouldGiveTheSameCiphertext() throws Exception {
        String cipherText1 = aesExample.encrypt(SAMPLE_TEXT);
        String cipherText2 = new AESEncryptor().encrypt(SAMPLE_TEXT);
        assertEquals(cipherText1, cipherText2);
    }

}
