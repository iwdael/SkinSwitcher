package com.hacknife.example.skinswitcher;

import android.view.View;

import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.SwitcherHelper;
import com.hacknife.skinswitcher.annotation.Filter;
import com.hacknife.skinswitcher.annotation.Switcher;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class ViewSkinSwitcher {
    @Filter
    static boolean background(String attr, String value) {
        return attr.equals("background") && value.contains("default");
    }

    @Switcher
    static void background(View view, String value, Type type) {
        view.setBackgroundResource(SwitcherHelper.getResourceId(view.getClass().getClassLoader(), "com.hacknife.example", value.replace("default", "mav"), type));
    }

}
