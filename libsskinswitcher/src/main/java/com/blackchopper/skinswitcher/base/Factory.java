package com.blackchopper.skinswitcher.base;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
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
