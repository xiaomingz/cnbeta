package com.ming.cnbeta.mvp.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ming.cnbeta.R;
import com.ming.cnbeta.adapter.NewsItemRecyclerViewAdapter;
import com.ming.cnbeta.base.BaseFragment;
import com.ming.cnbeta.base.Config;
import com.ming.cnbeta.db.greendao.NewsItem;
import com.ming.cnbeta.mvp.presenters.NewsListPresenter;
import com.ming.cnbeta.mvp.views.activity.NewsDetailActivity;
import com.ming.cnbeta.utils.LogUtils;
import com.ming.cnbeta.widget.SpaceItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


/**
 * Created by ming on 16/2/18.
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NewsItemRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = "NewsListFragment";
    private static final String NEWS_TYPE = "news_type";

    @Bind(R.id.recyclerView_list)
    RecyclerView mRecyclerViewList;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    NewsItemRecyclerViewAdapter mNewsItemRecyclerViewAdapter;
    @Bind(R.id.error_message)
    TextView mErrorMessage;
    private LinearLayoutManager mLayoutManager;
    private SlideInBottomAnimationAdapter mSlideInBottomAnimationAdapter;

    private NewsListPresenter mNewsListPresenter;
    private int current = 0;
    /* 点击了新闻 */
    private boolean selectNews = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsListPresenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    @Override
    protected void initializePresenter() {

        mNewsListPresenter = new NewsListPresenter();
        mNewsListPresenter.attachView(this);

        Bundle bundleData = getActivity().getIntent().getExtras();
        if (bundleData == null || !bundleData.containsKey(NEWS_TYPE)) {
            mNewsListPresenter.setType(Config.ALLNEWS);
        } else {
            String type = (String) getActivity().getIntent().getSerializableExtra(NEWS_TYPE);
            mNewsListPresenter.setType(type);
        }
    }

    private void initView() {
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent,
                R.color.colorAccent, R.color.colorPrimaryDark,
                R.color.colorAccent);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerViewList.setLayoutManager(mLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.list_item_space);
        mRecyclerViewList.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        mErrorMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsListPresenter.getNewsByPage(current);
            }
        });
    }

    private void initData() {

        mNewsItemRecyclerViewAdapter = new NewsItemRecyclerViewAdapter();

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mNewsItemRecyclerViewAdapter);
        mSlideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(alphaAdapter);
        mSlideInBottomAnimationAdapter.setFirstOnly(true);
        mRecyclerViewList.setAdapter(mSlideInBottomAnimationAdapter);
        mRecyclerViewList.setOnScrollListener(mOnScrollListener);
        mNewsItemRecyclerViewAdapter.setOnItemClickListener(this);


        mNewsListPresenter.getNewsItemData();

    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            mSlideInBottomAnimationAdapter.setStartPosition(lastVisibleItem);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mNewsItemRecyclerViewAdapter.getItemCount()
                    && mNewsItemRecyclerViewAdapter.isShowFooter()) {
            }
        }
    };

    public void showProgress() {
        mErrorMessage.setVisibility(View.GONE);
        if (mNewsItemRecyclerViewAdapter != null) {
            mNewsItemRecyclerViewAdapter.setProgressBarVisibility(View.VISIBLE);
        }
        if (mSwipeRefreshWidget != null)
            mSwipeRefreshWidget.setRefreshing(true);

        if (mNewsItemRecyclerViewAdapter.getItemCount() < 2){
            mRecyclerViewList.setVisibility(View.GONE);
        }else {
            mRecyclerViewList.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (mNewsItemRecyclerViewAdapter != null) {
            mNewsItemRecyclerViewAdapter.setProgressBarVisibility(View.GONE);
        }
        if (mSwipeRefreshWidget != null)
            mSwipeRefreshWidget.setRefreshing(false);
    }

    public void onLoadSuccess(){
        hideProgress();
        mRecyclerViewList.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
    }

    public void onLoadFail() {

        hideProgress();

        if (mNewsItemRecyclerViewAdapter.getItemCount() < 2){
            mRecyclerViewList.setVisibility(View.GONE);
            mErrorMessage.setVisibility(View.VISIBLE);
        }else {
            mRecyclerViewList.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.GONE);
        }

    }

    public void bindNewsList(List<NewsItem> newsModels) {
        if (mNewsItemRecyclerViewAdapter != null) {
            mNewsItemRecyclerViewAdapter.setDate(newsModels);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        LogUtils.i(TAG, "onRefresh");
        mRecyclerViewList.scrollToPosition(0);
        mNewsListPresenter.getNewsByPage(1);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (!selectNews){
            String articleID = mNewsItemRecyclerViewAdapter.getData().get(position).getSid();
//        String articleID = "478135";
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra(NewsDetailFragment.NEWS_ARTICLEID, articleID);
            getActivity().startActivity(intent);
        }
        selectNews = true;

    }

    @Override
    public void onFootClick() {
        //加载更多
        LogUtils.d(TAG, "loading more data");
        int page = Integer.valueOf(mNewsListPresenter.getListNews().getPage());
        page++;

        mNewsListPresenter.getNewsByPage(page);
    }

    @Override
    public void onResume() {
        super.onResume();

        selectNews = false;
    }
}
