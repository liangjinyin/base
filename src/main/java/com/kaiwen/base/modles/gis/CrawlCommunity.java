package com.kaiwen.base.modles.gis;

import com.kaiwen.base.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: liangjinyin
 * @Date: 2019-04-01
 * @Description: 爬取小区数据
 */
@Slf4j
public class CrawlCommunity {

    private static final String CRAWL_URL = "https://map.baidu.com/?ugc_type=3&ugc_ver=1&qt=detailConInfo&device_ratio=2&compat=1&t=1554100284590&uid=%s";

    public static void crawlCom(String uid) {
        String url = String.format(CrawlCommunity.CRAWL_URL, uid);
        String response = HttpUtils.get(url);
        log.info("得到的响应：", response);
    }

    public static void main(String[] args) {
        CrawlCommunity.crawlCom("b597668a4d4063d5f90cad5e");
    }
}
