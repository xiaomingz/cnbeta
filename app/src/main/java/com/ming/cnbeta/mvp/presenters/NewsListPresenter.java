package com.ming.cnbeta.mvp.presenters;

import android.util.Log;

import com.ming.cnbeta.bean.ListNews;
import com.ming.cnbeta.bean.ResponseObject;
import com.ming.cnbeta.db.DaoHelper.NewsItemDaoHelper;
import com.ming.cnbeta.db.greendao.NewsItem;
import com.ming.cnbeta.mvp.views.fragment.NewsListFragment;
import com.ming.cnbeta.net.NewsClient;
import com.ming.cnbeta.utils.LogUtils;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ming on 16/2/22.
 */
public class NewsListPresenter implements Presenter{
    private static final String TAG = "NewsListPresenter";

    private ListNews mListNews = new ListNews();
    private NewsListFragment mNewsListFragment;
    private String mType;
    private NewsItemDaoHelper mNewsItemDaoHelper = new NewsItemDaoHelper();

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(Object v) {
        mNewsListFragment = (NewsListFragment)v;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    public void setType(String type){
        mType = type;
    }

    public void getNewsByPage(final int page){
        LogUtils.i(TAG,"getNewsByPage:" + page);
        mNewsListFragment.showProgress();

        NewsClient.getCnBetaApi().getNewslistByPage(mType, page, System.currentTimeMillis())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseObject<ListNews>>() {
                    @Override
                    public void call(ResponseObject<ListNews> newsModel) {


                        if (newsModel.isOK()){
                            List<NewsItem> newList = newsModel.getResult().getList();
                            mNewsItemDaoHelper.addListData(newList);
                            mListNews.setPage(newsModel.getResult().getPage());
                            if (page == 1){
                                mListNews.setList(newList);
                            }else {
                                mListNews.addList(newList);
                            }

                            mNewsListFragment.bindNewsList(mListNews.getList());
                            mNewsListFragment.onLoadSuccess();
                        }else {
                            mNewsListFragment.onLoadFail();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        mNewsListFragment.onLoadFail();
                        mNewsListFragment.showToast("刷新失败～");
                        Log.i("dsd", "请求错误:" + throwable.toString());

                    }
                });
    }

    public  ListNews getListNews(){
        return mListNews;
    }

    public void getNewsItemData(){
        List<NewsItem> newsItems = mNewsItemDaoHelper.getLatelyNews();
        if (newsItems != null && newsItems.size() > 0){
            mListNews.setList(newsItems);
            mListNews.setPage("1");

            mNewsListFragment.bindNewsList(newsItems);
            mNewsListFragment.onLoadSuccess();
        }else {
            getNewsByPage(1);
        }
    }
}
