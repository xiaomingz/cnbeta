package com.ming.cnbeta.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ming.cnbeta.db.DaoHelper.THDevOpenHelper;
import com.ming.cnbeta.db.greendao.DaoMaster;
import com.ming.cnbeta.db.greendao.DaoSession;
import com.ming.cnbeta.net.NewsClient;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by ming on 16/2/18.
 */
public class BaseApplication extends Application{

    private static Context mContext;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        init();
    }

    private void init(){
        //蒲公英崩溃统计
        PgyCrashManager.register(this);
        // 初始化网络请求
        NewsClient.init();

        //检测内存泄露
//        LeakCanary.install(this);

        setUpDatabase("cnBeta");
    }

    public void setUpDatabase(String databaseName){
        if (daoSession != null){
            daoSession.clear();
        }

        THDevOpenHelper helper = new THDevOpenHelper(this, databaseName + "-db", null);
        SQLiteDatabase mDatabase = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(mDatabase);
        daoSession = daoMaster.newSession();
    }

    public synchronized static DaoSession getDaoSession(){return daoSession;}

    public static synchronized BaseApplication context() {
        return (BaseApplication) mContext;
    }

    public static void showToast(String message) {
        if (message!=null)
            Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }
}
