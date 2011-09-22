package com.rhjensen.encryption;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    private byte[] sessionKey = null;
    private final byte[] iv = new byte[] { 0x7F, 0x6E, 0x5D, 0x4C, 0x3B, 0x2A, 0x19, 0x08,
                             0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00 };

    @Test
    public void decryptingCiphertextShouldReturnOriginalPlaintext() {
        assertEquals("Original Plaintext", decrypt(encrypt("Original Plaintext")));
    }

    private String decrypt(String cipherText) {
        byte[] plaintext = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //You can use ENCRYPT_MODE or DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));

            plaintext = cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cipherText));
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return plaintext.toString();
    }

    private String encrypt(String plainText) {
        byte[] ciphertext = null;
        Cipher cipher;
        try {
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            kGen.init(128);
            sessionKey = kGen.generateKey().getEncoded();
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //You can use ENCRYPT_MODE or DECRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));

            ciphertext = cipher.doFinal(plainText.getBytes("utf-8"));
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return new BASE64Encoder().encode(ciphertext).toString();

    }
}
