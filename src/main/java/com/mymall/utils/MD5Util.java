package com.mymall.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * Created by HUA on 2018/5/27.
 */
public class MD5Util {


    public static String MD5EncodeUtf8(String origin) {
        //MD5加盐值
        origin = origin + PropertisUtil.getProperty("password.salt", "");
        return MD5EncodeUtf8(origin, "utf-8");
    }

    /**
     * 返回大写的MD5值
     *
     * @param origin
     * @param charset
     * @return
     */
    private static String MD5EncodeUtf8(String origin, String charset) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtils.isBlank(charset)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charset)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString.toUpperCase();

    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

}
