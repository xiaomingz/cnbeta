package com.ming.cnbeta.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by ming on 16/2/19.
 */
public class BaseActivity extends AppCompatActivity {

    private SwipeBackActivityHelper mSwipeBackActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSwipeBackActivityHelper = new SwipeBackActivityHelper(this);
        mSwipeBackActivityHelper.onActivityCreate();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackActivityHelper.onPostCreate();
    }

    public void setSwipeBackLayoutEnable(boolean enable){
        mSwipeBackActivityHelper.getSwipeBackLayout().setEnableGesture(enable);
    }

    public void showToast(String message) {
        if (message!=null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
