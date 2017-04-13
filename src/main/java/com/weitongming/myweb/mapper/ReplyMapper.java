package com.weitongming.myweb.mapper;

import com.weitongming.myweb.model.Comment;
import com.weitongming.myweb.model.Reply;

import java.util.List;


public interface ReplyMapper {

    void insertReply(Reply reply);

    List<Reply> listReply(int pid);

    void insertComment(Comment comment);

    List<Comment> listComment(Integer rid);

    String getContentByRid(int rid);

}
