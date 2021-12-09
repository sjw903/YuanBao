package com.yuanbaogo.datacenter.encryption;

import android.text.TextUtils;

import com.yuanbaogo.libbase.baseutil.LogUtil;
import com.yuanbaogo.datacenter.utils.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    private static final String TAG = "EncryptUtil";
    private static String RSA = "RSA";
    private static final int MAX_DECRYPT_BLOCK = 128;
    public static String PUCLIC_KEY = "";
    private static final String HEX = "";
    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    public EncryptUtil() {
    }

    public static String toMd5(byte[] bytes) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException var2) {
            LogUtil.v("EncryptUtil", "toMd5():" + var2);
            throw new RuntimeException(var2);
        }
    }

    private static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        byte[] var3 = bytes;
        int var4 = bytes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            String hex = Integer.toHexString(255 & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex).append(separator);
        }

        return hexString.toString();
    }

    public static String desEncrypt(String content, String ivString, String keyString) {
        try {
            if (TextUtils.isEmpty(content)) {
                return null;
            } else {
                IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
                DESKeySpec dks = new DESKeySpec(keyString.getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey key = keyFactory.generateSecret(dks);
                Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                cipher.init(1, key, iv);
                byte[] result = cipher.doFinal(content.getBytes("utf-8"));
                return StringUtil.byteArr2HexStr(result);
            }
        } catch (Exception var9) {
            LogUtil.e("DESEncrypt", "ENCRYPT ERROR:" + var9);
            return null;
        }
    }

    public static String desEncrypt(String content) {
        try {
            if (TextUtils.isEmpty(content)) {
                return null;
            } else {
                IvParameterSpec iv = new IvParameterSpec("EbpU4WtY".getBytes());
                DESKeySpec dks = new DESKeySpec("vpRZ1kmU".getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey key = keyFactory.generateSecret(dks);
                Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                cipher.init(1, key, iv);
                byte[] result = cipher.doFinal(content.getBytes("utf-8"));
                return StringUtil.byteArr2HexStr(result);
            }
        } catch (Exception var7) {
            LogUtil.e("DESEncrypt", "ENCRYPT ERROR:" + var7);
            return null;
        }
    }

    public static String desDecrypt(String content, String ivString, String keyString) {
        try {
            if (TextUtils.isEmpty(content)) {
                return null;
            } else {
                IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
                DESKeySpec dks = new DESKeySpec(keyString.getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey key = keyFactory.generateSecret(dks);
                Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                cipher.init(2, key, iv);
                byte[] result = cipher.doFinal(StringUtil.hexStr2ByteArr(content));
                return new String(result, "utf-8");
            }
        } catch (Exception var9) {
            LogUtil.e("DESEncrypt", "ENCRYPT ERROR:" + var9);
            return null;
        }
    }

    public static String desDecrypt(String content) {
        try {
            if (TextUtils.isEmpty(content)) {
                return null;
            } else {
                IvParameterSpec iv = new IvParameterSpec("EbpU4WtY".getBytes());
                DESKeySpec dks = new DESKeySpec("vpRZ1kmU".getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey key = keyFactory.generateSecret(dks);
                Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                cipher.init(2, key, iv);
                byte[] result = cipher.doFinal(StringUtil.hexStr2ByteArr(content));
                return new String(result, "utf-8");
            }
        } catch (Exception var7) {
            LogUtil.e("DESEncrypt", "ENCRYPT ERROR:" + var7);
            return null;
        }
    }


    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
        cipher.init(1, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
        cipher.init(2, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];

        for(int i = 0; i < len; ++i) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }

        return result;
    }

    private static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        } else {
            StringBuffer result = new StringBuffer(3 * buf.length);

            for(int i = 0; i < buf.length; ++i) {
                appendHex(result, buf[i]);
            }

            return result.toString();
        }
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append("0123456789ABCDEFFFFAD".charAt(b >> 4 & 15)).append("0123456789ABCDEFFFFAD".charAt(b & 15));
    }

    public static byte[] rsaEncrypt(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(1, publicKey);
            InputStream in = new ByteArrayInputStream(data);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[117];
            int var7 = 0;

            int bufl;
            while((bufl = in.read(buf)) != -1) {
                ++var7;
                out.write(cipher.doFinal(buf, 0, bufl));
            }

            return out.toByteArray();
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static byte[] rsaDecrypt(byte[] encryptedData, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(2, publicKey);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
                byte[] cache;
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception var9) {
            return null;
        }
    }

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;

        while(i < len) {
            int b1 = data[i++] & 255;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 3) << 4]);
                sb.append("==");
                break;
            }

            int b2 = data[i++] & 255;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 3) << 4 | (b2 & 240) >>> 4]);
                sb.append(base64EncodeChars[(b2 & 15) << 2]);
                sb.append("=");
                break;
            }

            int b3 = data[i++] & 255;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[(b1 & 3) << 4 | (b2 & 240) >>> 4]);
            sb.append(base64EncodeChars[(b2 & 15) << 2 | (b3 & 192) >>> 6]);
            sb.append(base64EncodeChars[b3 & 63]);
        }

        return sb.toString();
    }


}
