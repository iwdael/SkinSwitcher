package com.hacknife.skinswitcher;

import android.view.View;

import com.hacknife.skinswitcher.base.BaseFactory;
import com.hacknife.skinswitcher.base.ResourceType;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public abstract class SkinFactory extends BaseFactory {
    @Override
    protected void switcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
        onSwitcher(view, attrType, attrName, attrValue, attrObj);
    }

    @Override
    protected boolean attributeFilter(String attrName, String value) {
        return onAttributeFilter(attrName) && value.contains("@");
    }

    protected abstract boolean onAttributeFilter(String attrName);

    protected abstract void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj);
}
