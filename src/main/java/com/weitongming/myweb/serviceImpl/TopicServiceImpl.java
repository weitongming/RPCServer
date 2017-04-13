package com.weitongming.myweb.serviceImpl;

import com.weitongming.myweb.mapper.TopicMapper;
import com.weitongming.myweb.model.Topic;
import com.weitongming.myweb.service.TopicService;
import com.weitongming.rpc.server.RpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RpcService(TopicService.class)
public class TopicServiceImpl implements TopicService{


    @Autowired
    private TopicMapper topicMapper;

    public List<Topic> listTopic() {
        return topicMapper.listTopic();
    }

    public List<String> listImage() {
        return topicMapper.listImage();
    }
}

