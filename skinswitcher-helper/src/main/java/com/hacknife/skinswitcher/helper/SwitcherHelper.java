package com.hacknife.skinswitcher.helper;


import android.content.res.Resources;


import com.hacknife.skinswitcher.entity.Type;

import java.io.File;
import java.lang.reflect.Field;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class SwitcherHelper {
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


    public static String getType(Type type) {
        switch (type) {
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

    public static Type getType(String type) {
        switch (type) {
            case "styleable":
                return Type.STYLEABLE;
            case "style":
                return Type.STYLE;
            case "string":
                return Type.STRING;
            case "mipmap":
                return Type.MIPMAP;
            case "layout":
                return Type.LAYOUT;
            case "integer":
                return Type.INTEGER;
            case "id":
                return Type.ID;
            case "drawable":
                return Type.DRAWABLE;
            case "dimen":
                return Type.DIMEN;
            case "color":
                return Type.COLOR;
            case "bool":
                return Type.BOOL;
            case "attr":
                return Type.ATTR;
            case "anim":
                return Type.ANIM;
        }
        return null;
    }

    public static int str2Id(ClassLoader loader, String _package, String id, Type type) {
        String clazz = _package + SwitcherHelper.getType(type);
        String key = String.format("%s.%s", clazz, id);
        int resId = Resource.get(key);
        if (resId != 0)
            return resId;
        try {
            Class<?> loadClass = loader != null ? loader.loadClass(clazz) : Class.forName(clazz);
            Field field = loadClass.getDeclaredField(id);
            resId = (int) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resource.put(key, resId);
        return resId;
    }


    public static Object id2Resource(Resources resources, Type type, int id) {
        if (resources == null) return null;
        switch (type) {
            case STYLEABLE:
            case STYLE:
            case ATTR:
                return null;
            case STRING:
                return resources.getString(id);
            case MIPMAP:
            case DRAWABLE:
                return resources.getDrawable(id);
            case LAYOUT:
                return resources.getLayout(id);
            case INTEGER:
                return resources.getInteger(id);
            case ID:
                return id;
            case DIMEN:
                return resources.getDimension(id);
            case COLOR:
                return resources.getColor(id);
            case BOOL:
                return resources.getBoolean(id);
            case ANIM:
                return resources.getAnimation(id);
        }
        return null;
    }

}
