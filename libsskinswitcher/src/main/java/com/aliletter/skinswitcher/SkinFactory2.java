package com.aliletter.skinswitcher;

import android.view.View;

import com.aliletter.skinswitcher.base.BaseFactory;
import com.aliletter.skinswitcher.base.ResourceType;

/**
 * Author: aliletter
 * Github：https://github.com/aliletter
 * Emile:4884280@qq.com
 */

public class SkinFactory2 extends BaseFactory {
    public interface OnSkinFactory {
        boolean onAttributeFilter(String attrName);

        void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj);
    }

    protected OnSkinFactory onSkinFactory;

    public void setOnSkinFactory(OnSkinFactory onSkinFactory) {
        this.onSkinFactory = onSkinFactory;
    }

    @Override
    protected void switcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
        onSkinFactory.onSwitcher(view, attrType, attrName, attrValue, attrObj);
    }

    @Override
    protected boolean attributeFilter(String attrName, String value) {
        return onSkinFactory.onAttributeFilter(attrName) && value.contains("@");
    }
}
