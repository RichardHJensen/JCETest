package com.rhjensen.encryption;

import org.apache.commons.codec.binary.Base64;

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
    private Base64 base64codec;

    public AESEncryptor(String sessionKey, String iv) {
        byte[] keyBytes;
        byte[] vectorBytes;
        try {
            base64codec = new Base64();
            keyBytes = base64codec.decode(sessionKey);
            vectorBytes = base64codec.decode(iv);
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
        }
    }

    public String encrypt(String plainText) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // get bytes from string, encrypt, encode
        byte[] utf8bytes = plainText.getBytes("utf-8");
        byte[] ciphertext = encryptor.doFinal(utf8bytes);

        return base64codec.encodeToString(ciphertext);
    }

    public String decrypt(String cipherText) throws IOException, IllegalBlockSizeException, BadPaddingException {
        // decode, decrypt, use bytes to create string
        byte[] encryptedBytes = base64codec.decode(cipherText);
        byte[] plaintext = decryptor.doFinal(encryptedBytes);
        return new String(plaintext);
    }
}