package com.hacknife.example.skinswitcher;

import android.view.View;
import android.widget.TextView;

import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.TypeHelper;
import com.hacknife.skinswitcher.BuildConfig;
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
        view.setBackgroundResource(TypeHelper.getResourceId(view.getClass().getClassLoader(), BuildConfig.APPLICATION_ID, value.replace("default", "mav"), type));
    }

    @Filter
    static boolean textColor(String attr, String value) {
        return attr.equals("background") && value.contains("default");
    }

    @Switcher
    static void textColor(View view, String value, Type type) {
        ((TextView)view).setTextColor(TypeHelper.getResourceId(view.getClass().getClassLoader(), BuildConfig.APPLICATION_ID, value.replace("default", "mav"), type));
    }
}
