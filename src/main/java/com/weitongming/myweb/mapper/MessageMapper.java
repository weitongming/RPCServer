package com.weitongming.myweb.mapper;

import com.weitongming.myweb.model.Message;

import java.util.List;


public interface MessageMapper {

    void insertMessage(Message message);

    List<Message> listMessageByUid(Integer uid);


}
