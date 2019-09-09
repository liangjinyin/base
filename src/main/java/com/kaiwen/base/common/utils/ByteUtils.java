package com.kaiwen.base.common.utils;

import io.netty.buffer.ByteBuf;
import org.springframework.util.StringUtils;

/**
 * @author: liangjinyin
 * @Date: 2019-08-28
 * @Description:
 */
public class ByteUtils {

    /**
     * 获取一个字节的bit数组
     *
     * @param value
     * @return
     */
    public static byte[] getByteArray(byte value) {
        byte[] byteArr = new byte[8]; //一个字节八位
        for (int i = 7; i > 0; i--) {
            byteArr[i] = (byte) (value & 1); //获取最低位
            value = (byte) (value >> 1); //每次右移一位
        }
        return byteArr;
    }

    /**
     * 把byte转为字符串的bit
     *
     * @param b
     * @return
     */
    public static String byteToBitString(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    /**
     * 获取一个字节第n位,
     * 思路：右移n位，与1
     *
     * @param value
     * @param index
     * @return
     */
    public static int get(byte value, int index) {
        return (value >> index) & 0x1;
    }

    /**
     * 获取一个字节的第m到第n位
     *
     * @param value
     * @param start >0
     * @param end   >0
     * @return
     */
    public static byte[] getBitRange(byte value, int start, int end) {
        byte[] rangeArray = new byte[end - start + 1];
        if (start > 7 || start < 0) {
            throw new RuntimeException("illegal start param");
        }
        if (end > 7 || end < 0) {
            throw new RuntimeException("illegal end param");
        }
        if (start > end) {
            throw new RuntimeException("start can not bigger than end");
        }
        if (start == end) {
            rangeArray[0] = (byte) ByteUtils.get(value, start);
            return rangeArray;
        }
        for (int i = end; i < start; i--) {
            rangeArray[i] = (byte) ByteUtils.get(value, start);
        }
        return rangeArray;
    }

    /**
     * 位字符串转字节
     *
     * @param str
     * @return
     */
    public static byte bitStringToByte(String str) {
        if (null == str) {
            throw new RuntimeException("when bit string convert to byte, Object can not be null!");
        }
        if (8 != str.length()) {
            throw new RuntimeException("bit string'length must be 8");
        }
        try {
            //判断最高位，决定正负
            if (str.charAt(0) == '0') {
                return (byte) Integer.parseInt(str, 2);
            } else if (str.charAt(0) == '1') {
                return (byte) (Integer.parseInt(str, 2) - 256);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("bit string convert to byte failed, byte String must only include 0 and 1!");
        }

        return 0;
    }

    /**
     * <编码>
     * <数字字符串编成BCD格式字节数组>
     *
     * @param bcd 数字字符串
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static byte[] str2bcd(String bcd) {
        if (StringUtils.isEmpty(bcd)) {
            return null;
        } else {
            // 获取字节数组长度
            int size = bcd.length() / 2;
            int remainder = bcd.length() % 2;

            // 存储BCD码字节
            byte[] bcdByte = new byte[size + remainder];

            // 转BCD码
            for (int i = 0; i < size; i++) {
                int low = Integer.parseInt(bcd.substring(2 * i, 2 * i + 1));
                int high = Integer.parseInt(bcd.substring(2 * i + 1, 2 * i + 2));
                bcdByte[i] = (byte) ((high << 4) | low);
            }

            // 如果存在余数，需要填F
            if (remainder > 0) {
                int low = Integer.parseInt(bcd.substring(bcd.length() - 1));
                bcdByte[bcdByte.length - 1] = (byte) ((0xf << 4) | low);
            }

            // 返回BCD码字节数组
            return bcdByte;
        }
    }

    /**
     * <解码>
     * <BCD格式的字节数组解成数字字符串>
     *
     * @param bcd 字节数组
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String bcd2str(byte[] bcd) {
        if (null == bcd || bcd.length == 0) {
            return "";
        } else {
            // 存储转码后的字符串
            StringBuilder sb = new StringBuilder();

            // 循环数组解码
            for (int i = 0; i < bcd.length; i++) {
                // 转换低字节
                int low = (bcd[i] & 0x0f);
                sb.append(low);

                // 转换高字节
                int high = ((bcd[i] & 0xf0) >> 4);

                // 如果高字节等于0xf说明是补的字节，直接抛掉
                if (high != 0xf) {
                    sb.append(high);
                }
            }

            // 返回解码字符串
            return sb.toString();
        }
    }

    public static int BCDToInt(byte bcd) //BCD转十进制
    {
        return (0xff & (bcd >> 4)) * 10 + (0xf & bcd);
    }

    public static int IntToBCD(byte i) //十进制转BCD
    {
        return ((i / 10) << 4 + ((i % 10) & 0x0f));
    }

    static int bcd_decimal(char bcd) {
        return bcd - (bcd >> 4) * 6;
    }

    public static String byteToBitString2(String str) {
        if (str.length() == 2) {
            String i1 = Integer.parseInt(str.substring(0, 1), 16) + "";
            String i2 = Integer.parseInt(str.substring(1, 2), 16) + "";
            String s = byteToBitString(Byte.valueOf(i1)).substring(4, 8);
            String s1 = byteToBitString(Byte.valueOf(i2)).substring(4, 8);
            return s + s1;
        }
        return "";
    }

    public static String byteToBitString4(String str) {
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

    public String byteBE2Float(byte[] bytes) {
        int l;
        l = bytes[0];
        l &= 0xff;
        l |= ((long) bytes[1] << 8);
        l &= 0xffff;
        l |= ((long) bytes[2] << 16);
        l &= 0xffffff;
        l |= ((long) bytes[3] << 24);
        return Float.intBitsToFloat(l) + "";
    }

    /**
     * BCD字节数组===>String
     *
     * @return 十进制字符串
     */
    public static String bcd2String(Object msg) {

        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            // 高四位
            temp.append((bytes[i] & 0xf0) >>> 4);
            // 低四位
            temp.append(bytes[i] & 0x0f);
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] longToBytes(long value, int len) {
        byte[] result = new byte[len];
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result[i] += (value & 0x0ff);
            } else {
                result[i] += (value >>> temp) & 0x0ff;
            }
        }
        return result;
    }


    /**
     *  16进制字符串转为16进制
     *  @param hex 16进制的字符串
     *   @return
     */
    public static byte[] hexString2Bytes(String hex) {
        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    public static void main(String[] args) {
        String s = byteToBitString4("000c0003");
        String s1 = HexStringUtils.hexStringToByte("000c0003");
 /*       Integer integer = Integer.valueOf("000c0003");
        System.out.println(Integer.toBinaryString(integer));*/
        System.out.println(s1.length());
    }
}
