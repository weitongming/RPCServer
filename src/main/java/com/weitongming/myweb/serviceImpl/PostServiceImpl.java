package com.weitongming.myweb.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.weitongming.myweb.async.MessageTask;
import com.weitongming.myweb.mapper.MessageMapper;
import com.weitongming.myweb.mapper.PostMapper;
import com.weitongming.myweb.mapper.ReplyMapper;
import com.weitongming.myweb.mapper.UserMapper;
import com.weitongming.myweb.model.PageBean;
import com.weitongming.myweb.model.Post;
import com.weitongming.myweb.service.PostService;
import com.weitongming.myweb.util.Config;
import com.weitongming.myweb.util.MyConstant;
import com.weitongming.myweb.util.MyUtil;
import com.weitongming.rpc.server.RpcService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;


@RpcService(PostService.class)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private TaskExecutor taskExecutor;

    //根据uid，获得帖子列表
    public List<Post> getPostList(int uid) {
        return postMapper.listPostByUid(uid);
    }

    public int publishPost(Post post) {

        //构造帖子
        post.setPublishTime(MyUtil.formatDate(new Date()));
        post.setReplyTime(MyUtil.formatDate(new Date()));
        post.setReplyCount(0);
        post.setLikeCount(0);
        post.setScanCount(0);
        //插入一条帖子记录
        postMapper.insertPost(post);
        System.out.println(post.getPid());
        //更新用户发帖量
        userMapper.updatePostCount(post.getUser().getUid());

        return post.getPid();
    }

    //按时间列出帖子
    public PageBean<Post> listPostByTime(int curPage) {
        //从jedis连接池之中获取链接
        Jedis jedis = jedisPool.getResource();
        //每页记录数，从哪开始
        int limit = 8;
        int offset = (curPage-1) * limit;
        //获得总记录数，总页数
        String allCountStr = jedis.get(Config.REDIS_ALL_POST_NUM);
        int allCount;
        if ( allCountStr != null){
            allCount  = Integer.valueOf(allCountStr);
        }
        else{
            allCount = postMapper.selectPostCount();
            jedis.set(Config.REDIS_ALL_POST_NUM,allCount+"");
            jedis.pexpire(Config.REDIS_ALL_POST_NUM,200);
        }
        int allPage = 0;
        if (allCount <= limit) {
            allPage = 1;
        } else if (allCount / limit == 0) {
            allPage = allCount / limit;
        } else {
            allPage = allCount / limit + 1;
        }
        //从redis取出消息
        String postListString = jedis.get(Config.REDIS_POST_BY_TIME_PAGE + curPage);
        List<Post> postList  = JSONObject.parseArray(postListString,Post.class);
        //如果redis之中不存在
        if (postList == null){
            //分页查询数据库得到数据列表
            postList = postMapper.listPostByTime(offset,limit);
            jedis.set(Config.REDIS_POST_BY_TIME_PAGE + curPage,JSONObject.toJSONString(postList));
            jedis.pexpire(Config.REDIS_POST_BY_TIME_PAGE + curPage,Config.REDIS_EXPIRE_TIME_MILLSECOND);
        }

        for(Post post : postList){
            post.setLikeCount((int)(long)jedis.scard(post.getPid()+":like"));
        }

        //构造PageBean
        PageBean<Post> pageBean = new PageBean<>(allPage,curPage);
        pageBean.setList(postList);

        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return pageBean;
    }

    @Override
    public PageBean<Post> listPostByTimeAndType( int curPage, int tid) {
        //从jedis连接池之中获取链接
        Jedis jedis = jedisPool.getResource();
        //每页记录数，从哪开始
        int start = (curPage-1) * Config.pageSize;
        //获得总记录数，总页数
        String allCountStr = jedis.get(Config.REDIS_ALL_POST_NUM_TOPIC_ + tid);
        int allCount;
        if ( allCountStr != null){
            allCount  = Integer.valueOf(allCountStr);
        }
        else{
            allCount = postMapper.selectPostCount();
            jedis.set(Config.REDIS_ALL_POST_NUM_TOPIC_ + tid,allCount+"");
            jedis.pexpire(Config.REDIS_ALL_POST_NUM_TOPIC_ + tid,200);
        }
        int allPage = 0;
        if (allCount <= Config.pageSize) {
            allPage = 1;
        } else if (allCount / Config.pageSize == 0) {
            allPage = allCount / Config.pageSize;
        } else {
            allPage = allCount / Config.pageSize + 1;
        }
        //从redis取出消息
        String postListString = jedis.get(Config.REDIS_ALL_POST_NUM_TOPIC_ + tid + curPage);
        List<Post> postList  = JSONObject.parseArray(postListString,Post.class);
        //如果redis之中不存在
        if (postList == null){
            //分页查询数据库得到数据列表
            postList = postMapper.listPostByTimeAndTid(tid,start,start + Config.pageSize);
            jedis.set(Config.REDIS_ALL_POST_NUM_TOPIC_ + tid + curPage,JSONObject.toJSONString(postList));
            jedis.pexpire(Config.REDIS_ALL_POST_NUM_TOPIC_ + tid + curPage,Config.REDIS_EXPIRE_TIME_MILLSECOND);
        }

        for(Post post : postList){
            post.setLikeCount((int)(long)jedis.scard(post.getPid()+":like"));
        }

        //构造PageBean
        PageBean<Post> pageBean = new PageBean<>(allPage,curPage);
        pageBean.setList(postList);

        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return pageBean;
    }

    public Post getPostByPid(int pid) {
        Jedis jedis = jedisPool.getResource();
        String postString = jedis.get("pid"+pid);
        System.out.println(postString);
        Post post = JSONObject.parseObject(postString,Post.class);
        if(post == null){
            post = postMapper.getPostByPid(pid);
            jedis.set("pid"+pid,JSONObject.toJSONString(post));
            jedis.expire( "pid"+pid,20) ;
        }
        //设置点赞数
        long likeCount = jedis.scard(pid+":like");
        post.setLikeCount((int)likeCount);

        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return post;
    }

    //点赞
    public String clickLike(int pid, int sessionUid) {
        Jedis jedis = jedisPool.getResource();
        //pid被sessionUid点赞
        jedis.sadd(pid+":like", String.valueOf(sessionUid));
        //增加用户获赞数
        jedis.hincrBy("vote",sessionUid+"",1);

        //插入一条点赞消息
        taskExecutor.execute(new MessageTask(messageMapper,userMapper,postMapper,replyMapper,pid,0,sessionUid, MyConstant.OPERATION_CLICK_LIKE));
        String result = String.valueOf(jedis.scard(pid+":like"));

        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    //某用户是否赞过某帖子
    public boolean getLikeStatus(int pid, int sessionUid) {
        Jedis jedis = jedisPool.getResource();
        boolean result = jedis.sismember(pid+":like", String.valueOf(sessionUid));

        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    @Override
    public PageBean<Post> search(String keyworld,int curPage) {
        int start = (curPage - 1) * Config.pageSize;
        PageBean<Post> pagePost = new PageBean<>();
        List<Post> postList = postMapper.selectPostByKeyworld(keyworld);
        //
        postList.forEach(post -> {
            if (post.getContent().length() > 20)
            {
                post.setContent(post.getContent().substring(0,20));
            }
        });
        pagePost.setAllPage(postList.size());
        int limit = (start + Config.pageSize) > postList.size() ? postList.size() :(start + Config.pageSize) ;

        pagePost.setList(postList.subList(start,limit));
        pagePost.setCurPage(curPage);
        return pagePost;
    }
}

