/*
 * 
 */

package com.box.common.util;

// TODO: Auto-generated Javadoc
/**
 * 字符串常用类.
 *
 * @author qiyan
 */
public class StringUtils {

    /**
     * Gets the character num.
     *
     * @param content the content
     * @return the character num
     * @description 获取一段字符串的字符个数（包含中英文，一个中文算2个字符）
     */
    public static int getCharacterNum(final String content) {
        if (null == content || "".equals(content)) {
            return 0;
        } else {
            return (content.length() + getChineseNum(content));
        }
    }

    /**
     * Gets the chinese num.
     *
     * @param s the s
     * @return the chinese num
     * @description 返回字符串里中文字或者全角字符的个数
     */
    public static int getChineseNum(String s) {

        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char)(byte)myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s the s
     * @return the string
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static String getNameByUrl(String url){
        String temp[] = url.replaceAll("\\\\", "/").split("/");
        if(temp.length>0){
            return temp[temp.length - 1];
        }
        return "";
    }
}
