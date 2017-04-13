package com.weitongming.myweb.service;

import com.weitongming.myweb.mapper.TopicMapper;
import com.weitongming.myweb.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



public interface TopicService {




    public List<Topic> listTopic();

    public List<String> listImage();
}

