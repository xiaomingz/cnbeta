package com.ming.cnbeta.db.DaoHelper;

import com.ming.cnbeta.base.BaseApplication;
import com.ming.cnbeta.base.BaseDaoHelper;
import com.ming.cnbeta.db.greendao.CommentItem;
import com.ming.cnbeta.db.greendao.CommentItemDao;

import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by ming on 16/2/29.
 */
public class CommentItemDaoHelper extends BaseDaoHelper<CommentItem,String>{
    @Override
    public AbstractDao getDao() {
        return BaseApplication.getDaoSession().getCommentItemDao();
    }

    public List<CommentItem> getCommentsBySid(String sid){

        try {
            List qb = dao.queryBuilder().where(CommentItemDao.Properties.Sid.eq(sid))
                    .orderDesc(CommentItemDao.Properties.Score)
                    .list();
            return qb;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
