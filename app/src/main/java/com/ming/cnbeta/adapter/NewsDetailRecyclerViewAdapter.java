package com.ming.cnbeta.adapter;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ming.cnbeta.R;
import com.ming.cnbeta.base.BaseApplication;
import com.ming.cnbeta.db.greendao.CommentItem;
import com.ming.cnbeta.interf.NewsDetailCallBack;
import com.ming.cnbeta.net.Constant;
import com.ming.cnbeta.utils.LogUtils;
import com.ming.cnbeta.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 */
public class NewsDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_WEBVIEW = 1;

    private List<CommentItem> mData = new ArrayList<>();

    private WebView mWebView;
    private LinearLayout mWebContent;
    private NewsDetailCallBack mNewsDetailCallBack;

    public void setNewsDetailCallBack(NewsDetailCallBack callBack){
        mNewsDetailCallBack = callBack;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void destroyWebView() {
        if (mWebContent != null) {
            mWebContent.removeAllViews();
        }
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
        }
    }

    public void setDate(List<CommentItem> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    public void addData(List<CommentItem> data){
        this.mData.addAll(data);
        this.notifyItemRangeChanged(1, data.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_WEBVIEW;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if (viewType == TYPE_ITEM){
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ItemViewHolder(
                    MaterialRippleLayout.on(inflater.inflate(R.layout.adapter_comment, parent, false))
                            .rippleOverlay(true)
                            .rippleAlpha(0.2f)
                            .rippleColor(0xFF585858)
                            .rippleHover(true)
                            .create()
            );

        }else {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_webview, parent, false);
            WebViewViewHolder vh = new WebViewViewHolder(v);
            return vh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData == null) {
            return;
        }
        CommentItem comment = mData.get(position);
        if (comment == null) {
            return;
        }

        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;

            String refContent = comment.getRefContent();
            if (refContent != null && !refContent.isEmpty()){
                viewHolder.mCommentRef.setVisibility(View.VISIBLE);
                viewHolder.mCommentRef.setText(comment.getRefContent());
            }else {
                viewHolder.mCommentRef.setVisibility(View.GONE);
            }

            viewHolder.mCommentContent.setText(comment.getComment());
            viewHolder.mCommentTime.setText(comment.getDate());
            viewHolder.mCommentName.setText(comment.getName());
            viewHolder.mCommentFrom.setText(comment.getHost_name());
            viewHolder.mCommentScore.setText(String.valueOf(comment.getScore()));
            viewHolder.mCommentReason.setText(String.valueOf(comment.getReason()));

        }

        if (holder instanceof WebViewViewHolder){
            WebViewViewHolder viewHolder = (WebViewViewHolder) holder;

            if (mWebView == null) {
                mWebView = new WebView(BaseApplication.context());
                initWebView(mWebView);
                mWebView.loadDataWithBaseURL(Constant.BASEURL, comment.getRefContent(), "text/html", "UTF-8", null);//utf-8
                viewHolder.mWebContent.addView(mWebView);
            }

            mWebContent = viewHolder.mWebContent;

        }
    }

    private void initWebView(WebView webView) {
        WebSettings mSettings = webView.getSettings();
        mSettings.setUseWideViewPort(true);
        mSettings.setSupportZoom(true);
        mSettings.setAllowFileAccess(true);
        mSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setLoadsImagesAutomatically(true);
        mSettings.setDefaultTextEncodingName("utf-8");
        mSettings.setTextZoom(110);
        mWebView.setWebChromeClient(new VideoWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (NetWorkUtils.isNetworkReachable(BaseApplication.context())) {
            mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.comment_name)
        TextView mCommentName;
        @Bind(R.id.comment_from)
        TextView mCommentFrom;
        @Bind(R.id.comment_ref)
        TextView mCommentRef;
        @Bind(R.id.comment_content)
        TextView mCommentContent;
        @Bind(R.id.comment_time)
        TextView mCommentTime;
        @Bind(R.id.comment_reason)
        TextView mCommentReason;
        @Bind(R.id.comment_score)
        TextView mCommentScore;
        @Bind(R.id.comment_layout)
        RelativeLayout mCommentLayout;

        public ItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }


    }

    public class WebViewViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.web_content)
        LinearLayout mWebContent;

        public WebViewViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }


    }

    class VideoWebChromeClient extends WebChromeClient {
        private View myView = null;
        CustomViewCallback myCallback = null;


        @Override
        public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
                return;
            }
            view.setBackgroundColor(Color.BLACK);
//            onShowHtmlVideoView(view);
            myView = view;
            myCallback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {

            if (myView != null) {

                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                }

//                onHideHtmlVideoView(myView);
                myView = null;
            }
        }


    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            LogUtils.i("MyWebViewClient", "MyWebViewClient.onPageFinished");
            super.onPageFinished(view, url);
            if (mNewsDetailCallBack != null){
                mNewsDetailCallBack.onNewsLoadFinish();
            }
        }

    }


}
