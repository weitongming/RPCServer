package com.weitongming.myweb.mapper;

import com.weitongming.myweb.model.BlogWithUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */
public interface BlogMapper {
    public List<BlogWithUserInfo> queryBlogWithUserInfoWithLimitByTime(@Param("start") int start ,@Param("offset") int offset);
    public BlogWithUserInfo getBlogWithUserInfo(int bid);
}
