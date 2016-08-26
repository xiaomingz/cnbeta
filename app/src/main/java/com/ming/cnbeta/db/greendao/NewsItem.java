package com.ming.cnbeta.db.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table tb_NewsItem.
 */
public class NewsItem {

    private String sid;
    private String catid;
    private String topic;
    private String aid;
    private String user_id;
    private String title;
    private String style;
    private String keywords;
    private String hometext;
    private String listorder;
    private String comments;
    private String counter;
    private String mview;
    private String collectnum;
    private String good;
    private String bad;
    private String score;
    private String ratings;
    private String score_story;
    private String ratings_story;
    private String pollid;
    private String queueid;
    private String ifcom;
    private String ishome;
    private String elite;
    private String status;
    private String inputtime;
    private String updatetime;
    private String thumb;
    private String source;
    private String url_show;
    private Integer rate_sum;

    public NewsItem() {
    }

    public NewsItem(String sid) {
        this.sid = sid;
    }

    public NewsItem(String sid, String catid, String topic, String aid, String user_id, String title, String style, String keywords, String hometext, String listorder, String comments, String counter, String mview, String collectnum, String good, String bad, String score, String ratings, String score_story, String ratings_story, String pollid, String queueid, String ifcom, String ishome, String elite, String status, String inputtime, String updatetime, String thumb, String source, String url_show, Integer rate_sum) {
        this.sid = sid;
        this.catid = catid;
        this.topic = topic;
        this.aid = aid;
        this.user_id = user_id;
        this.title = title;
        this.style = style;
        this.keywords = keywords;
        this.hometext = hometext;
        this.listorder = listorder;
        this.comments = comments;
        this.counter = counter;
        this.mview = mview;
        this.collectnum = collectnum;
        this.good = good;
        this.bad = bad;
        this.score = score;
        this.ratings = ratings;
        this.score_story = score_story;
        this.ratings_story = ratings_story;
        this.pollid = pollid;
        this.queueid = queueid;
        this.ifcom = ifcom;
        this.ishome = ishome;
        this.elite = elite;
        this.status = status;
        this.inputtime = inputtime;
        this.updatetime = updatetime;
        this.thumb = thumb;
        this.source = source;
        this.url_show = url_show;
        this.rate_sum = rate_sum;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getHometext() {
        return hometext;
    }

    public void setHometext(String hometext) {
        this.hometext = hometext;
    }

    public String getListorder() {
        return listorder;
    }

    public void setListorder(String listorder) {
        this.listorder = listorder;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getMview() {
        return mview;
    }

    public void setMview(String mview) {
        this.mview = mview;
    }

    public String getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(String collectnum) {
        this.collectnum = collectnum;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getScore_story() {
        return score_story;
    }

    public void setScore_story(String score_story) {
        this.score_story = score_story;
    }

    public String getRatings_story() {
        return ratings_story;
    }

    public void setRatings_story(String ratings_story) {
        this.ratings_story = ratings_story;
    }

    public String getPollid() {
        return pollid;
    }

    public void setPollid(String pollid) {
        this.pollid = pollid;
    }

    public String getQueueid() {
        return queueid;
    }

    public void setQueueid(String queueid) {
        this.queueid = queueid;
    }

    public String getIfcom() {
        return ifcom;
    }

    public void setIfcom(String ifcom) {
        this.ifcom = ifcom;
    }

    public String getIshome() {
        return ishome;
    }

    public void setIshome(String ishome) {
        this.ishome = ishome;
    }

    public String getElite() {
        return elite;
    }

    public void setElite(String elite) {
        this.elite = elite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl_show() {
        return url_show;
    }

    public void setUrl_show(String url_show) {
        this.url_show = url_show;
    }

    public Integer getRate_sum() {
        return rate_sum;
    }

    public void setRate_sum(Integer rate_sum) {
        this.rate_sum = rate_sum;
    }

}
