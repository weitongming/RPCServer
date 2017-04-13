package com.weitongming.myweb.service;

import com.weitongming.myweb.model.User;

import java.util.List;



public interface UserService {

    User getProfile(int sessionUid, int uid);
    User getEditInfo(int uid);

    void updateUser(User user);

    void record(StringBuffer requestURL, String contextPath, String remoteAddr) ;

    List<User> listUserByTime();

    List<User> listUserByHot();

    void updateHeadUrl(int uid, String headUrl);

    void unfollow(int sessionUid, int uid) ;

    void follow(int sessionUid, int uid);

    boolean getFollowStatus(int sessionUid, int uid);

    String updatePassword(String password, String newpassword, String repassword, int sessionUid);

    //发送忘记密码确认邮件
    void forgetPassword(String email) ;
    void verifyForgetPassword(String code);
}

