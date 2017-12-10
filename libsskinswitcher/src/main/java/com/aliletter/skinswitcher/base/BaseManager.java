package com.aliletter.skinswitcher.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.aliletter.skinswitcher.util.ResourceTypeUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/8/28.
 */

  abstract class BaseManager {

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

    protected int getId(ResourceType plugin, String id) {
        int resId = 0;
        try {
            Class<?> loadClass = mClassLoader.loadClass(mPackageName + ResourceTypeUtil.getType(plugin));
            //   Field[] fields = loadClass.getFields();
            Field resIdField = loadClass.getDeclaredField(id);
            resId = (int) resIdField.get(null);
            Log.v("TAG", "----resId--->>" + resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
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

    public abstract Object getStyeale(ResourceType plugin, String id);

    public abstract Object getStyle(ResourceType plugin, String id);

    public abstract String getString(ResourceType plugin, String id);

    public abstract Drawable getMipmap(ResourceType plugin, String id);

    public abstract XmlResourceParser getLayout(ResourceType plugin, String id);

    public abstract Integer getInteger(ResourceType plugin, String id);

    public abstract Object getID(ResourceType plugin, String id);

    public abstract Drawable getDrawable(ResourceType plugin, String id);

    public abstract Float getDimen(ResourceType plugin, String id);

    public abstract Integer getColor(ResourceType plugin, String id);

    public abstract Boolean getBool(ResourceType plugin, String id);

    public abstract Object getAttr(ResourceType plugin, String id);

    public abstract XmlResourceParser getAnim(ResourceType plugin, String id);

    public Object getAttrValue(ResourceType attrType, String attrValue) {
        if (mResources == null) return null;
        switch (attrType) {
            case STYLEABLE:
                return getStyeale(ResourceType.STYLEABLE, attrValue);
            case STYLE:
                return getStyle(ResourceType.STYLE, attrValue);
            case STRING:
                return getString(ResourceType.STRING, attrValue);
            case MIPMAP:
                return getMipmap(ResourceType.MIPMAP, attrValue);
            case LAYOUT:
                return getLayout(ResourceType.LAYOUT, attrValue);
            case INTEGER:
                return getInteger(ResourceType.INTEGER, attrValue);
            case ID:
                return getID(ResourceType.ID, attrValue);
            case DRAWABLE:
                return getDrawable(ResourceType.DRAWABLE, attrValue);
            case DIMEN:
                return getDimen(ResourceType.DIMEN, attrValue);
            case COLOR:
                return getColor(ResourceType.COLOR, attrValue);
            case BOOL:
                return getBool(ResourceType.BOOL, attrValue);
            case ATTR:
                return getBool(ResourceType.ATTR, attrValue);
            case ANIM:
                return getBool(ResourceType.ANIM, attrValue);
        }
        return null;
    }
}
