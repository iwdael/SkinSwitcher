package com.absurd.demo_skinswitcher;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 段泽全 on 2017/10/23.
 * Github：https://github.com/mr-absurd
 * Emile:4884280@qq.com
 */

public class SkinFactory implements LayoutInflater.Factory2 {

    @Override
    public View onCreateView(View view, String s, Context context, AttributeSet attributeSet) {
        Log.v("TAG", "------------------->>" + s);
        return null;
    }

    @Override
    public View onCreateView(String s, Context context, AttributeSet attributeSet) {
        Log.v("TAG", "------------------->>" + s);
        return null;
    }
}
