package com.ming.cnbeta.mvp.presenters;

import com.ming.cnbeta.base.Config;
import com.ming.cnbeta.bean.CommentListObject;
import com.ming.cnbeta.bean.ResponseObject;
import com.ming.cnbeta.db.DaoHelper.CommentItemDaoHelper;
import com.ming.cnbeta.db.DaoHelper.NewsDetailDaoHelper;
import com.ming.cnbeta.db.greendao.CommentItem;
import com.ming.cnbeta.db.greendao.NewsDetail;
import com.ming.cnbeta.mvp.views.fragment.NewsDetailFragment;
import com.ming.cnbeta.net.NewsClient;
import com.ming.cnbeta.utils.LogUtils;
import com.ming.cnbeta.utils.cnBetaUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ming on 16/2/22.
 */
public class NewsDetailPresenter implements Presenter{

    private NewsDetail mNewsDetail = new NewsDetail();
    private NewsDetailFragment mNewsDetailFragment;
    private Subscription mSubscription;
    private boolean isLoadDataSuccess = false;
    private NewsDetailDaoHelper mNewsDetailHelper = new NewsDetailDaoHelper();
    private CommentItemDaoHelper mCommentItemHelper = new CommentItemDaoHelper();

    private String webTemplate = "<!DOCTYPE html><html><head><title></title>" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"+
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>" +
            "<link  rel=\"stylesheet\" href=\"file:///android_asset/style.css\" type=\"text/css\"/>" +
            "<script>var config = {\"enableImage\":true,\"enableFlashToHtml5\":true};</script>" +
            "<script src=\"file:///android_asset/BaseTool.js\"></script>" +
            "<script src=\"file:///android_asset/ImageTool.js\"></script>" +
            "<script src=\"file:///android_asset/VideoTool.js\"></script></head>" +
            "<body><div><div class=\"title\">%s</div><div class=\"from\">%s<span style=\"float: right\">%s</span></div><div id=\"introduce\">%s<div class=\"clear\"></div></div>" +
            "<body><div id=\"content\">%s</div><div class=\"clear foot\">-- The End --</div></div>" +
            "<script src=\"file:///android_asset/loder.js\"></script></body></html>";

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {
        if (mSubscription != null){
            mSubscription.unsubscribe();

            if (!isLoadDataSuccess){
                mNewsDetailFragment.onLoadFail();
            }
        }


    }

