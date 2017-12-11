package com.ruitukeji.zwbh.constant;

/**
 * 用于存放字符串常量的类
 * Created by ruitu ck on 2016/9/14.
 */

public class StringConstants {
    /**
     * 存放的文件名
     */
    public static String FILENAME = "wztxhShare";
    /**
     * 文件缓存路径存放的文件名-----图片以及URL请求缓存路径
     */
    public static String CACHEPATH = "WZTXH/Cache";
    /**
     * 图片缓存路径 存放的文件名
     */
    public static String PHOTOCACHE = "WZTXH/PhotoCache";
    /**
     * 图片保存路径存放的文件名
     */
    public static String PHOTOPATH = "WZTXH/PhonePhoto";
    /**
     * 下载文件保存路径的文件名
     */
    public static String DOWNLOADPATH = "WZTXH/Download";
    /**
     * 错误日志存放位置ERRORLOG
     */
    public static String ERRORLOG = "WZTXH/PhoneLog";

    // 图片缓存最大容量，150M，根据自己的需求进行修改
    public static final int GLIDE_CATCH_SIZE = 550 * 1000 * 1000;
    // 图片压缩，5M，根据自己的需求进行修改(单位bt)
    public static final long COMPRESSION_SIZE = 2 * 1000 * 1000;
    /**
     * 微信
     */
    public static String WEIXINAppKey = "wxc50f5bf05f014dee";
    public static String WEIXINAppSecret = "b7e5381ce6de433431e123f936492f08";
    /**
     * 腾讯QQ
     */
    public static String QQAppID = "1106079304";
    public static String QQAppKey = "CkUkdnUcUcoiYE8T";
    /**
     * 高德地图 云图表tableid
     * <p>
     * NearTableid   附近周边表
     * LogisticsPositioningTableid   物流轨迹表
     */
    public static String NearTableid = "596892e77bbf190cbd32058b";
    public static String LogisticsPositioningTableid = "5950e0947bbf190cbdfb5e46";


    /**
     * 支付宝
     */
    // 商户PID
    public static final String PARTNER = "2088221846698385";
    // 商户收款账号
    public static final String SELLER = "286166462@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICXAIBAAKBgQCh8rQvj/7pS6J+8G7hEqhBaspHm4Lj3XTM7WBAsDrCdAvbuDUYBUjuIV81gwkfzdKvjfCONK1bkrIDYUYZJ8YktLsrkPA2Tei7MxdjdaWv+EhL9DxvU1py4c7dvhmP/as1RftwL2P5vwFrb0Yf5zW6ckE/R7Yu23nYpTFcX9wh/wIDAQABAoGAFRQT48ToNtFDWyZMKDtXbvxJS7yv8pyWYT3cM088tqaIDJcTQgceEPCDBeICRAN0Eql0z+2HSs3zIYF9sDR/E37jrMKn21hwX7UFZ2+GJS75XAhthcvMeVgVRnv+F0rIWk5iM1abZN4Zxt13v3Wt9Ld9fO1bZ9ghKJKcceRj5MECQQDXglKijsbATR6lD7DKuvfvkNMrNR8YP+Q2cDoPUzWOKtGYlQglgpWhPem09nxMKFTTvgg7vGSV9+CYNACMop7RAkEAwGAsWpWYma5VoBBT3N5z7xIEWJ40o/TjZ+J+bhy/m96X74fZCdvDv7AHuQg+TaO+aRXmWZN5X7U1OLdbMyYHzwJAYShl7NeFjyi1iiDayWslYCHgsfgO/rW/QzOxWTtgR6nSOIHn3FFU+A6bkHQXcZY1OAjSWWwDxQCthrDbFowyIQJAOcPnLaAj/Wmi3nhopjbJ+yyez3lZ9y2Op2AUdL3/Ly+s0ckp+9LYuZODDiWSh7+2almgHJ+y33FLNOeh4/70cwJBAIkglCePAl0/mRY/48F70FC3Mh9T1J7ObHkBfdUqLgZOZ7xFnXFKyOcRC3vasORVoX5D7xUxaEpAQFU2b/mTLAc=";

    public static String ZHANGHUMING = "怎么吃账户充值";
}
