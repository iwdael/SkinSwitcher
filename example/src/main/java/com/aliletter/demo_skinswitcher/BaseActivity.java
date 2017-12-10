package com.aliletter.demo_skinswitcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.View;

import com.aliletter.skinswitcher.SkinFactory;
import com.aliletter.skinswitcher.SkinFactory2;
import com.aliletter.skinswitcher.base.BaseFactory;
import com.aliletter.skinswitcher.base.ResourceType;

/**
 * Created by aliletter on 2017/10/23.
 * Github：https://github.com/aliletter
 * Emile:4884280@qq.com
 */

public class BaseActivity extends Activity implements SkinFactory2.OnSkinFactory {
    protected SkinFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory = new SkinFactory() {
            @Override
            public boolean onAttributeFilter(String attrName) {
                return attrName.contains("background");
            }

            @Override
            public void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
                view.setBackgroundColor((Integer) attrObj);
            }
        };
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), factory);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        factory.apply();
    }

    @Override
    public boolean onAttributeFilter(String attrName) {
        return attrName.contains("background");
    }

    @Override
    public void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
        view.setBackgroundColor((Integer) attrObj);
    }
}
