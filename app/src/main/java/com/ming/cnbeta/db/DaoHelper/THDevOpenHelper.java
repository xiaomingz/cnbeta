package com.ming.cnbeta.db.DaoHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ming.cnbeta.db.greendao.DaoMaster;


/**
 * Created by ming on 15/11/25.
 */
public class THDevOpenHelper extends DaoMaster.OpenHelper {


    public THDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        MigrationHelper.getInstance().migrate(db, NewsItemDao.class);

    }
}
