package com.yuanbaogo.datacenter.encryption;

import java.nio.charset.Charset;
import java.util.Base64;

public class Base64Utils {


    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");


    public static String encodeToString(byte[] src) {
        if (src.length == 0) {
            return "";
        }
        return new String(encode(src), DEFAULT_CHARSET);
    }

    public static byte[] decodeFromString(String src) {
        if (src.isEmpty()) {
            return new byte[0];
        }
        return decode(src.getBytes(DEFAULT_CHARSET));
    }


    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getEncoder().encode(src);
    }

    /**
     * Base64-decode the given byte array.
     * @param src the encoded byte array
     * @return the original byte array
     */
    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
//        Log.e("yf","===== "+Base64.getDecoder().decode(src.replace("\r\n", "")));
        return Base64.getDecoder().decode(src);
    }
}
