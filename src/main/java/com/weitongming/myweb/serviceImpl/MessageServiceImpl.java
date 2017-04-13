package com.weitongming.myweb.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.weitongming.myweb.mapper.MessageMapper;
import com.weitongming.myweb.model.Message;
import com.weitongming.myweb.model.Post;
import com.weitongming.myweb.service.MessageService;
import com.weitongming.myweb.util.Config;
import com.weitongming.myweb.util.MyUtil;
import com.weitongming.rpc.server.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RpcService(MessageService.class)
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private JedisPool jedisPool;
    //获得消息列表
    public Map<String, List<Message>> listMessageByUid(Integer sessionUid) {
        //从jedis连接池之中获取链接
        Jedis jedis = jedisPool.getResource();
        //从redis取出消息
        String messageString = jedis.get(Config.REDIS_MESSAGE_UID+sessionUid);
        List<Message> messageList  = JSONObject.parseArray(messageString,Message.class);
        if(messageList == null){
            messageList = messageMapper.listMessageByUid(sessionUid);
            jedis.set(Config.REDIS_MESSAGE_UID + sessionUid,JSONObject.toJSONString(messageList));
            jedis.expire( Config.REDIS_MESSAGE_UID,Config.REDIS_EXPIRE_TIME_1S) ;
        }
        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }

        Map<String, List<Message>> map = new HashMap<>();
        for(Message message : messageList){
            String time = MyUtil.formatDate(message.getMsgTime()).substring(0,11);
            if(map.get(time)==null){
                map.put(time,new LinkedList<Message>());
                map.get(time).add(message);
            }else{
                map.get(time).add(message);
            }
        }
        return map;
    }
}