    @Override
    public void attachView(Object v) {
        mNewsDetailFragment = (NewsDetailFragment)v;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    public void getNewsDataDetail(final String articleID){

        NewsDetail newsDetail = mNewsDetailHelper.getDataById(articleID);
        if (newsDetail != null){
            loadWebView(newsDetail);
            mNewsDetail = newsDetail;
            mNewsDetailFragment.onLoadSuccess();
            isLoadDataSuccess = true;
            return;
        }

        LogUtils.i("getNewsDataDetail", "articleID:" + articleID);

        mNewsDetailFragment.onLoadStart();

        mSubscription = NewsClient.getCnBetaApi().getNewsBySid(articleID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String resp) {
                        LogUtils.i("dsd", resp);

                        mNewsDetailFragment.onLoadSuccess();
                        handleResponseString(mNewsDetail, resp,articleID);
                        isLoadDataSuccess = true;

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mNewsDetailFragment.onLoadFail();
                        mNewsDetailFragment.showToast("获取数据失败～");
                        LogUtils.i("dsd", "请求错误:" + throwable.toString());

                    }
                });

    }

    public void getCommentBySnAndSid(final String sid){

        String content = "1," + sid + "," + mNewsDetail.getSN();

        LogUtils.i("getCommentBySnAndSid", "op:" + content);
        mSubscription = NewsClient.getCnBetaApi().getCommentBySnAndSid(content)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseObject<CommentListObject>>() {
                    @Override
                    public void call(ResponseObject<CommentListObject> responseObject) {
                        if (responseObject.isOK()){
                            handleCommentData(responseObject.getResult());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        List<CommentItem> cmntlist = mCommentItemHelper.getCommentsBySid(sid);
                        mNewsDetailFragment.loadCommentList(cmntlist);
                        LogUtils.i("dsd", "请求错误:" + throwable.toString());

                    }
                });
    }

    public NewsDetail getNewsDetail(){
        return mNewsDetail;
    }

    private void handleResponseString(NewsDetail item,String resp,String articleID){
        Document doc = Jsoup.parse(resp);
        Elements newsHeadlines = doc.select(".body");
        item.setTitle(newsHeadlines.select("#news_title").html().replaceAll("<.*?>", ""));
        item.setSource(newsHeadlines.select(".where").html());
        item.setDate(newsHeadlines.select(".date").html());
        Elements introduce = newsHeadlines.select(".introduction");
        introduce.select("div").remove();
        item.setIntro(introduce.html());
        Elements content = newsHeadlines.select(".content");
        content.select(".tigerstock").remove();
        Elements scripts = content.select("script");
        for (int i=0;i<scripts.size();i++){
            Element script = scripts.get(i);
            Element SiblingScript = script.nextElementSibling();
            String _script;
            if(SiblingScript!=null&&SiblingScript.tag()== Tag.valueOf("script")){
                i++;
                _script = script.toString().replaceAll(",?\"?(width|height)\"?:?\"(.*)?\"","");
                _script += SiblingScript.toString();
                _script = _script.replaceAll("\"|'","'");
                SiblingScript.remove();
            }else{
                _script = script.toString().replaceAll(",?\"(width|height)\":\"\\d+\"","").replaceAll("\"|'","'");
            }
            Element element = new Element(Tag.valueOf("iframe"),"");
            element.attr("contentScript",_script);
            element.attr("ignoreHolder","true");
            element.attr("style","width:100%");
            element.attr("allowfullscreen ","true");
            element.attr("onload","VideoTool.onloadIframeVideo(this)");
            script.replaceWith(element);
        }
        Matcher snMatcher = Config.SN_PATTERN.matcher(resp);
        if (snMatcher.find())
            item.setSN(snMatcher.group(1));

        item.setContent(content.html());
        item.setSid(articleID);

        mNewsDetailHelper.addData(item);
        loadWebView(item);
    }

    private void loadWebView(NewsDetail newsDetail){
        String data = String.format(Locale.CHINA,
                webTemplate,
                newsDetail.getTitle(),
                newsDetail.getSource(),
                newsDetail.getDate(),
                newsDetail.getIntro(),
                newsDetail.getContent());
        mNewsDetailFragment.loadNewsDetail(data);
    }

    private void handleCommentData(CommentListObject commentListObject){
        ArrayList<CommentItem> cmntlist = commentListObject.getCmntlist();
        HashMap<String, CommentItem> cmntstore = commentListObject.getCmntstore();
        for (CommentItem item : cmntlist) {
            StringBuilder sb = new StringBuilder();
            cnBetaUtils.copy(cmntstore.get(item.getTid()),item);
            CommentItem parent = cmntstore.get(item.getPid());
            while (parent != null) {
                sb.append("//@");
                sb.append(parent.getName());
                sb.append(": [");
                sb.append(parent.getHost_name());
//                sb.append("] <br/>");
                sb.append("] ");
                sb.append(parent.getComment());
                parent = cmntstore.get(parent.getPid());
//                if (parent != null) {
//                    sb.append("<br/>");
//                }
            }
            item.setRefContent(sb.toString());
        }

        Collections.sort(cmntlist, new Comparator<CommentItem>() {
            @Override
            public int compare(CommentItem arg0, CommentItem arg1) {
                if (arg0 != null && arg1 != null) {
                    return (Integer.valueOf(arg1.getScore()) - Integer.valueOf(arg0.getScore()));
                } else {
                    return 0;
                }
            }
        });

        mCommentItemHelper.addListData(cmntlist);

//        ArrayList<CommentItem> hotcmntlist = commentListObject.getHotlist();
//        for (CommentItem item : hotcmntlist) {
//            StringBuilder sb = new StringBuilder();
//            item.copy(cmntstore.get(item.getTid()));
//            CommentItem parent = cmntstore.get(item.getPid());
//            while (parent != null) {
//                sb.append("//@");
//                sb.append(parent.getName());
//                sb.append(": [");
//                sb.append(parent.getHost_name());
////                sb.append("] <br/>");
//                sb.append("] ");
//                sb.append(parent.getComment());
//                parent = cmntstore.get(parent.getPid());
////                if (parent != null) {
////                    sb.append("<br/>");
////                }
//            }
//            item.setRefContent(sb.toString());
//        }


        mNewsDetailFragment.loadCommentList(cmntlist);
    }
}
