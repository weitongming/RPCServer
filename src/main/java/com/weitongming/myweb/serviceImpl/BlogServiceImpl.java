package com.weitongming.myweb.serviceImpl;

import com.weitongming.myweb.mapper.BlogMapper;
import com.weitongming.myweb.model.BlogWithUserInfo;
import com.weitongming.myweb.service.BlogService;
import com.weitongming.myweb.util.Config;
import com.weitongming.rpc.server.RpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */
@RpcService(BlogService.class)
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogMapper blogMapper;
    @Override
    public List<BlogWithUserInfo> getBlogListByTime(int currentPage) {
        List<BlogWithUserInfo> blogWithUserInfoList = blogMapper.queryBlogWithUserInfoWithLimitByTime((currentPage - 1)*Config.pageSize,currentPage* Config.pageSize);
        return blogWithUserInfoList;
    }

    @Override
    public BlogWithUserInfo getBlogDetail(int bid) {
        return blogMapper.getBlogWithUserInfo(bid);
    }
}
