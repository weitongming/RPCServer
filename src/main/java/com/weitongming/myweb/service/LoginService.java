package com.weitongming.myweb.service;


import com.weitongming.myweb.model.User;
import java.util.Map;


public interface LoginService {


    //注册
     String register(User user,String repassword);

    //登录
     Map<String,Object> login(User user);
     void activate(String activateCode);
}
