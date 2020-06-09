package com.hacknife.skinswitcher.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.SwitcherHelper;
import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public abstract class BaseManager {

    protected File mDir;
    protected Context mContext;
    protected File mApk;
    protected DexClassLoader mClassLoader;
    protected String mPackageName;
    protected AssetManager mAsset;
    protected Resources mResources;

    protected void loadSkinPackage(Context context, File apk) {
        this.mContext = context;
        this.mApk = apk;
        if (!apk.exists()) Log.e("skinswitcher", "apk is not exists !");
        mDir = this.mContext.getDir("skinswitcher", Context.MODE_PRIVATE);
        mClassLoader = new DexClassLoader(mApk.getAbsolutePath(), mDir.getAbsolutePath(), null, mContext.getClassLoader());
        mPackageName = mContext.getPackageManager().getPackageArchiveInfo(apk.getAbsolutePath(), PackageManager.GET_ACTIVITIES).packageName;
        mAsset = getAssetManager(mApk.getAbsolutePath());
        mResources = new Resources(mAsset, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
    }

    protected int getResourceId(Type type, String id) {
        return SwitcherHelper.str2Id(mClassLoader, mPackageName, id, type);
    }

    private AssetManager getAssetManager(String apk) {
        AssetManager asset = null;
        try {
            asset = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(asset, apk);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asset;
    }

    public abstract Object getStyleable(Type type, String id);

    public abstract Object getStyle(Type type, String id);

    public abstract String getString(Type type, String id);

    public abstract Drawable getMipmap(Type type, String id);

    public abstract XmlResourceParser getLayout(Type type, String id);

    public abstract Integer getInteger(Type type, String id);

    public abstract Object getID(Type type, String id);

    public abstract Drawable getDrawable(Type type, String id);

    public abstract Float getDimen(Type type, String id);

    public abstract Integer getColor(Type type, String id);

    public abstract Boolean getBool(Type type, String id);

    public abstract Object getAttr(Type type, String id);

    public abstract XmlResourceParser getAnim(Type type, String id);

    public Object getAttrValue(Type attrType, String attrValue) {
        if (mResources == null) return null;
        switch (attrType) {
            case STYLEABLE:
                return getStyleable(Type.STYLEABLE, attrValue);
            case STYLE:
                return getStyle(Type.STYLE, attrValue);
            case STRING:
                return getString(Type.STRING, attrValue);
            case MIPMAP:
                return getMipmap(Type.MIPMAP, attrValue);
            case LAYOUT:
                return getLayout(Type.LAYOUT, attrValue);
            case INTEGER:
                return getInteger(Type.INTEGER, attrValue);
            case ID:
                return getID(Type.ID, attrValue);
            case DRAWABLE:
                return getDrawable(Type.DRAWABLE, attrValue);
            case DIMEN:
                return getDimen(Type.DIMEN, attrValue);
            case COLOR:
                return getColor(Type.COLOR, attrValue);
            case BOOL:
                return getBool(Type.BOOL, attrValue);
            case ATTR:
                return getAttr(Type.ATTR, attrValue);
            case ANIM:
                return getAnim(Type.ANIM, attrValue);
        }
        return null;
    }
}
