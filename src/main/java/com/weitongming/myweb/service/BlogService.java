package com.weitongming.myweb.service;

import com.weitongming.myweb.model.BlogWithUserInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */
public interface BlogService {
    public List<BlogWithUserInfo> getBlogListByTime(int currentPage);
    public BlogWithUserInfo getBlogDetail(int bid);
}
