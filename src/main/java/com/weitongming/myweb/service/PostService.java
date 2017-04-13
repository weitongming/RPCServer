package com.weitongming.myweb.service;


import com.weitongming.myweb.model.PageBean;
import com.weitongming.myweb.model.Post;

import java.util.List;


public interface PostService {

    //根据uid，获得帖子列表
    public List<Post> getPostList(int uid);

    public int publishPost(Post post) ;

    //按时间列出帖子
    public PageBean<Post> listPostByTime(int curPage);
    public PageBean<Post> listPostByTimeAndType(int page, int tid);
    public Post getPostByPid(int pid);

    //点赞
    public String clickLike(int pid, int sessionUid) ;

    //某用户是否赞过某帖子
    public boolean getLikeStatus(int pid, int sessionUid);

    public PageBean<Post> search(String keyworld,int curPage);
}

