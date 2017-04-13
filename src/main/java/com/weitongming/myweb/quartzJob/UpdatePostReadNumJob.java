package com.weitongming.myweb.quartzJob;

import com.weitongming.myweb.mapper.PostMapper;
import com.weitongming.myweb.model.Post;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;


import java.util.Map;

/**
 * Created by Administrator on 2017/4/9.
 */

public class UpdatePostReadNumJob {
    private Map<String,Post>  postMap;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private JedisPool jedisPool;

    public void runJob(){
        Logger.getLogger(UpdatePostReadNumJob.class).error("##################################################################################");
        System.out.println("##################################################################################");
    }
}
