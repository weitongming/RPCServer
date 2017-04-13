package com.weitongming.myweb.util;


public class MyConstant {

    public static final String NGINX_LOCATION = "C:\\Users\\Administrator\\Desktop\\nginx-1.8.1\\";
    public static final String NGINX_STATIC_FILE_PATH = "html\\resources\\image\\";
    public static final String HEAD_IMAGE_LOCATION = NGINX_LOCATION + NGINX_STATIC_FILE_PATH;

    //发送邮件的邮箱，要与df.properties中的一致
    public static final String MAIL_FROM = "weitongming@yeah.net";

    //域名
    public static final String DOMAIN_NAME = "http://localhost:8080/";

    //三种操作
    public static final int OPERATION_CLICK_LIKE = 1;
    public static final int OPERATION_REPLY = 2;
    public static final int OPERATION_COMMENT = 3;

}
