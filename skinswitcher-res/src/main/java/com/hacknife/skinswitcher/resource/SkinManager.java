package com.hacknife.skinswitcher.resource;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.core.BaseManager;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class SkinManager extends BaseManager {
    private static volatile SkinManager instance;

    public static SkinManager getInstance() {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null)
                    instance = new SkinManager();
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
    }


    public void loadSkinPackage(String apk) {
        super.loadSkinPackage(mContext, new File(apk));
    }

    @Override
    public Object getStyleable(Type type, String id) {
        return null;
    }

    @Override
    public Object getStyle(Type type, String id) {
        return null;
    }

    @Override
    public String getString(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getString(getResourceId(type, id));
    }

    @Override
    public Drawable getMipmap(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getDrawable(getResourceId(type, id));
    }

    @Override
    public XmlResourceParser getLayout(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getLayout(getResourceId(type, id));
    }

    @Override
    public Integer getInteger(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getInteger(getResourceId(type, id));
    }

    @Override
    public Object getID(Type type, String id) {
        return getResourceId(type, id);
    }

    @Override
    public Drawable getDrawable(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getDrawable(getResourceId(type, id));
    }

    @Override
    public Float getDimen(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getDimension(getResourceId(type, id));
    }

    @Override
    public Integer getColor(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getColor(getResourceId(type, id));
    }

    @Override
    public Boolean getBool(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getBoolean(getResourceId(type, id));
    }

    @Override
    public Object getAttr(Type type, String id) {
        return null;
    }

    @Override
    public XmlResourceParser getAnim(Type type, String id) {
        if (mResources == null) return null;
        return mResources.getAnimation(getResourceId(type, id));
    }
}
