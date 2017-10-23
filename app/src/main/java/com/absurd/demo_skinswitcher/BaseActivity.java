package com.absurd.demo_skinswitcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;

/**
 * Created by 段泽全 on 2017/10/23.
 * Github：https://github.com/mr-absurd
 * Emile:4884280@qq.com
 */

public class BaseActivity extends Activity {
    private SkinFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory=new SkinFactory();
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), factory);
    }
}
