package com.blackchopper.skinswitcher;

import android.view.View;

import com.blackchopper.skinswitcher.base.BaseFactory;
import com.blackchopper.skinswitcher.base.ResourceType;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
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
