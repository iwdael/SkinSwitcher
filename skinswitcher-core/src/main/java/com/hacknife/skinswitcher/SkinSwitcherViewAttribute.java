package com.hacknife.skinswitcher;

import com.hacknife.skinswitcher.entity.Attribute;


public interface SkinSwitcherViewAttribute {
    Attribute saveAttribute();

    void restoreAttribute(Attribute attrs);
}
