package com.debug52.utils.util;

import org.apache.tomcat.util.buf.HexUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author system
 */
public class DESUtils {

    /**
     * 对上传字符串进行DES加密并Base64
     *
     * @param content 数据
     * @param key     DES-KEY
     * @return DES+Base64
     */
    public static String encrypt(String content, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, random);
            return Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对获得字符串进行DES解密并解Base64
     *
     * @param content 数据
     * @param key     DES-KEY
     * @return normal data
     */
    public static String decryptFromHex(String content, String key) {
        try {
            byte[] decryHex = HexUtils.fromHexString(content);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
            byte[] result = cipher.doFinal(decryHex);
            return new String(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对获得字符串进行DES解密并解Base64
     *
     * @param content 数据
     * @param key     DES-KEY
     * @return normal data
     */
    public static String decrypt(String content, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
