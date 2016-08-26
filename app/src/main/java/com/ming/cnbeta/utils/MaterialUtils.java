package com.ming.cnbeta.utils;

import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ming.cnbeta.R;


/**
 * Material Design 工具类
 * Created by ming on 16/1/22.
 */
public class MaterialUtils {

    /**
     * 设置多个view为pipple效果
     * @param isAdapterView
     * @param views
     */
    public static void setMultipleRippleEffect(boolean isAdapterView,View ...views){
        for (View  view: views) {
            setCommonRippleEffect(isAdapterView,view);
        }
    }

    /**
     * 设置一般的pipple效果
     * @param view
     */
    public static void setCommonRippleEffect(boolean isAdapterView,View view){
        setRippleEffect(view, R.color.colorRipple,isAdapterView);
    }

    /**
     * 设置pipple效果
     * @param view
     * @param color
     */
    public static void setRippleEffect(View view,int color,boolean isAdapterView){

        MaterialRippleLayout.RippleBuilder builder = MaterialRippleLayout.on(view);

        if (isAdapterView){
            builder.rippleInAdapter(true);
        }

        builder.rippleColor(color)
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .rippleOverlay(true)
                .rippleDelayClick(true)
                .rippleDuration(100)
                .create();
    }

}
