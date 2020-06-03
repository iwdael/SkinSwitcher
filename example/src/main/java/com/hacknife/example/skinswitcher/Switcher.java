package com.hacknife.example.skinswitcher;

import android.view.View;

import com.hacknife.skinswitcher.annotation.Id;
import com.hacknife.skinswitcher.annotation.Replace;
import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.SwitcherHelper;

public class Switcher {

    @Replace
    static String replace(String originalValue) {
        return originalValue.replace("default", "mav");
    }

    @Id
    static int replaceId(View view, String value, Type type) {
        return SwitcherHelper.getResourceId(view.getClass().getClassLoader(), "com.hacknife.example", value, type);
    }
}
