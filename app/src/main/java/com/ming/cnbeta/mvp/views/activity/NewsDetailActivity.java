package com.ming.cnbeta.mvp.views.activity;

import android.os.Bundle;

import com.ming.cnbeta.R;
import com.ming.cnbeta.base.BaseActivity;
import com.ming.cnbeta.mvp.views.fragment.NewsDetailFragment;
import com.ming.cnbeta.utils.LogUtils;

/**
 * Created by ming on 16/2/19.
 */
public class NewsDetailActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
    }

    private void initView(){
        final NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_content,newsDetailFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("NewsDetailActivity","onDestroy");
    }

}
