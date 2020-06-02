package com.hacknife.skinswitcher;


import com.hacknife.skinswitcher.core.BaseFactory;

import java.util.Arrays;


/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class SkinSwitcherFactory extends BaseFactory {

    @Override
    public void skinSwitch() {
        for (SkinView skinView : skinViews) {
            skinView.skinSwitch();
        }
    }

    @Override
    public SkinSwitcherFactory addSkinSwitcherAdapters(SkinSwitcherAdapter... adapters) {
        switcherAdapters.addAll(Arrays.asList(adapters));
        return this;
    }
}
