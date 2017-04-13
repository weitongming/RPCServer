package com.weitongming.myweb.mapper;

import com.weitongming.myweb.model.PageBean;
import com.weitongming.myweb.model.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PostMapper {


    List<Post> listPostByUid(int uid);

    int insertPost(Post post);

    List<Post> listPostByTime(@Param("start") int start, @Param("limit") int limit);

    List<Post> listPostByTimeAndTid(@Param("tid") int tid ,@Param("start") int start, @Param("limit") int limit);
    int selectPostCount();

    Post getPostByPid(int pid);

    void updateReplyCount(int pid);

    void updateScanCount(int pid);

    void updateReplyTime(int pid);

    int getUidByPid(int pid);

    String getTitleByPid(int pid);

    List<Post> selectPostByKeyworld(@Param(value = "keyworld") String keyworld);

}
