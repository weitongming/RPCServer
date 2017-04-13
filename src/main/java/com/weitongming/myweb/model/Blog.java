package com.weitongming.myweb.model;

import lombok.Data;

/**
 * Created by Administrator on 2017/4/4.
 */
@Data
public class Blog {
    private Integer id;
    public String uid;
    public String title;
    public String content;
    public String key;
    public String time;
}
