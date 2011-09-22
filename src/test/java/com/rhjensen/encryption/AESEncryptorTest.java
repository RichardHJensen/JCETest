package com.rhjensen.encryption;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.crypto.KeyGenerator;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

/**
 * User: Richard H. Jensen
 * Date: 9/21/11
 * Time: 6:08 PM
 */
public class AESEncryptorTest {
    private static String SAMPLE_TEXT = "Original Plaintext";
    private static KeyGenerator KEY_GENERATOR = null;
    private static byte[] SESSION_KEY = null;
    private static byte[] VECTOR;
    private AESEncryptor aesExample;

    @BeforeClass
    public static void initializeSharedState() throws NoSuchAlgorithmException {
        KEY_GENERATOR = KeyGenerator.getInstance("AES");
        KEY_GENERATOR.init(128);
        SESSION_KEY = KEY_GENERATOR.generateKey().getEncoded();
        VECTOR = new byte[]{0x7F, 0x6E, 0x5D, 0x4C, 0x3B, 0x2A, 0x19, 0x08,
            0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00};
    }

    @Before
    public void setUp() {
        aesExample = new AESEncryptor(SESSION_KEY, VECTOR);
    }

    @Test
    public void decryptingCiphertextShouldReturnOriginalPlaintext() throws Exception {
        String cipherText = aesExample.encrypt(SAMPLE_TEXT);
        String plainText = aesExample.decrypt(cipherText);
        assertEquals(SAMPLE_TEXT, plainText);
    }

    @Test
    public void encryptingTheSameStringShouldGiveTheSameCiphertext() throws Exception {
        String cipherText1 = aesExample.encrypt(SAMPLE_TEXT);
        String cipherText2 = new AESEncryptor(SESSION_KEY, VECTOR).encrypt(SAMPLE_TEXT);
        assertEquals(cipherText1, cipherText2);
    }

}
