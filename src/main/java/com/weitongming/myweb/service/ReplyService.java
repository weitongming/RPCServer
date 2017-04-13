package com.weitongming.myweb.service;

import com.weitongming.myweb.model.Reply;

import java.util.List;



public interface ReplyService {

    //回复
    void reply(int sessionUid, int pid, String content) ;

    //评论
    void comment(int pid,int sessionUid, int rid, String content) ;
    //根据pid列出回复
    List<Reply> listReply(int pid) ;
}

