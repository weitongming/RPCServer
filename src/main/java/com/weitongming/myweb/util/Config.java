package com.weitongming.myweb.util;

/**
 * Created by Administrator on 2017/4/3.
 */
public class Config {
    public static int REDIS_EXPIRE_TIME_MILLSECOND = 200;
    public static int REDIS_EXPIRE_TIME_1S = 1;
    public static int REDIS_EXPIRE_TIME_2S = 2;
    public static int REDIS_EXPIRE_TIME_3S = 3;
    public static int REDIS_EXPIRE_TIME_4S = 4;
    public static int REDIS_EXPIRE_TIME_5S = 5;
    //缓存根据用户ID取到的消息
    public static String REDIS_MESSAGE_UID = "REDIS_MESSAGE_UID:";
    //缓存按时间列出帖子
    public static String REDIS_POST_BY_TIME_PAGE = "REDIS_POST_BY_TIME_PAGE:";
    //缓存帖子数量
    public static String REDIS_ALL_POST_NUM = "REDIS_ALL_POST_NUM";
    //
    public static int pageSize = 10;
    public static String REDIS_ALL_POST_NUM_TOPIC_ = "REDIS_ALL_POST_NUM_TOPIC_";
}
