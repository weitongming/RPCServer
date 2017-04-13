package com.weitongming.myweb.serviceImpl;

import com.weitongming.myweb.async.MailTask;
import com.weitongming.myweb.mapper.UserMapper;
import com.weitongming.myweb.model.User;
import com.weitongming.myweb.service.LoginService;
import com.weitongming.myweb.util.MyConstant;
import com.weitongming.myweb.util.MyUtil;
import com.weitongming.rpc.server.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RpcService(LoginService.class)
public class LoginServiceImpl implements LoginService{


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TaskExecutor taskExecutor;

    //注册
    public String register(User user,String repassword) {

        //校验邮箱格式
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
        Matcher m = p.matcher(user.getEmail());
        if(!m.matches()){
            return "邮箱格式有问题啊~";
        }

        //校验密码长度
        p = Pattern.compile("^\\w{6,20}$");
        m = p.matcher(user.getPassword());
        if(!m.matches()){
            return "密码长度要在6到20之间~";
        }

        //检查密码
        if(!user.getPassword().equals(repassword)){
            return "两次密码输入不一致~";
        }

        //检查邮箱是否被注册
        int emailCount = userMapper.selectEmailCount(user.getEmail());
        if(emailCount>0){
            return "该邮箱已被注册~";
        }

        //构造user，设置未激活
        user.setActived(0);
        String activateCode = MyUtil.createActivateCode();
        user.setActivateCode(activateCode);
        user.setJoinTime(MyUtil.formatDate(new Date()));
        user.setUsername("用户"+new Random().nextInt(10000)+"号");
        user.setHeadUrl("image/head.jpg");

        //发送邮件
        taskExecutor.execute(new MailTask(activateCode,user.getEmail(),javaMailSender,1));

        //向数据库插入记录
        userMapper.insertUser(user);

        return "ok";
    }



    //登录
    public Map<String,Object> login(User user) {

        Map<String,Object> map = new HashMap<>();
        User realUser = userMapper.selectUserByEmailAndPassword(user);
        if(realUser==null){
            map.put("status","no");
            map.put("error","用户名或密码错误~");
            return map;
        }

        int checkActived = userMapper.selectActived(user);
        if(realUser.getActivateCode().equals("0")){
            map.put("status","no");
            map.put("error","您还没有激活账户哦，请前往邮箱激活~");
            return map;
        }

        map.put("status","yes");
        map.put("uid",realUser.getUid());
        map.put("headUrl",realUser.getHeadUrl());
        map.put("user",realUser);
        return map;
    }

    public void activate(String activateCode) {
        userMapper.updateActived(activateCode);
    }
}
