package com.blackchopper.skinswitcher.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.blackchopper.skinswitcher.base.ResourceType;

import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : SkinSwitcher
 */

public class ResourceTypeUtil {
    private static String styleable = ".R$styleable";
    private static String style = ".R&style";
    private static String string = ".R$string";
    private static String mipmap = ".R$mipmap";
    private static String layout = ".R$layout";
    private static String integer = ".R$integer";
    private static String id = ".R$id";
    private static String drawable = ".R$drawable";
    private static String dimen = ".R$dimen";
    private static String color = ".R$color";
    private static String bool = ".R$bool";
    private static String attr = ".R$attr";
    private static String anim = ".R$anim";


    public static String getType(ResourceType plugin) {
        switch (plugin) {
            case STYLEABLE:
                return styleable;
            case STYLE:
                return style;
            case STRING:
                return string;
            case MIPMAP:
                return mipmap;
            case LAYOUT:
                return layout;
            case INTEGER:
                return integer;
            case ID:
                return id;
            case DRAWABLE:
                return drawable;
            case DIMEN:
                return dimen;
            case COLOR:
                return color;
            case BOOL:
                return bool;
            case ATTR:
                return attr;
            case ANIM:
                return anim;
        }

        return "";
    }

    public static ResourceType getType(String type) {
        switch (type) {
            case "styleable":
                return ResourceType.STYLEABLE;
            case "style":
                return ResourceType.STYLE;
            case "string":
                return ResourceType.STRING;
            case "mipmap":
                return ResourceType.MIPMAP;
            case "layout":
                return ResourceType.LAYOUT;
            case "integer":
                return ResourceType.INTEGER;
            case "id":
                return ResourceType.ID;
            case "drawable":
                return ResourceType.DRAWABLE;
            case "dimen":
                return ResourceType.DIMEN;
            case "color":
                return ResourceType.COLOR;
            case "bool":
                return ResourceType.BOOL;
            case "attr":
                return ResourceType.ATTR;
            case "anim":
                return ResourceType.ANIM;
        }
        return null;
    }

    public static String getApkPackageName(Context context, File file) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        return info.packageName;
    }
}
