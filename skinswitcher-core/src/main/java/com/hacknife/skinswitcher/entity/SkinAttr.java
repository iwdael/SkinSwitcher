package com.hacknife.skinswitcher.entity;


/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class SkinAttr {
    public String name;
    public String value;
    public Type type;

    public SkinAttr(String name, String value, Type type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\'" + name + "\'" +
                ", \"value\":\'" + value + "\'" +
                ", \"type\":" + type +
                '}';
    }
}
