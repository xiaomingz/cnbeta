package com.ming.entity;

import com.ming.interf.ID;

/**
 * Created by ming on 16/2/22.
 */
public class CommentItem {

    private Integer score;
    @ID
    private String tid;
    private String pid;
    private Integer sid;
    private Integer reason;
    private String icon;
    private String date;
    private String name;
    private String comment;
    private String host_name;
    private String refContent;
}
