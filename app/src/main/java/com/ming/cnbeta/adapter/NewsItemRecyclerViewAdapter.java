package com.ming.cnbeta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.ming.cnbeta.R;
import com.ming.cnbeta.base.Config;
import com.ming.cnbeta.db.greendao.NewsItem;
import com.ming.cnbeta.utils.TimeUtils;

import java.util.Date;
import java.util.List;



/**
 */
public class NewsItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<NewsItem> mData;
    private boolean mShowFooter = true;
    private Context mContext;
    private int mPositon;
    private OnItemClickListener mOnItemClickListener;
    private ProgressBar mProgressBar;


    public void setDate(List<NewsItem> data) {
        this.mData = data;
//        notifyItemRangeChanged(0,data.size());
        this.notifyDataSetChanged();
    }

    public  List<NewsItem> getData(){
        return mData;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        mContext = parent.getContext();
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item, parent, false);
//            v.setOnClickListener(this);
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ItemViewHolder(
                    MaterialRippleLayout.on(inflater.inflate(R.layout.fragment_item, parent, false))
                            .rippleOverlay(true)
                            .rippleAlpha(0.2f)
                            .rippleColor(0xFF585858)
//                            .rippleColor(R.color.colorRipple)
                            .rippleHover(true)
                            .create()
            );
//            ItemViewHolder vh = new ItemViewHolder(v);
//            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            view.setOnClickListener(this);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        LogUtils.i("onBindViewHolder", "位置+++++++" + position);
        if(holder instanceof ItemViewHolder) {
            mPositon = position;
            NewsItem news = mData.get(position);
            if(news == null) {
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(news.getTitle());
            ((ItemViewHolder) holder).mNewsComments.setText(news.getComments());
            ((ItemViewHolder) holder).mNewsViews.setText(news.getMview());
//            ((ItemViewHolder) holder).mDesc.setText(news.getHometext());
            String source = news.getSource();
            String[] list = source.split("@");
            if (list.length > 0){
                source = list[0];
            }
            ((ItemViewHolder) holder).mFrom.setText(source);
            String inputTime = news.getInputtime();
            Date date = TimeUtils.stringToDate(inputTime, Config.TIMEFORMAT_DATE);
            String time = TimeUtils.getBeforeTime(date.getTime());
            ((ItemViewHolder) holder).mTime.setText(time);

            Glide.with(mContext).load(news.getThumb()).error(R.drawable.ic_image_loadfail).placeholder(R.drawable.ic_image_loading)
                    .centerCrop()
                    .crossFade()
                    .into(((ItemViewHolder) holder).mNewsImg);
        }

        if (holder instanceof FooterViewHolder){
            mProgressBar = ((FooterViewHolder) holder).progressBar;
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if(mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    public NewsItem getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.layout.fragment_item){
//            mOnItemClickListener.onItemClick(v,mPositon);
//        }else if (id == R.layout.footer){
//            mOnItemClickListener.onFootClick();
//        }
//
//    }


    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProgressBar progressBar;

        public FooterViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.foot_progress);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
//                progressBar.setVisibility(View.VISIBLE);
                mOnItemClickListener.onFootClick();
            }
        }
    }

    public void setProgressBarVisibility(int v){
        if (mProgressBar != null){
            mProgressBar.setVisibility(v);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFootClick();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public ImageView mNewsImg;
        public TextView mFrom;
        public TextView mTime;
        public TextView mNewsComments;
        public TextView mNewsViews;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mFrom = (TextView) v.findViewById(R.id.from);
            mTime = (TextView) v.findViewById(R.id.time);
            mNewsComments = (TextView) v.findViewById(R.id.news_comments);
            mNewsViews = (TextView) v.findViewById(R.id.news_views);
            mNewsImg = (ImageView) v.findViewById(R.id.ivNews);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }


}
