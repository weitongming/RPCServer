package com.weitongming.myweb.service;


import com.weitongming.myweb.model.Message;
import java.util.List;
import java.util.Map;


public interface MessageService {


    //获得消息列表
    Map<String, List<Message>> listMessageByUid(Integer sessionUid) ;
}
