package com.hacknife.skinswitcher.entity;

import java.util.HashMap;
import java.util.Map;

public class Attribute {
    Map<Integer, Object> attr = new HashMap<>();

    public Attribute put(Integer k, Object v) {
        attr.put(k, v);
        return this;
    }

    public <T> T get(Integer k) {
        return (T) attr.get(k);
    }
}
