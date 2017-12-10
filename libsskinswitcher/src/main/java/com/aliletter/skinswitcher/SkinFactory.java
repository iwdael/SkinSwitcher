package com.aliletter.skinswitcher;

import android.view.View;

import com.aliletter.skinswitcher.base.BaseFactory;
import com.aliletter.skinswitcher.base.ResourceType;

/**
 * Author: aliletter
 * Github：https://github.com/aliletter
 * Emile:4884280@qq.com
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
