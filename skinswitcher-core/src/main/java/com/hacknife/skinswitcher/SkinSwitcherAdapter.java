package com.hacknife.skinswitcher;

import android.view.View;

import com.hacknife.skinswitcher.entity.Type;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public interface SkinSwitcherAdapter {

    boolean filter(View view, String name, String attr, String value, Type type);

    boolean switcher(View view, String name, String attr, String value, Type type);
}
