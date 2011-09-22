package com.rhjensen.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESEncryptor {
    byte[] sessionKey = null;
    final byte[] iv = new byte[]{0x7F, 0x6E, 0x5D, 0x4C, 0x3B, 0x2A, 0x19, 0x08,
            0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00};
    private Cipher encryptor;
    private Cipher decryptor;

    public AESEncryptor() {
        try {
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            kGen.init(128);
            sessionKey = kGen.generateKey().getEncoded();
            encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptor.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));
            decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptor.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String plainText) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // get bytes, encrypt, encode
        byte[] utf8bytes = plainText.getBytes("utf-8");
        byte[] ciphertext = encryptor.doFinal(utf8bytes);

        return new BASE64Encoder().encode(ciphertext);
    }

    public String decrypt(String cipherText) throws IOException, IllegalBlockSizeException, BadPaddingException {
        // decode, decrypt, get bytes
        byte[] encryptedBytes = new BASE64Decoder().decodeBuffer(cipherText);
        byte[] plaintext = decryptor.doFinal(encryptedBytes);
        return new String(plaintext);
    }
}