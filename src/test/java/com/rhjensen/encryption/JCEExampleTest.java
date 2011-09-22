package com.rhjensen.encryption;

import org.junit.Test;

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
        return null;
    }

    private String encrypt(String plainText) {
        return null;
    }
}
