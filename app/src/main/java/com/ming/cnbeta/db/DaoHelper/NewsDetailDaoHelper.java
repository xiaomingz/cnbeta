package com.ming.cnbeta.db.DaoHelper;

import com.ming.cnbeta.base.BaseApplication;
import com.ming.cnbeta.base.BaseDaoHelper;
import com.ming.cnbeta.db.greendao.NewsDetail;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by ming on 16/2/29.
 */
public class NewsDetailDaoHelper extends BaseDaoHelper<NewsDetail,String>{
    @Override
    public AbstractDao getDao() {
        return BaseApplication.getDaoSession().getNewsDetailDao();
    }
}
