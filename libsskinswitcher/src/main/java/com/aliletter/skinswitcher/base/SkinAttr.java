package com.aliletter.skinswitcher.base;
import com.aliletter.skinswitcher.util.ResourceTypeUtil;

/**
 * Created by aliletter on 2017/10/24.
 * Github：https://github.com/aliletter
 * Emile:4884280@qq.com
 */

  class SkinAttr {
    private String attrName;
    private String attrValue;
    private ResourceType attrType;

    public SkinAttr(String attrName, String attrValue, String attrType) {
        this.attrName = attrName;
        this.attrValue = attrValue;
        this.attrType = ResourceTypeUtil.getType(attrType);
    }

    public String getAttrName() {
        return attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public ResourceType getAttrType() {
        return attrType;
    }
}
