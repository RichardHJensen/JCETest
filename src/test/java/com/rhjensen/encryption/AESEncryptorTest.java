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

}
