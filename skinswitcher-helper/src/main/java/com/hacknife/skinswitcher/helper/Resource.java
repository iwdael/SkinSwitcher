package com.hacknife.skinswitcher.helper;

import java.util.HashMap;
/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class Resource {
    private static volatile Resource resource;
    private HashMap<String, Integer> resourceMap;

    public static Resource get() {
        if (resource == null) {
            synchronized (Resource.class) {
                if (resource == null) {
                    resource = new Resource();
                    resource.resourceMap = new HashMap<>();
                }
            }
        }
        return resource;
    }

    public static void put(String k, Integer v) {
        get().resourceMap.put(k, v);
    }

    public static int get(String k) {
        if (get().resourceMap.containsKey(k)) {
            return get().resourceMap.get(k);
        } else {
            return 0;
        }
    }
}
