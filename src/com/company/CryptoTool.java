package com.company;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoTool {
    private byte[] key;
    public CryptoTool(String key) throws Exception {
        byte[] keyByte = key.getBytes("ASCII");
        if(keyByte.length != 16)
            throw new Exception("key length error");
        this.key = keyByte.clone();
    }


    public byte[] crypt(byte[] text) throws Exception {
        byte[] crypt = null;
        try {
            SecretKey aesKey = new SecretKeySpec(key, "AES");
            final Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(key));
            crypt = cipher.doFinal(text);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return crypt;
    }

    public byte[] decrypt(byte[] text) throws Exception {
        SecretKey aesKey = new SecretKeySpec(key, "AES");
        final Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(key));
        return cipher.doFinal(text);
    }
}