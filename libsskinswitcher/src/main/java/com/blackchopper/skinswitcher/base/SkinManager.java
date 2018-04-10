package com.blackchopper.skinswitcher.base;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : SkinSwitcher
 */
class SkinManager extends BaseManager {
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
    public Object getStyeale(ResourceType plugin, String id) {
        return null;
    }

    @Override
    public Object getStyle(ResourceType plugin, String id) {
        return null;
    }

    @Override
    public String getString(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getString(getId(plugin, id));
    }

    @Override
    public Drawable getMipmap(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getDrawable(getId(plugin, id));
    }

    @Override
    public XmlResourceParser getLayout(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getLayout(getId(plugin, id));
    }

    @Override
    public Integer getInteger(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getInteger(getId(plugin, id));
    }

    @Override
    public Object getID(ResourceType plugin, String id) {
        return null;
    }

    @Override
    public Drawable getDrawable(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getDrawable(getId(plugin, id));
    }

    @Override
    public Float getDimen(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getDimension(getId(plugin, id));
    }

    @Override
    public Integer getColor(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getColor(getId(plugin, id));
    }

    @Override
    public Boolean getBool(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getBoolean(getId(plugin, id));
    }

    @Override
    public Object getAttr(ResourceType plugin, String id) {
        return null;
    }

    @Override
    public XmlResourceParser getAnim(ResourceType plugin, String id) {
        if (mResources == null) return null;
        return mResources.getAnimation(getId(plugin, id));
    }
}
