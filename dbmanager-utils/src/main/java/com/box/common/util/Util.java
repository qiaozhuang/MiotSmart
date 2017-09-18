/*
 * 
 */

package com.box.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc

/**
 * The Class Util.
 * 
 * @ClassName: Util
 * @Description:
 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:33 Util.
 */
public class Util {
    // 随机类
    /** The random. */
    private static Random random;

    // byte buffer
    /** The baos. */
    private static ByteArrayOutputStream baos = new ByteArrayOutputStream();

    // string buffer
    /** The sb. */
    private static StringBuilder sb = new StringBuilder();
    /** The Constant BOX_LENGTH_KEY. */
    private final static int LENGTH_KEY = 20;

    /**
     * 判断SDCARD是否有效.
     * 
     * @return true, if is sDCARD mounted
     */
    public static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(status))
            return true;
        return false;
    }

    /**
     * 创建文件目录.
     * 
     * @param dir 如：/sdcard/log
     * @return the file
     */
    public static File mkDir(String dir) {
        if (isSDCARDMounted()) {
            File file = null;
            try {
                file = new File(dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 获取当前时间日期.
     * 
     * @param format 自定义格式,例：yyyy-MM-dd hh:mm:ss
     * @return the format time
     */
    public static String getFormatTime(String format) {
        Date date = new Date();
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat(format);
        String time = df.format(date);
        return time;
    }

    /**
     * 保存到本地.
     * 
     * @param bitmap the bitmap
     * @param localpath the localpath
     * @return the file
     */
    public static File saveBitmap(Bitmap bitmap, String localpath) {
        try {
            File f = new File(localpath);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the look up key.
     * 
     * @param value the value
     * @return String 返回类型
     * @Title: getLookUpKey
     * @Description:反转电话号码，最多返回11位string
     * @date 2012-11-7 下午2:10:57
     */
    public static String getLookUpKey(String value) {
        String result = "";
        if (isNotEmpty(value)) {
            result = reverse(value);
            if (result.length() > 11) {
                result = result.substring(0, 11);
            }
        }
        return result;
    }

    /**
     * Empty to n ull.
     * 
     * @param value the value
     * @return String 返回类型
     * @Title: EmptyToNUll
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @date 2012-11-13 上午11:40:34
     */
    public static String EmptyToNUll(String value) {
        String result = value;
        if (isEmpty(result)) {
            result = null;
        }
        return result;
    }

    /**
     * Reverse.
     * 
     * @param value the value
     * @return String 返回类型
     * @Title: reverse
     * @Description:String反转
     * @date 2012-11-7 下午2:10:10
     */
    public static String reverse(String value) {
        StringBuffer sb = new StringBuffer(value);
        sb = sb.reverse();
        return sb.toString();
    }

    /**
     * 判断String为空.
     * 
     * @param string the string
     * @return true, if is empty
     */
    public static boolean isEmpty(String string) {
        return (string == null || "".equalsIgnoreCase(string.trim()));
    }

    /**
     * 判断String不为空.
     * 
     * @param string the string
     * @return true, if is not empty
     */
    public static boolean isNotEmpty(String string) {
        return string != null && string.trim().length() > 0;
    }

    /**
     * 判断字符串类型数字还是其他的.
     * 
     * @param str the str
     * @return true, if is numeric
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 过滤空字符串或者以及去掉二头多有空格.
     * 
     * @param string the string
     * @return the string
     */
    public static String NullToString(String string) {
        if (isEmpty(string))
            return "";
        return string.trim();
    }

    /**
     * Replace all start end.
     * 
     * @param value the value
     * @param tag the tag
     * @return String 返回类型
     * @Title: replaceAllStartEnd
     * @Description: 去掉文本前后指定的字符 如 ：,1,2,3, 使用后为 1,2,3
     * @date 2012-11-27 上午9:13:12
     */
    public static String replaceAllStartEnd(String value, String tag) {
        String result = value;
        if (isNotEmpty(result)) {
            result = result.replaceAll(tag + tag, "");
            if (result.startsWith(tag)) {
                result = result.substring(1);
            }
            if (result.endsWith(tag)) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    // 16进制字符数组
    /** The hex. */
    private static char[] hex = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * 把字节数组从offset开始的len个字节转换成一个unsigned int， 因为java里面没有unsigned，所以unsigned
     * int使用long表示的， 如果len大于8，则认为len等于8。如果len小于8，则高位填0 <br>
     * 改变了算法, 性能稍微好一点. 在我的机器上测试10000次, 原始算法花费18s, 这个算法花费12s.
     * 
     * @param in 字节数组.
     * @param offset 从哪里开始转换.
     * @param len 转换长度, 如果len超过8则忽略后面的
     * @return the unsigned int
     */
    public static long getUnsignedInt(byte[] in, int offset, int len) {
        long ret = 0;
        int end = 0;
        if (len > 8)
            end = offset + 8;
        else
            end = offset + len;
        for (int i = offset; i < end; i++) {
            ret <<= 8;
            ret |= in[i] & 0xff;
        }
        return (ret & 0xffffffffl) | (ret >>> 32);
    }

    /**
     * 比较两个字节数组的内容是否相等.
     * 
     * @param b1 字节数组1
     * @param b2 字节数组2
     * @return true表示相等
     */
    public static boolean isByteArrayEqual(byte[] b1, byte[] b2) {
        if (b1.length != b2.length)
            return false;

        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i])
                return false;
        }
        return true;
    }

    /**
     * 判断IP是否全0.
     * 
     * @param ip the ip
     * @return true表示IP全0
     */
    public static boolean isIpZero(byte[] ip) {
        for (int i = 0; i < ip.length; i++) {
            if (ip[i] != 0)
                return false;
        }
        return true;
    }

    /**
     * Gets the file for byte.
     * 
     * @param file the file
     * @return byte[] 返回类型
     * @Title: getFileForByte
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @date 2012-11-23 下午3:20:55
     */
    public static byte[] getFileForByte(File file) {
        byte[] data = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            if (fis != null) {
                int len = fis.available();
                data = new byte[len];
                fis.read(data);
                fis.close();
                fis = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Gets the byte for file.
     * 
     * @param file the file
     * @param data the data
     * @return File 返回类型
     * @Title: getByteForFile
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @date 2012-11-23 下午5:03:05
     */
    public static File getByteForFile(File file, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();
            fos = null;
        } catch (Exception e) {
            file = null;
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 比较两个MD5是否相等.
     * 
     * @param m1 the m1
     * @param m2 the m2
     * @return true表示相等
     */
    public static boolean compareMD5(byte[] m1, byte[] m2) {
        if (m1 == null || m2 == null)
            return true;
        for (int i = 0; i < 16; i++) {
            if (m1[i] != m2[i])
                return false;
        }
        return true;
    }

    /**
     * 根据某种编码方式得到字符串的字节数组形式.
     * 
     * @param s 字符串
     * @param encoding 编码方式
     * @return 特定编码方式的字节数组，如果encoding不支持，返回一个缺省编码的字节数组
     */
    public static byte[] getBytes(String s, String encoding) {
        try {
            return s.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return s.getBytes();
        }
    }

    /**
     * 对原始字符串进行编码转换，如果失败，返回原始的字符串.
     * 
     * @param s 原始字符串
     * @param srcEncoding 源编码方式
     * @param destEncoding 目标编码方式
     * @return 转换编码后的字符串，失败返回原始字符串
     */
    public static String getString(String s, String srcEncoding, String destEncoding) {
        try {
            return new String(s.getBytes(srcEncoding), destEncoding);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    /**
     * Gets the string.
     * 
     * @param buf the buf
     * @param len the len
     * @param encoding the encoding
     * @return the string
     */
    public static String getString(ByteBuffer buf, int len, String encoding) {
        baos.reset();
        while (buf.hasRemaining() && len-- > 0) {
            baos.write(buf.get());
        }
        return getString(baos.toByteArray(), encoding);
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串.
     * 
     * @param b 字节数组
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, String encoding) {
        try {
            return new String(b, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b);
        }
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串.
     * 
     * @param b 字节数组
     * @param offset 要转换的起始位置
     * @param len 要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }

    /**
     * 把字符串转换成int.
     * 
     * @param s 字符串
     * @param faultValue 如果转换失败，返回这个值
     * @return 如果转换失败，返回faultValue，成功返回转换后的值
     */
    public static int getInt(String s, int faultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return faultValue;
        }
    }

    /**
     * 把字符串转换成long.
     * 
     * @param s 字符串
     * @param radix the radix
     * @param faultValue 如果转换失败，返回这个值
     * @return 如果转换失败，返回faultValue，成功返回转换后的值
     */
    public static long getLong(String s, int radix, long faultValue) {
        try {
            return Long.parseLong(s, radix);
        } catch (NumberFormatException e) {
            return faultValue;
        }
    }

    /**
     * 把字符串转换成int.
     * 
     * @param s 字符串
     * @param radix 基数
     * @param faultValue 如果转换失败，返回这个值
     * @return 如果转换失败，返回faultValue，成功返回转换后的值
     */
    public static int getInt(String s, int radix, int faultValue) {
        try {
            return Integer.parseInt(s, radix);
        } catch (NumberFormatException e) {
            return faultValue;
        }
    }

    /**
     * 检查字符串是否是整数格式.
     * 
     * @param s 字符串
     * @return true表示可以解析成整数
     */
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 把字符串转换成char类型的无符号数.
     * 
     * @param s 字符串
     * @param faultValue 如果转换失败，返回这个值
     * @return 如果转换失败，返回faultValue，成功返回转换后的值
     */
    public static char getChar(String s, int faultValue) {
        return (char)(getInt(s, faultValue) & 0xFFFF);
    }

    /**
     * 把字符串转换成byte.
     * 
     * @param s 字符串
     * @param faultValue 如果转换失败，返回这个值
     * @return 如果转换失败，返回faultValue，成功返回转换后的值
     */
    public static byte getByte(String s, int faultValue) {
        return (byte)(getInt(s, faultValue) & 0xFF);
    }

    /**
     * Gets the ip string from bytes.
     * 
     * @param ip ip的字节数组形式
     * @return 字符串形式的ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
        sb.delete(0, sb.length());
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    /**
     * 从ip的字符串形式得到字节数组形式.
     * 
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        StringTokenizer st = new StringTokenizer(ip, ".");
        try {
            ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 判断IP是否相等.
     * 
     * @param ip1 IP的字节数组形式
     * @param ip2 IP的字节数组形式
     * @return true如果两个IP相等
     */
    public static boolean isIpEquals(byte[] ip1, byte[] ip2) {
        return (ip1[0] == ip2[0] && ip1[1] == ip2[1] && ip1[2] == ip2[2] && ip1[3] == ip2[3]);
    }

    /**
     * 在buf字节数组中的begin位置开始，查找字节b出现的第一个位置.
     * 
     * @param buf 字节数组
     * @param begin 开始未知索引
     * @param b 要查找的字节
     * @return 找到则返回索引，否则返回-1
     */
    public static int indexOf(byte[] buf, int begin, byte b) {
        for (int i = begin; i < buf.length; i++) {
            if (buf[i] == b)
                return i;
        }
        return -1;
    }

    /**
     * 在buf字节数组中的begin位置开始，查找字节数组b中只要任何一个出现的第一个位置.
     * 
     * @param buf 字节数组
     * @param begin 开始未知索引
     * @param b 要查找的字节数组
     * @return 找到则返回索引，否则返回-1
     */
    public static int indexOf(byte[] buf, int begin, byte[] b) {
        for (int i = begin; i < buf.length; i++) {
            for (int j = 0; j < b.length; j++)
                if (buf[i] == b[j])
                    return i;
        }
        return -1;
    }

    /**
     * Random.
     * 
     * @return Random对象
     */
    public static Random random() {
        if (random == null)
            random = new Random();
        return random;
    }

    /**
     * Random key.
     * 
     * @return 一个随机产生的密钥字节数组
     */
    public static byte[] randomKey() {
        byte[] key = new byte[LENGTH_KEY];
        random().nextBytes(key);
        return key;
    }

    /**
     * 从content的offset位置起的4个字节解析成int类型.
     * 
     * @param content 字节数组
     * @param offset 偏移
     * @return int
     */
    public static final int parseInt(byte[] content, int offset) {
        return ((content[offset++] & 0xff) << 24) | ((content[offset++] & 0xff) << 16)
                | ((content[offset++] & 0xff) << 8) | (content[offset++] & 0xff);
    }

    /**
     * 从content的offset位置起的2个字节解析成char类型.
     * 
     * @param content 字节数组
     * @param offset 偏移
     * @return char
     */
    public static final char parseChar(byte[] content, int offset) {
        return (char)(((content[offset++] & 0xff) << 8) | (content[offset++] & 0xff));
    }

    /**
     * 把字节数组转换成16进制字符串.
     * 
     * @param b 字节数组
     * @return 16进制字符串，每个字节之间空格分隔，头尾无空格
     */
    public static String convertByteToHexString(byte[] b) {
        if (b == null)
            return "null";
        else
            return convertByteToHexString(b, 0, b.length);
    }

    /**
     * 把字节数组转换成16进制字符串.
     * 
     * @param b 字节数组
     * @param offset 从哪里开始转换
     * @param len 转换的长度
     * @return 16进制字符串，每个字节之间空格分隔，头尾无空格
     */
    public static String convertByteToHexString(byte[] b, int offset, int len) {
        if (b == null)
            return "null";

        // 检查索引范围
        int end = offset + len;
        if (end > b.length)
            end = b.length;

        sb.delete(0, sb.length());
        for (int i = offset; i < end; i++) {
            sb.append(hex[(b[i] & 0xF0) >>> 4]).append(hex[b[i] & 0xF]).append(' ');
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 把字节数组转换成16进制字符串.
     * 
     * @param b 字节数组
     * @return 16进制字符串，每个字节没有空格分隔
     */
    public static String convertByteToHexStringWithoutSpace(byte[] b) {
        if (b == null)
            return "null";

        return convertByteToHexStringWithoutSpace(b, 0, b.length);
    }

    /**
     * 把字节数组转换成16进制字符串.
     * 
     * @param b 字节数组
     * @param offset 从哪里开始转换
     * @param len 转换的长度
     * @return 16进制字符串，每个字节没有空格分隔
     */
    public static String convertByteToHexStringWithoutSpace(byte[] b, int offset, int len) {
        if (b == null)
            return "null";

        // 检查索引范围
        int end = offset + len;
        if (end > b.length)
            end = b.length;

        sb.delete(0, sb.length());
        for (int i = offset; i < end; i++) {
            sb.append(hex[(b[i] & 0xF0) >>> 4]).append(hex[b[i] & 0xF]);
        }
        return sb.toString();
    }

    /**
     * 转换16进制字符串为字节数组.
     * 
     * @param s 16进制字符串，每个字节由空格分隔
     * @return 字节数组，如果出错，返回null，如果是空字符串，也返回null
     */
    public static byte[] convertHexStringToByte(String s) {
        try {
            s = s.trim();
            StringTokenizer st = new StringTokenizer(s, " ");
            byte[] ret = new byte[st.countTokens()];
            for (int i = 0; st.hasMoreTokens(); i++) {
                String byteString = st.nextToken();

                // 一个字节是2个16进制数，如果不对，返回null
                if (byteString.length() > 2)
                    return null;

                ret[i] = (byte)(Integer.parseInt(byteString, 16) & 0xFF);
            }
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把一个16进制字符串转换为字节数组，字符串没有空格，所以每两个字符 一个字节.
     * 
     * @param s the s
     * @return the byte[]
     */
    public static byte[] convertHexStringToByteNoSpace(String s) {
        int len = s.length();
        byte[] ret = new byte[len >>> 1];
        for (int i = 0; i <= len - 2; i += 2) {
            ret[i >>> 1] = (byte)(Integer.parseInt(s.substring(i, i + 2).trim(), 16) & 0xFF);
        }
        return ret;
    }

    /**
     * 把ip的字节数组形式转换成字符串形式.
     * 
     * @param ip ip地址字节数组，big-endian
     * @return ip字符串
     */
    public static String convertIpToString(byte[] ip) {
        sb.delete(0, sb.length());
        for (int i = 0; i < ip.length; i++) {
            sb.append(ip[i] & 0xFF).append('.');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 从头开始（包含指定位置）查找一个字节的出现位置.
     * 
     * @param ar 字节数组
     * @param b 要查找的字节
     * @return 字节出现的位置，如果没有找到，返回-1
     */
    public static int findByteOffset(byte[] ar, byte b) {
        return findByteOffset(ar, b, 0);
    }

    /**
     * 从指定位置开始（包含指定位置）查找一个字节的出现位置.
     * 
     * @param ar 字节数组
     * @param b 要查找的字节
     * @param from 指定位置
     * @return 字节出现的位置，如果没有找到，返回-1
     */
    public static int findByteOffset(byte[] ar, byte b, int from) {
        for (int i = from; i < ar.length; i++) {
            if (ar[i] == b)
                return i;
        }
        return -1;
    }

    /**
     * 从指定位置开始（包含指定位置）查找一个字节的第N次出现位置.
     * 
     * @param ar 字节数组
     * @param b 要查找的字节
     * @param from 指定位置
     * @param occurs 第几次出现
     * @return 字节第N次出现的位置，如果没有找到，返回-1
     */
    public static int findByteOffset(byte[] ar, byte b, int from, int occurs) {
        for (int i = from, j = 0; i < ar.length; i++) {
            if (ar[i] == b) {
                j++;
                if (j == occurs)
                    return i;
            }
        }
        return -1;
    }

    /**
     * 把一个char转换成字节数组.
     * 
     * @param c 字符
     * @return 字节数组，2字节大小
     */
    public static byte[] convertCharToBytes(char c) {
        byte[] b = new byte[2];
        b[0] = (byte)((c & 0xFF00) >>> 8);
        b[1] = (byte)(c & 0xFF);
        return b;
    }

    /**
     * 从字节数组的指定位置起的len的字节转换成int型(big-endian)，如果不足4字节，高位认为是0.
     * 
     * @param b 字节数组
     * @param offset 转换起始位置
     * @param len 转换长度
     * @return 转换后的int
     */
    public static int getIntFromBytes(byte[] b, int offset, int len) {
        if (len > 4)
            len = 4;

        int ret = 0;
        int end = offset + len;
        for (int i = offset; i < end; i++) {
            ret |= b[i] & 0xFF;
            if (i < end - 1)
                ret <<= 8;
        }
        return ret;
    }

    /**
     * 得到一个字节数组的一部分.
     * 
     * @param b 原始字节数组
     * @param offset 子数组开始偏移
     * @param len 子数组长度
     * @return 子数组
     */
    public static byte[] getSubBytes(byte[] b, int offset, int len) {
        byte[] ret = new byte[len];
        System.arraycopy(b, offset, ret, 0, len);
        return ret;
    }

    /**
     * Gets the bytes.
     * 
     * @param buf the buf
     * @param length the length
     * @return the bytes
     */
    public static byte[] getBytes(ByteBuffer buf, int length) {
        byte[] bytes = new byte[length];
        buf.get(bytes);
        return bytes;
    }

    /**
     * 验证是否是手机号码.
     * 
     * @param str the str
     * @return true, if is mobile
     */
    public static boolean isMobile(String str) {
        if (str == null)
            return false;
        boolean flag = false;
        String rex = "^1[3,5,8]\\d{9}$";
        if (str.matches(rex)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断字符串中是否只含空格和回车，如果为空也返回true.
     * 
     * @param str the str
     * @return true, if is string only contain space and enter
     */
    public static boolean isStringOnlyContainSpaceAndEnter(String str) {
        if (null == str) {
            return true;
        }
        if (str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!(ch == ' ' || ch == '\r' || ch == '\n')) {
                return false;
            }
        }
        return true;
    }

    /**
     * new一个string.
     * 
     * @param string the string
     * @return the string
     */
    public static String newString(String string) {
        if (null == string) {
            return null;
        } else {
            return new String(string);
        }
    }

    /**
     * Gets the media len.
     * 
     * @param context the context
     * @param audioFile the audio file
     * @return int 返回类型
     * @Title: getMediaLen
     * @Description:获取音频播放时长
     * @date 2013-1-8 下午6:55:52
     */
    public static int getMediaLen(Context context, File audioFile) {
        int mediaLen = 0;
        try {
            MediaPlayer mMediaPlayer = MediaPlayer.create(context, Uri.fromFile(audioFile));
            mediaLen = mMediaPlayer.getDuration();
            mMediaPlayer.release();
            mMediaPlayer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaLen;
    }

    /**
     * 过滤号码 去除 +86 和 -.
     * 
     * @param telString the tel string
     * @return the string
     */
    public static String filterTelNum(String telString) {

        String retString = telString;
        if (telString.indexOf("+86") == 0) {
            retString = telString.substring(3);
        } else if (telString.indexOf("86") == 0) {
            retString = telString.substring(2);
        } else {
            int off = telString.indexOf("-");
            if (off != -1) {
                retString = telString.replace("-", "");
            }
        }

        return retString;
    }

    public static String readLocalJson(Context context,  String fileName){
        String jsonString="";
        String resultString="";
        try {
            InputStream inputStream=context.getResources().getAssets().open(fileName);
            byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString=new String(buffer,"UTF-8");
        } catch (Exception e) {
        }
        return resultString;
    }

}
