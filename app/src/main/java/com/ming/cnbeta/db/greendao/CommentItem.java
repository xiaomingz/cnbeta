package com.ming.cnbeta.db.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table tb_CommentItem.
 */
public class CommentItem {

    private Integer score;
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

    public CommentItem() {
    }

    public CommentItem(String tid) {
        this.tid = tid;
    }

    public CommentItem(Integer score, String tid, String pid, Integer sid, Integer reason, String icon, String date, String name, String comment, String host_name, String refContent) {
        this.score = score;
        this.tid = tid;
        this.pid = pid;
        this.sid = sid;
        this.reason = reason;
        this.icon = icon;
        this.date = date;
        this.name = name;
        this.comment = comment;
        this.host_name = host_name;
        this.refContent = refContent;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getRefContent() {
        return refContent;
    }

    public void setRefContent(String refContent) {
        this.refContent = refContent;
    }

}
