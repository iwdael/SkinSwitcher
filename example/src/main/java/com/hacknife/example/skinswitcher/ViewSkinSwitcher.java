package com.hacknife.example.skinswitcher;

import android.view.View;
import android.widget.TextView;

import com.hacknife.skinswitcher.annotation.Filter;
import com.hacknife.skinswitcher.annotation.Switcher;
import com.hacknife.skinswitcher.annotation.Target;


/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
@Target(View.class)
class ViewSkinSwitcher {
    @Filter
    static boolean background(String attr, String value) {
        return attr.equals("background") && value.contains("default");
    }

    @Switcher
    static void background(View view, int resource) {
        view.setBackgroundColor(resource);
    }
}
