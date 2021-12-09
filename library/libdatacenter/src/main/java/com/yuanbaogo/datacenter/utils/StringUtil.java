package com.yuanbaogo.datacenter.utils;

import java.io.UnsupportedEncodingException;

public class StringUtil {
    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static int stringToInt(String str) {
        if (isEmpty(str)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException var2) {
                return 0;
            }
        }
    }

    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        StringBuilder sb = new StringBuilder(iLen * 2);

        for(int i = 0; i < iLen; ++i) {
            int intTmp;
            for(intTmp = arrB[i]; intTmp < 0; intTmp += 256) {
            }

            if (intTmp < 16) {
                sb.append("0");
            }

            sb.append(Integer.toString(intTmp, 16));
        }

        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];

        for(int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
        }

        return arrOut;
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();

        for(int i = 0; i < bs.length; ++i) {
            int bit = (bs[i] & 240) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 15;
            sb.append(chars[bit]);
        }

        return sb.toString();
    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            int n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte)(n & 255);
        }

        return new String(bytes);
    }

    public static String byte2HexStr(byte[] bytes) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < bytes.length; ++n) {
            stmp = Integer.toHexString(bytes[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0);
        b0 = (byte)(b0 << 4);
        byte b1 = Byte.decode("0x" + src1);
        byte ret = (byte)(b0 | b1);
        return ret;
    }


    public static String stringToUnicode(String strText) throws Exception {
        String strRet = "";

        for(int i = 0; i < strText.length(); ++i) {
            char c = strText.charAt(i);
            String strHex = Integer.toHexString(c);
            if (c > 128) {
                strRet = strRet + "\\u" + strHex;
            } else {
                strRet = strRet + "\\u00" + strHex;
            }
        }

        return strRet;
    }

    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < t; ++i) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            String s1 = s.substring(2, 4) + "00";
            String s2 = s.substring(4);
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }

        return str.toString();
    }

    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");

        for(int i = 1; i < hex.length; ++i) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char)data);
        }

        return string.toString();
    }

    public static String gbk2utf8(String gbk) {
        String utf8 = "";

        try {
            utf8 = new String(gbk.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return utf8;
    }

    public static boolean notNull(String str) {
        return str != null && !"".equals(str) && !"null".equals(str);
    }

    public static boolean isNull(String str) {
        return str == null || "".equals(str) || "null".equals(str);
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
    }


}
