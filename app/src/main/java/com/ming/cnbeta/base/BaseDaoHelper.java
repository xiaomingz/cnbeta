package com.ming.cnbeta.base;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by ming on 15/11/25.
 */
public abstract class BaseDaoHelper<T,ID> {

    protected  AbstractDao dao;

    public BaseDaoHelper(){
        dao = getDao();
    }

    public abstract AbstractDao getDao();

    /**
     * 添加数据
     * @param bean
     * @param <T>
     */
    public <T> void addData(T bean) {
        if(dao != null && bean != null) {
            dao.insertOrReplace(bean);
        }
    }

    public <T> void addListData(List<T> beans){
        if(dao != null && beans != null) {
            for (T t  : beans) {
                dao.insertOrReplace(t);
            }
        }
    }

    /**
     * 更新数据
     * @param bean
     * @param <T>
     */
    public <T> void update(T bean) {
        if(dao != null && bean != null) {
            dao.update(bean);
            dao.updateInTx();
        }
    }

    /**
     * 删除数据
     * @param id
     */
    public void deleteData(ID id) {
        if(dao != null ) {
            dao.deleteByKey(id);
        }
    }


    /**
     * 根据ID获取数据
     * @param id
     * @return
     */
    public T getDataById(ID id) {
        if(dao != null ) {
            return (T)dao.load(id);
        }
        return null;
    }

    /**
     * 获取所有数据
     * @return
     */
    public List getAllData() {
        if(dao != null) {
            return dao.loadAll();
        }
        return null;
    }

    /**
     * 获取数量
     * @return
     */
    public long getTotalCount() {
        if(dao == null) {
            return 0;
        }

        QueryBuilder<T> qb = dao.queryBuilder();
        return qb.buildCount().count();
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        if(dao != null) {
            dao.deleteAll();
        }
    }



}
