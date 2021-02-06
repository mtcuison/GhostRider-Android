package org.rmj.g3appdriver.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author sayso
 */
public class MySQLAESCrypt {
    private static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
        try {
            final byte[] finalKey = new byte[16];

            int i = 0;

            for(byte b : key.getBytes(encoding))
                finalKey[i++%16] ^= b;

            return new SecretKeySpec(finalKey, "AES");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String Encrypt(String data, String key){
        try {
            Cipher encryptCipher = Cipher.getInstance("AES");

            encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(key, "UTF-8"));

            String ret = new String(Hex.encodeHex(encryptCipher.doFinal(data.getBytes(StandardCharsets.UTF_8))));

            return ret.toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (NoSuchPaddingException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
        } catch (BadPaddingException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static String Decrypt(String data, String key){
        try {
            Cipher decryptCipher = Cipher.getInstance("AES");

            decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(key, "UTF-8"));

            return new String(decryptCipher.doFinal(Hex.decodeHex(data.toCharArray())));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (NoSuchPaddingException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
        } catch (BadPaddingException ex) {
            ex.printStackTrace();
        } catch (DecoderException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}