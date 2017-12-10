package com.aliletter.skinswitcher.base;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Author: aliletter
 * Github：https://github.com/aliletter
 * Emile:4884280@qq.com
 */

  abstract class Factory implements LayoutInflater.Factory2 {
    public static void initSkinFactory(Context context) {
        SkinManager.getInstance().init(context.getApplicationContext());
    }

    public static void loadSkinPackage(String filePath) {
        SkinManager.getInstance().loadSkinPackage(filePath);
    }
}
