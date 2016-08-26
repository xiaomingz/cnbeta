package com.ming.cnbeta.mvp.views.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.ming.cnbeta.R;
import com.ming.cnbeta.adapter.NewsDetailRecyclerViewAdapter;
import com.ming.cnbeta.base.BaseFragment;
import com.ming.cnbeta.db.greendao.CommentItem;
import com.ming.cnbeta.interf.NewsDetailCallBack;
import com.ming.cnbeta.mvp.presenters.NewsDetailPresenter;
import com.ming.cnbeta.net.Constant;
import com.ming.cnbeta.utils.LogUtils;
import com.ming.cnbeta.widget.avloading.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by ming on 16/2/22.
 */
public class NewsDetailFragment extends BaseFragment implements NewsDetailCallBack {

    public static final String NEWS_ARTICLEID = "news_articleID";

    @Bind(R.id.message)
    TextView mMessage;
    @Bind(R.id.action)
    FloatingActionButton mAction;
    @Bind(R.id.loading)
    AVLoadingIndicatorView mLoading;
    @Bind(R.id.recyclerView_list)
    RecyclerView mRecyclerViewList;

    private NewsDetailPresenter mNewsDetailPresenter;
    private String mArticleID;
    private NewsDetailRecyclerViewAdapter mNewsDetailRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this, view);

        initData();
        initView();
        return view;
    }

    private void initView() {
        Bundle bundleData = getActivity().getIntent().getExtras();
        if (bundleData != null && bundleData.containsKey(NEWS_ARTICLEID)) {
            mArticleID = (String) getActivity().getIntent().getSerializableExtra(NEWS_ARTICLEID);
            mNewsDetailPresenter.getNewsDataDetail(mArticleID);
        }

        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsDetailPresenter.getNewsDataDetail(mArticleID);
            }
        });
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = String.format(Constant.DETAIL_URL,mArticleID);
                Uri uri = Uri.parse(url);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

    }


    private void initData() {

        mNewsDetailRecyclerViewAdapter = new NewsDetailRecyclerViewAdapter();
        mNewsDetailRecyclerViewAdapter.setNewsDetailCallBack(this);
        mRecyclerViewList.setAdapter(mNewsDetailRecyclerViewAdapter);
    }

    @Override
    protected void initializePresenter() {
        mNewsDetailPresenter = new NewsDetailPresenter();
        mNewsDetailPresenter.attachView(this);

    }

    public void loadNewsDetail(String content) {
        List<CommentItem> commentList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            CommentItem comment = new CommentItem();
            if (i == 0) {
                comment.setRefContent(content);
            }
            commentList.add(comment);
        }

        mNewsDetailRecyclerViewAdapter.setDate(commentList);

    }

    public void loadCommentList(List<CommentItem> items){
        if (items .size() > 0 ){
            mNewsDetailRecyclerViewAdapter.addData(items);
        }

    }

    public void onLoadStart() {
        mLoading.setVisibility(View.VISIBLE);
        mMessage.setVisibility(View.GONE);
//        mWebView.setVisibility(View.GONE);
    }

    public void onLoadFail() {
        mLoading.setVisibility(View.GONE);
        mMessage.setVisibility(View.VISIBLE);
    }

    public void onLoadSuccess() {
        mLoading.setVisibility(View.GONE);
//        mWebView.setVisibility(View.VISIBLE);
    }

    public void hideLoadingView() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
//        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mWebView.onPause();
        mNewsDetailPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mNewsDetailPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        mNewsDetailRecyclerViewAdapter.destroyWebView();
//        mScrollView.removeAllViews();
//        mWebView.stopLoading();
//        mWebView.destroy();
        super.onDestroyView();

        mNewsDetailPresenter.onDestroy();
        ButterKnife.unbind(this);

    }

    @Override
    public void onNewsLoadFinish() {
        LogUtils.i("onNewsLoadFinish","onNewsLoadFinish");
        mNewsDetailPresenter.getCommentBySnAndSid(mArticleID);
    }


}
