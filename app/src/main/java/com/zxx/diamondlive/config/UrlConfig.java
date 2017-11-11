package com.zxx.diamondlive.config;

/**
 * date: Created xiaoyuan on 16/11/05.
 */
public class UrlConfig {

    public static String TestHostUrl;

    // 0测试环境
    public static final int Test = 0;
    // 1线上环境
    public static final int Online = 1;

    static {
        switch (Online) {
            case Test:
                TestHostUrl = "http://121.42.26.175:14444/";
                break;
            case Online:
                TestHostUrl = "http://121.42.26.175:14444/";
                break;
        }
    }


    public final static String API_V1_0_TEST = "contact/company/list.json";
    public final static String HOME_API = "contact/company/list.json";


    public final static String UTIL_FILE_API = "util/file/upload.json";
}
