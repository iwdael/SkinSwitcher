package com.hacknife.example.skinswitcher;

import android.view.View;

import com.hacknife.skinswitcher.annotation.DefaultFilter;
import com.hacknife.skinswitcher.annotation.Id;
import com.hacknife.skinswitcher.annotation.Replace;
import com.hacknife.skinswitcher.annotation.Resource;
import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.SwitcherHelper;

public class Helper {
    public static String Skin = "default";

    @Replace
    static String replace(String originalValue) {
        return originalValue.replace("default", Skin);
    }

    @Id
    static int replaceId(String value, Type type) {
        return SwitcherHelper.str2Id(null, "com.hacknife.example", value, type);
    }

    @Resource
    static Object id2Resource(View view, Type type, int id) {
        return SwitcherHelper.id2Resource(view.getResources(), type, id);
    }

    @DefaultFilter
    static boolean defaultFilter(String value) {
        return value.contains("default");
    }
}
