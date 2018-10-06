package com.hacknife.skinswitcher.base;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
  abstract class Factory implements LayoutInflater.Factory2 {
    public static void initSkinFactory(Context context) {
        SkinManager.getInstance().init(context.getApplicationContext());
    }

    public static void loadSkinPackage(String filePath) {
        SkinManager.getInstance().loadSkinPackage(filePath);
    }
}
