package com.ming.cnbeta.bean;

import com.ming.cnbeta.db.greendao.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ming on 16/2/19.
 */
public class ListNews {

    private List<NewsItem> list = new ArrayList<>();

    private String pager;

    private int auto;

    private String type;

    private String page = "0";

    public void setList(List<NewsItem> list){
        this.list = list;
    }
    public void addList(List<NewsItem> list){
        this.list.addAll(list);
    }
    public List<NewsItem> getList(){
        return this.list;
    }
    public void setPager(String pager){
        this.pager = pager;
    }
    public String getPager(){
        return this.pager;
    }
    public void setAuto(int auto){
        this.auto = auto;
    }
    public int getAuto(){
        return this.auto;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setPage(String page){
        this.page = page;
    }
    public String getPage(){
        return this.page;
    }
}
