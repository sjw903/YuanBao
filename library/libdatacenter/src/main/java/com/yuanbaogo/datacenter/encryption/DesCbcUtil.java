package com.yuanbaogo.datacenter.encryption;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesCbcUtil {
    // 加解密统一使用的编码方式
    private final static String encoding = "UTF-8";


    /**
     * 3des 密钥长度不得小于24
     */
    private static String desSecretKey = "b2c17a46e2b1415392aabyua2869856c";
    /**
     * 3des IV向量必须为8位
     */
    private static String desIv = "20210842";


    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return 加密后的文本，失败返回null
     */
    public static String encode(String plainText) {
        String result = null;
        try {
//            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(secretKey.getBytes());
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(desSecretKey.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(desIv.getBytes());
//            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            result = Base64Utils.encodeToString(encryptData);
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return 解密后明文，失败返回null
     */
    public static String decode(String encryptText) {
        String result = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(desSecretKey.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = secretKeyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(desIv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, desKey, ips);

            byte[] decryptData = cipher.doFinal(Base64Utils.decodeFromString(encryptText));

            result = new String(decryptData, encoding);

        } catch (Exception e) {

        }
        return result;
    }

}
