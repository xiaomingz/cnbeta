package com.ming.cnbeta.net;


import com.ming.cnbeta.bean.CommentListObject;
import com.ming.cnbeta.bean.ListNews;
import com.ming.cnbeta.bean.ResponseObject;
import com.ming.cnbeta.db.greendao.NewsItem;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ming on 16/1/30.
 */
public interface CnBetaApi {

    /**
     * 获取首页新闻
     * @return
     */
    @GET("/api/getArticles")
    Observable<List<NewsItem>> getArticles();

    /**
     * 获取更多新闻
     * @param fromArticleID 是上次获取的最后一条新闻的ID
     * @return
     */
    @GET("/api/getMoreArticles/{fromArticleID}")
    Observable<List<NewsItem>> getMoreArticles(@Path("fromArticleID")int fromArticleID);

    /**
     * 获取新闻详情
     * @param articleID 新闻的ID
     * @return
     */
    @GET("/api/getMoreArticles/{articleID}")
    Observable<NewsItem> getArticleDetail(@Query("articleID")int articleID);

    @GET("/more")
    Observable<ResponseObject<ListNews>> getNewslistByPage(@Query("type") String type,
                                                  @Query("page") int page,
                                                  @Query("_") long time);
    @GET("/articles/{sid}.htm")
    Observable<String> getNewsBySid(@Path("sid") String sid);

    @GET("/cmt")
    Observable<ResponseObject<CommentListObject>> getCommentBySnAndSid(@Query("op") String op);
}
