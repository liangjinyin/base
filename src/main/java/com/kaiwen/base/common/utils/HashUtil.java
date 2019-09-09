package com.kaiwen.base.common.utils;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author: liangjinyin
 * @Date: 2019-08-22
 * @Description:
 */
public class HashUtil {

    /**
     * 不带虚拟节点的一致性Hash算法
     */
    //待添加入Hash环的服务器列表 95.631071 122.237948 108.631071 116.631071
    private static String[] servers = {"95.631071", "108.631071", "116.631071", "122.237948"};

    //key表示服务器的hash值，value表示服务器
    private static SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();

    //程序初始化，将所有的服务器放入sortedMap中
    static {
        for (int i = 0; i < servers.length; i++) {
            int hash = getHash(servers[i]);
            System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, servers[i]);
        }
        System.out.println();
    }

    //得到应当路由到的结点
    private static String getServer(String key) {
        //得到该key的hash值
        int hash = getHash(key);
        //得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = sortedMap.firstKey();
            //返回对应的服务器
            return sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
    }

    //使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    public static void main(String[] args) {
        String[] keys = {"太阳", "月亮", "星星"};
        for (int i = 0; i < keys.length; i++)
            System.out.println("[" + keys[i] + "]的hash值为" + getHash(keys[i])
                    + ", 被路由到结点[" + getServer(keys[i]) + "]");
    }

    public static String getDB(String key) {
        return getServer(key);
    }

    // 长江 头部：121.96473538190111, 31.683295381234334 尾部：104.6145587852267, 28.759875901599752
    public static Integer getPGDB(Double lon) {
        final Double min = 104.6145587852267;
        final Double max = 121.96473538190111;
        if (lon == null || lon < min || lon > max) {
            return 0;
        }
        Double svg = (max - min) / 4;
        if (lon <= min + svg) {
            return 1;
        } else if (lon <= min + svg * 2) {
            return 2;
        } else if ( lon <= min + svg * 3) {
            return 3;
        }
        return 4;
    }
}
