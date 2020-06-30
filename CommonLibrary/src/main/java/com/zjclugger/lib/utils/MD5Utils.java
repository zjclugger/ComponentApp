package com.zjclugger.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * Created by King on 2016/7/26.
 */
public class MD5Utils {
    private MD5Utils() {
    }

    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Utils.class.getName());
            nsaex.printStackTrace();
        }
    }

    /**
     * @param string 明文字符串
     * @return 加密后的字符串(32位)
     */
    public static String getMD5String(String string) {
        if (string.trim().length() == 0) {
            return "";
        }
        return getMD5String(string.getBytes());
    }

    /**
     * @param value     明文字符串
     * @param md5String MD5加密后的字符串
     * @return 相等为true
     */
    public static boolean equals(String value, String md5String) {
        return md5String.equals(getMD5String(value));
    }

    /**
     * @param filePath 文件路径
     * @return 文件的MD5
     * @throws IOException
     */
    public static synchronized String getFileMD5String(File filePath) throws IOException {
        InputStream fis;
        fis = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }

    public static String getMD5String(byte[] bytes) {
        if (0 == bytes.length) {
            return "";
        }
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];

        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
