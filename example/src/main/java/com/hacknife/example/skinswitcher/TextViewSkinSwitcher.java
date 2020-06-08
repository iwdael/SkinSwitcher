package com.hacknife.example.skinswitcher;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.hacknife.skinswitcher.annotation.Filter;
import com.hacknife.skinswitcher.annotation.Target;
import com.hacknife.skinswitcher.annotation.Switcher;

@Target(TextView.class)
class TextViewSkinSwitcher {

    @Filter
    static boolean textColor(String attr, String value) {
        return attr.equals("textColor") && value.contains("default");
    }

    @Switcher
    static void textColor(View view, Object obj) {
        ((TextView) view).setTextColor((Integer) obj);
    }
}
