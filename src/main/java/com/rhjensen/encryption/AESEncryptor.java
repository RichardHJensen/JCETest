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
    private Cipher encryptor;
    private Cipher decryptor;

    public AESEncryptor(String sessionKey, String iv) {
        byte[] keyBytes;
        byte[] vectorBytes;
        try {
            keyBytes = new BASE64Decoder().decodeBuffer(sessionKey);
            vectorBytes = new BASE64Decoder().decodeBuffer(iv);
            encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptor.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(keyBytes, "AES"),
                    new IvParameterSpec(vectorBytes));
            decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptor.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(keyBytes, "AES"),
                    encryptor.getParameters());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String plainText) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // get bytes from string, encrypt, encode
        byte[] utf8bytes = plainText.getBytes("utf-8");
        byte[] ciphertext = encryptor.doFinal(utf8bytes);

        return new BASE64Encoder().encode(ciphertext);
    }

    public String decrypt(String cipherText) throws IOException, IllegalBlockSizeException, BadPaddingException {
        // decode, decrypt, use bytes to create string
        byte[] encryptedBytes = new BASE64Decoder().decodeBuffer(cipherText);
        byte[] plaintext = decryptor.doFinal(encryptedBytes);
        return new String(plaintext);
    }
}