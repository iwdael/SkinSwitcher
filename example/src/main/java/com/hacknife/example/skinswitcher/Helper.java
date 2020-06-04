package com.hacknife.example.skinswitcher;

import com.hacknife.skinswitcher.annotation.Id;
import com.hacknife.skinswitcher.annotation.Replace;
import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.SwitcherHelper;

class Helper {

    @Replace
    static String replace(String originalValue) {
         return originalValue.replace("default", "mav");
    }

    @Id
    static int replaceId(String value, Type type) {
         return SwitcherHelper.getResourceId(null, "com.hacknife.example", value, type);
    }
}
