package com.ming.cnbeta.db.DaoHelper;

import com.ming.cnbeta.base.BaseApplication;
import com.ming.cnbeta.base.BaseDaoHelper;
import com.ming.cnbeta.db.greendao.NewsItem;
import com.ming.cnbeta.db.greendao.NewsItemDao;

import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by ming on 16/2/29.
 */
public class NewsItemDaoHelper extends BaseDaoHelper<NewsItem,String>{
    @Override
    public AbstractDao getDao() {
        return BaseApplication.getDaoSession().getNewsItemDao();
    }

    public List<NewsItem> getLatelyNews(){

        try {
            List qb = dao.queryBuilder()
                    .orderDesc(NewsItemDao.Properties.Inputtime)
                    .limit(40)
                    .list();
            return qb;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
