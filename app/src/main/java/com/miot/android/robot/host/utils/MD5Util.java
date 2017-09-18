
package com.miot.android.robot.host.utils;


public class MD5Util {

    public static String getMD5(String source, String charset) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source.getBytes(charset));
            byte tmp[] = md.digest(); //
            char str[] = new char[16 * 2];
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
