package com.kaiwen.base.common.utils;

/**
 * @Class HexStringUtils
 * @Date 2019/5/21
 */
public class HexStringUtils {

    private static final char[] DIGITS_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_HEX[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_HEX[0x0F & data[i]];
        }
        return out;
    }

    private static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("字符个数应该为偶数");
        }
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f |= toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }



    /**
     * 字符串 转 16进制字符串
     */
    public static String toHex(String str) {
        return new String(encodeHex(str.getBytes()));
    }


    /**
     * 16进制字符串 转字符串
     */
    public static String fromHex(String hex) {
        return new String(decodeHex(hex.toCharArray()));
    }


    /**
     * 16进制字符串 转 10进制
     */
    public static int hex2decimal(String hex) {
        return Integer.parseInt(hex, 16);
    }


    /**
     * 10进制 转 16进制字符串(大写)
     */
    public static String demical2Hex(int i) {
        return Integer.toHexString(i).toUpperCase();
    }


    /**
     * 16进制字符串 转 2进制字符串
     */
    public static String hexStringToByte(String hex) {
        int i = Integer.parseInt(hex, 16);
        return Integer.toBinaryString(i);
    }


    /**
     * 字节数组 转16进制字符串
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte aBArray : bArray) {
            sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 16进制字符串 转 字节数组
     */
    public static byte[] hexStringToBytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * 16进制字符串 转 2进制字符串
     */
    public static String byteToBitString32(String str) {
        String s = HexStringUtils.hexStringToByte(str);
        if ("0".equals(s)){
            return  "00000000000000000000000000000000";
        }
        if(s.length() != 32){
            String temp = "";
            for (int i=0 ;i < 32 - s.length();i++){
                temp += "0";
            }
            s  = temp + s;
        }
        return s;
    }
}
