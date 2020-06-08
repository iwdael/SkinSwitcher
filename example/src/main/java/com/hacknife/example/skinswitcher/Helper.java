package com.hacknife.example.skinswitcher;

import android.view.View;

import com.hacknife.skinswitcher.annotation.Id;
import com.hacknife.skinswitcher.annotation.Replace;
import com.hacknife.skinswitcher.annotation.Resource;
import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.SwitcherHelper;

class Helper {

    @Replace
    static String replace(String originalValue) {
        return originalValue.replace("default", "mav");
    }

    @Id
    static int replaceId(String value, Type type) {
        return SwitcherHelper.str2Id(null, "com.hacknife.example", value, type);
    }

    @Resource
    static Object id2Resource(View view, Type type, int id) {
        return SwitcherHelper.id2Resource(view.getResources(), type, id);
    }
}
